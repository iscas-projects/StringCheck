// related URL: https://github.com/dromara/hutool/issues/1981
public class Src018 {
    private char[] value;
    private int position;

    public Src018(int initialCapacity) {
        value = new char[initialCapacity];
    }

    public static void main(String[] args) throws Exception {
        int int1 = -1;
        int int2 = -1;
        char[] charArray3 = {'\'', '\''};
        int int4 = Integer.MAX_VALUE;
        int int5 = 475868835;
        Src018 src018 = new Src018(int5);
        src018.test(int1, int2, charArray3, int4);
    }

    public void test(int srcBegin, int srcEnd, char[] dst, int dstBegin) throws Exception {
        if (srcEnd < 0) {} else if (srcEnd > this.position) {
            srcEnd = this.position;
        }
        if (srcBegin > srcEnd) {
            throw new Exception("srcBegin > srcEnd");
        }
        System.arraycopy(value, srcBegin, dst, dstBegin, srcEnd - srcBegin);
    }
}