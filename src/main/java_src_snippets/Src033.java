// related URL: https://github.com/tobato/FastDFS_Client/issues/272
public class Src033 {
    public static void main(String[] args) {
        byte[] byteArray0 = {};
        int int1 = 1;
        test(byteArray0, int1);
    }

    public static int test(byte[] bs, int offset) {
        return ((bs[offset] >= 0 ? bs[offset] : 256 + bs[offset]) << 24)
                | ((bs[offset + 1] >= 0 ? bs[offset + 1] : 256 + bs[offset + 1]) << 16)
                | ((bs[offset + 2] >= 0 ? bs[offset + 2] : 256 + bs[offset + 2]) << 8)
                | (bs[offset + 3] >= 0 ? bs[offset + 3] : 256 + bs[offset + 3]);
    }
}