// Bug related URL: https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8278186
public class Src001 {
    public static void main(String[] args) {
        test("xpointer(id(cUX=\\A)");
    }
    public static String test(String uri) {
        if (uri.length() == 0) {
            return null;
        }
        String id = uri.substring(1);
        if (id != null && id.startsWith("xpointer(id(")) {
            int i1 = id.indexOf('\'');
            int i2 = id.indexOf('\'', i1+1);
            id = id.substring(i1+1, i2);
        }
        return id;
    }
}