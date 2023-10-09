// related URL: https://github.com/libgdx/libgdx/issues/6709
public class Src020 {
    private float[] colors = {1, 1, 1};
    public float[] timeline = {0};

    public static void main(String[] args) {
        float float1 = 0.1f;
        float[] floatArray2 = {0.0f, 1.4E-45f};
        int int3 = 1;
        Src020 src020 = new Src020();
        src020.test(float1, floatArray2, int3);
    }

    public void test(float percent, float[] out, int index) {
        int startIndex = 0, endIndex = -1;
        float[] timeline = this.timeline;
        int n = timeline.length;
        for (int i = 1; i < n; i++) {}
        float startTime = timeline[startIndex];
        startIndex *= 3;
        float r1 = colors[startIndex];
        float g1 = colors[startIndex + 1];
        float b1 = colors[startIndex + 2];
        if (endIndex == -1) {
            out[index] = r1;
            out[index + 1] = g1;
            out[index + 2] = b1;
            return;
        }
    }
}