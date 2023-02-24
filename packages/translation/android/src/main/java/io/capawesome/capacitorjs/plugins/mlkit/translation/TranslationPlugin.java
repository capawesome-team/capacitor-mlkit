package io.capawesome.capacitorjs.plugins.mlkit.translation;

import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.mlkit.nl.translate.TranslateRemoteModel;
import java.util.Set;

@CapacitorPlugin(name = "Translation")
public class TranslationPlugin extends Plugin {

    public static final String ERROR_LANGUAGE_MISSING = "language must be provided.";
    public static final String ERROR_TEXT_MISSING = "text must be provided.";
    public static final String ERROR_SOURCE_LANGUAGE_MISSING = "sourceLanguage must be provided.";
    public static final String ERROR_TARGET_LANGUAGE_MISSING = "targetLanguage must be provided.";

    private Translation implementation = new Translation();

    @PluginMethod
    public void deleteDownloadedModel(PluginCall call) {
        try {
            String language = call.getString("language");
            if (language == null) {
                call.reject(ERROR_LANGUAGE_MISSING);
                return;
            }

            implementation.deleteDownloadedModel(
                language,
                new DeleteDownloadedModelResultCallback() {
                    @Override
                    public void success() {
                        call.resolve();
                    }

                    @Override
                    public void error(Exception exception) {
                        String message = exception.getMessage();
                        Logger.error(message, exception);
                        call.reject(message);
                    }
                }
            );
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }

    @PluginMethod
    public void downloadModel(PluginCall call) {
        try {
            String language = call.getString("language");
            if (language == null) {
                call.reject(ERROR_LANGUAGE_MISSING);
                return;
            }

            implementation.downloadModel(
                language,
                new DownloadModelResultCallback() {
                    @Override
                    public void success() {
                        call.resolve();
                    }

                    @Override
                    public void error(Exception exception) {
                        String message = exception.getMessage();
                        Logger.error(message, exception);
                        call.reject(message);
                    }
                }
            );
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }

    @PluginMethod
    public void getDownloadedModels(PluginCall call) {
        try {
            implementation.getDownloadedModels(
                new GetDownloadedModelsResultCallback() {
                    @Override
                    public void success(Set<TranslateRemoteModel> models) {
                        JSArray languagesResult = new JSArray();
                        for (TranslateRemoteModel model : models) {
                            languagesResult.put(model.getLanguage());
                        }
                        JSObject result = new JSObject();
                        result.put("languages", languagesResult);
                        call.resolve(result);
                    }

                    @Override
                    public void error(Exception exception) {
                        String message = exception.getMessage();
                        Logger.error(message, exception);
                        call.reject(message);
                    }
                }
            );
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }

    @PluginMethod
    public void translate(PluginCall call) {
        try {
            String text = call.getString("text");
            if (text == null) {
                call.reject(ERROR_TEXT_MISSING);
                return;
            }
            String sourceLanguage = call.getString("sourceLanguage");
            if (sourceLanguage == null) {
                call.reject(ERROR_SOURCE_LANGUAGE_MISSING);
                return;
            }
            String targetLanguage = call.getString("targetLanguage");
            if (targetLanguage == null) {
                call.reject(ERROR_TARGET_LANGUAGE_MISSING);
                return;
            }

            implementation.translate(
                text,
                sourceLanguage,
                targetLanguage,
                new TranslateResultCallback() {
                    @Override
                    public void success(String text) {
                        JSObject result = new JSObject();
                        result.put("text", text);
                        call.resolve(result);
                    }

                    @Override
                    public void error(Exception exception) {
                        String message = exception.getMessage();
                        Logger.error(message, exception);
                        call.reject(message);
                    }
                }
            );
        } catch (Exception exception) {
            String message = exception.getMessage();
            Logger.error(message, exception);
            call.reject(message);
        }
    }
}
