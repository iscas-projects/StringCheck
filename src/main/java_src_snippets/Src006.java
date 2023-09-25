// related URL: https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8279218
public class Src006 {
    public static int[] parseVersion(String version) {
        if (version == null)
            return new int[0];
        char[] s = version.toCharArray();
        //find the maximum span of the string "n.n.n..." where n is an integer
        int start = 0;
        for (; start < s.length  && (s[start] < '0' || s[start] > '9'); ++start)
            if (start == s.length)      //no digit found
                return new int[0];
        int end = start + 1;
        int size = 1;
        for (; end < s.length; ++end)
            if (s[end] == '.')
                ++size;
            else if (s[end] < '0' || s[end] > '9')
                break;
        int[] val = new int[size];
        for (int i = 0; i < size; ++i) {
            int dot = version.indexOf('.', start);
            if (dot == -1 || dot > end)
                dot = end;
            if (start >= dot)   //cases like "n." or "n..m"
                val[i] = 0;     //convert equivalent to "n.0" or "n.0.m"
            else
                val[i] = Integer.parseInt(version.substring(start, dot));
            start = dot + 1;
        }
        return val;
    }
}