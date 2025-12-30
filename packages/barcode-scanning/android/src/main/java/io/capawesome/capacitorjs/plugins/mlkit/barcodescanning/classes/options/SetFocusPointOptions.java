package io.capawesome.capacitorjs.plugins.mlkit.barcodescanning.classes.options;

public class SetFocusPointOptions {

    private float x;
    private float y;

    public SetFocusPointOptions(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
} 