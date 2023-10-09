// related URL: https://github.com/cmusphinx/sphinx4/issues/106
public class Src030 {
    public static void main(String[] args) {
        double[][] doubleArray0 = {};
        test(doubleArray0);
    }

    public static float[] double2float(double[] values) { // what a mess !!! -> fixme: how to convert number arrays ?
        float[] newVals = new float[values.length];
        for (int i = 0; i < newVals.length; i++) {
            newVals[i] = (float) values[i];
        }

        return newVals;
    }

    public static float[][] test(double[][] array) {
        float[][] floatArr = new float[array.length][array[0].length];
        for (int i = 0; i < array.length; i++)
            floatArr[i] = double2float(array[i]);
        return floatArr;
    }
}