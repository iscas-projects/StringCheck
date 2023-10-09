import java.io.File;
import java.net.MalformedURLException;

// related URL: https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8279422
public class Src010 {
    public static void main(String[] args) throws MalformedURLException {
        test(new String[] {});
    }
    public static void test(String[] args) throws MalformedURLException {
        String path = new File(args[0]).toURL().toString();
    }
}