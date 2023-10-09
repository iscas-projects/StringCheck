import java.nio.charset.Charset;

// related URL: https://github.com/dromara/hutool/issues/1975
public class Src015 {
    public static void main(String[] args) {
        test("", null);
    }

    public static String test(String paramPart, Charset charset) {
        int lastIndex = paramPart.length() - 1;
        if ('&' == paramPart.charAt(lastIndex)) {
        }
        return paramPart;
    }
}