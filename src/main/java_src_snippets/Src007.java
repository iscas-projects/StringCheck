// related URL: https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8279336
public class Src007 {
    public static boolean isManifestName(String name) {

        // remove leading /
        if (name.charAt(0) == '/') {
            name = name.substring(1, name.length());
        }
        // case insensitive
        name = name.toUpperCase();

        if (name.equals("META-INF/MANIFEST.MF")) {
            return true;
        }
        return false;
    }
}