package io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition

import com.getcapacitor.Logger
import com.google.mlkit.genai.common.DownloadStatus
import com.google.mlkit.genai.common.FeatureStatus
import com.google.mlkit.genai.common.audio.AudioSource
import com.google.mlkit.genai.speechrecognition.SpeechRecognition
import com.google.mlkit.genai.speechrecognition.SpeechRecognizer
import com.google.mlkit.genai.speechrecognition.SpeechRecognizerOptions
import com.google.mlkit.genai.speechrecognition.SpeechRecognizerRequest
import com.google.mlkit.genai.speechrecognition.SpeechRecognizerResponse
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.CustomExceptions
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.events.DownloadProgressEvent
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.events.ErrorEvent
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.events.FinalResultEvent
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.events.PartialResultEvent
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.options.FeatureOptions
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.classes.results.CheckFeatureStatusResult
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.interfaces.EmptyCallback
import io.capawesome.capacitorjs.plugins.mlkit.genaispeechrecognition.interfaces.NonEmptyResultCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class GenAiSpeechRecognition(private val plugin: GenAiSpeechRecognitionPlugin) {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private var recognitionJob: Job? = null
    private var speechRecognizer: SpeechRecognizer? = null
    private var speechRecognizerOptions: FeatureOptions? = null

    fun checkFeatureStatus(options: FeatureOptions, callback: NonEmptyResultCallback<CheckFeatureStatusResult>) {
        val speechRecognizer = getSpeechRecognizer(options)
        coroutineScope.launch {
            try {
                val featureStatus = speechRecognizer.checkStatus()
                callback.success(CheckFeatureStatusResult(featureStatus))
            } catch (exception: Exception) {
                callback.error(exception)
            }
        }
    }

    fun downloadFeature(options: FeatureOptions, callback: EmptyCallback) {
        val speechRecognizer = getSpeechRecognizer(options)
        coroutineScope.launch {
            try {
                if (speechRecognizer.checkStatus() == FeatureStatus.AVAILABLE) {
                    callback.success()
                    return@launch
                }
                speechRecognizer.download().collect { downloadStatus ->
                    when (downloadStatus) {
                        is DownloadStatus.DownloadProgress -> plugin.notifyDownloadProgressListeners(
                            DownloadProgressEvent(downloadStatus.totalBytesDownloaded)
                        )
                        is DownloadStatus.DownloadCompleted -> callback.success()
                        is DownloadStatus.DownloadFailed -> callback.error(downloadStatus.e)
                        else -> {}
                    }
                }
            } catch (exception: Exception) {
                callback.error(exception)
            }
        }
    }

    fun handleOnDestroy() {
        closeSpeechRecognizer()
        coroutineScope.cancel()
    }

    fun startRecognition(options: FeatureOptions, callback: EmptyCallback) {
        if (recognitionJob?.isActive == true) {
            callback.error(CustomExceptions.RECOGNITION_ALREADY_RUNNING)
            return
        }
        val speechRecognizer = getSpeechRecognizer(options)
        val requestBuilder = SpeechRecognizerRequest.Builder()
        requestBuilder.audioSource = AudioSource.fromMic()
        val request = requestBuilder.build()
        recognitionJob = coroutineScope.launch {
            try {
                speechRecognizer.startRecognition(request).collect { response ->
                    when (response) {
                        is SpeechRecognizerResponse.PartialTextResponse -> plugin.notifyPartialResultListeners(
                            PartialResultEvent(response.text)
                        )
                        is SpeechRecognizerResponse.FinalTextResponse -> plugin.notifyFinalResultListeners(
                            FinalResultEvent(response.text)
                        )
                        is SpeechRecognizerResponse.ErrorResponse -> handleRecognitionError(response.e)
                        else -> {}
                    }
                }
            } catch (exception: Exception) {
                handleRecognitionError(exception)
            }
        }
        callback.success()
    }

    fun stopRecognition(callback: EmptyCallback) {
        val speechRecognizer = speechRecognizer
        if (speechRecognizer == null || recognitionJob?.isActive != true) {
            callback.success()
            return
        }
        coroutineScope.launch {
            try {
                speechRecognizer.stopRecognition()
                callback.success()
            } catch (exception: Exception) {
                callback.error(exception)
            }
        }
    }

    private fun closeSpeechRecognizer() {
        recognitionJob?.cancel()
        recognitionJob = null
        speechRecognizer?.close()
        speechRecognizer = null
        speechRecognizerOptions = null
    }

    private fun getSpeechRecognizer(options: FeatureOptions): SpeechRecognizer {
        var speechRecognizer = this.speechRecognizer
        if (speechRecognizer == null || !options.matches(speechRecognizerOptions)) {
            closeSpeechRecognizer()
            val builder = SpeechRecognizerOptions.Builder()
            builder.locale = options.locale
            builder.preferredMode = options.mode
            speechRecognizer = SpeechRecognition.getClient(builder.build())
            this.speechRecognizer = speechRecognizer
            this.speechRecognizerOptions = options
        }
        return speechRecognizer
    }

    private fun handleRecognitionError(exception: Exception) {
        val message = exception.message ?: GenAiSpeechRecognitionPlugin.ERROR_UNKNOWN_ERROR
        Logger.error(GenAiSpeechRecognitionPlugin.TAG, message, exception)
        plugin.notifyErrorListeners(ErrorEvent(message))
    }
}
