// related URL: https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8279424
public class Src012 {
    public static void main(String[] args) throws ClassNotFoundException {
        (new Src012()).test("");
    }
    public void test(String name) throws ClassNotFoundException {
        // array
        if(name.endsWith("[]"))
            return;

        // try primitive type
        try {
            return;
        } catch (IllegalArgumentException e) {
            ;
        }

        // existing class
        new TypeNameParser(name).parseTypeName();
    }

    private final class TypeNameParser {
        private final String s;
        private int idx;

        public TypeNameParser(String s) {
            this.s = s;
        }

        void parseTypeName() {
            int start = idx;

            if (s.charAt(idx) == '?') {

            }

        }
    }
}