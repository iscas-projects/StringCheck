import java.io.InputStream;

// related URL: https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8279198
public class Src005 {
    public InputSource resolveEntity(String var1, String var2) {
        String var3 = var2.substring(var2.lastIndexOf("/"));
        return var2.startsWith("http://java.sun.com/xml/ns/jdbc") ? new InputSource(this.getClass().getResourceAsStream(var3)) : null;
    }

    private class InputSource {
        public InputSource(InputStream resourceAsStream) {
        }
    }
}
