// related URL: https://github.com/dromara/hutool/issues/1982
public class Src016 {
    private final int[] operatPriority = new int[]{0, 3, 2, 1, -1, 1, 0, 2};

    public static void main(String[] args) {
        char char1 = '%';
        char char2 = '$';
        Src016 src016 = new Src016();
        src016.test(char1, char2);
    }
    public boolean test(char cur, char peek) {
        final int offset = 40;
        if (cur == '%') {
            cur = 47;
        }
        if (peek == '%') {
            peek = 47;
        }

        return operatPriority[(peek) - offset] >= operatPriority[(cur) - offset];
    }
}