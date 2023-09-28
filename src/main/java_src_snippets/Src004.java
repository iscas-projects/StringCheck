// related URL: https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8279128
public class Src004 {
    public static boolean isName(String value) {
        if (value == null) {
            return false;
        } else {
            char c = value.charAt(0);
            if (!isLetter(c) && c != '_' && c != ':') {
                return false;
            } else {
                for(int i = 1; i < value.length(); ++i) {
                    if (!isNameChar(value.charAt(i))) {
                        return false;
                    }
                }

                return true;
            }
        }
    }

    public static boolean isNameChar(char c) {
        return false;
    }

    public static boolean isLetter(char c) {
            return false;
        }
}
