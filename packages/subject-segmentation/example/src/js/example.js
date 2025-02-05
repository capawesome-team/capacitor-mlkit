import { SubjectSegmentation } from '@capacitor-mlkit/subject-segmentation';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    SubjectSegmentation.echo({ value: inputValue })
}
