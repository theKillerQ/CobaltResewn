package se.fusion1013.render.effect;

public class EnvironmentEffectValues {
    public static boolean ResetTimer;

    public static int PaddingDuration = 100;

    public static float StartLerpFrom = 1;
    public static float EndLerpFrom = 1;

    public static float FogStart = 1;
    public static float FogEnd = 2;

    public static float ColorLerpFrom = 1;
    public static float Color = 1;

    public static void setStart(float start) {
        saveCurrentAsLerpFrom();
        FogStart = start;
        ResetTimer = true;
    }

    public static void setEnd(float end) {
        saveCurrentAsLerpFrom();
        FogEnd = end;
        ResetTimer = true;
    }

    public static void setColor(float color) {
        saveCurrentAsLerpFrom();
        Color = color;
        ResetTimer = true;
    }

    public static void setPaddingDuration(int paddingDuration) {
        saveCurrentAsLerpFrom();
        PaddingDuration = paddingDuration;
    }

    private static void saveCurrentAsLerpFrom() {
        StartLerpFrom = FogStart;
        EndLerpFrom = FogEnd;
        ColorLerpFrom = Color;
    }
}
