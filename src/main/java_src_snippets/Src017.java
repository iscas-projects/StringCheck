// related URL: https://github.com/dromara/hutool/issues/1980
public class Src017 {
    public static void main(String[] args) {
        char[] charArray0 = {'\''};
        int int1 = 0;
        int[][] intArray2 = {{0, Integer.MIN_VALUE, Integer.MIN_VALUE},
                {0, Integer.MIN_VALUE, Integer.MIN_VALUE},
                {0, Integer.MIN_VALUE, Integer.MIN_VALUE},
                {0, Integer.MIN_VALUE, Integer.MIN_VALUE}
        };
        zobrist(charArray0, int1, intArray2);
    }
    public static int zobrist(char[] key, int mask, int[][] tab) {
        int hash = key.length;

        for (int i = 0; i < key.length; ++i) {
            hash ^= tab[i][key[i]];
        }

        return hash & mask;
    }
}