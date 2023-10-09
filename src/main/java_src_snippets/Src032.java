// related URL: https://github.com/tobato/FastDFS_Client/issues/271
public class Src032 {
    public static void main(String[] args) {
        byte[] byteArray0 = {};
        int int1 = -1;
        test(byteArray0, int1);
    }

    public static long test(byte[] bs, int offset) {
        return (((long) (bs[offset] >= 0 ? bs[offset] : 256 + bs[offset])) << 56)
                | (((long) (bs[offset + 1] >= 0 ? bs[offset + 1] : 256 + bs[offset + 1])) << 48)
                | (((long) (bs[offset + 2] >= 0 ? bs[offset + 2] : 256 + bs[offset + 2])) << 40)
                | (((long) (bs[offset + 3] >= 0 ? bs[offset + 3] : 256 + bs[offset + 3])) << 32)
                | (((long) (bs[offset + 4] >= 0 ? bs[offset + 4] : 256 + bs[offset + 4])) << 24)
                | (((long) (bs[offset + 5] >= 0 ? bs[offset + 5] : 256 + bs[offset + 5])) << 16)
                | (((long) (bs[offset + 6] >= 0 ? bs[offset + 6] : 256 + bs[offset + 6])) << 8)
                | (bs[offset + 7] >= 0 ? bs[offset + 7] : 256 + bs[offset + 7]);
    }
}