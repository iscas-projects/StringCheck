// related URL: https://github.com/cmusphinx/sphinx4/pull/105
public class Src031 {
    public static void main(String[] args) {
        test("");
    }

    public static String test(String batchFileLine) {
        int firstSpace = batchFileLine.indexOf(' ');
        return batchFileLine.substring(0, firstSpace).trim();
    }
}