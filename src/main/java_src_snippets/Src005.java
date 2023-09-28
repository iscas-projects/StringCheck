import java.io.InputStream;

// related URL: https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8279198
public class Src005 {
    public Src005(InputStream resourceAsStream) {
    }

    public Src005 resolveEntity(String var1, String var2) {
        String var3 = var2.substring(var2.lastIndexOf("/"));
        return var2.startsWith("http://java.sun.com/xml/ns/jdbc") ? new Src005(this.getClass().getResourceAsStream(var3)) : null;
    }
    
}
