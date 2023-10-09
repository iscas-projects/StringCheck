// related URL: https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8279423
public class Src011 {
    public static void main(String[] args) {
        test("");
    }
    public static String test(String uriPath) {
        int idx = uriPath.lastIndexOf('/');

        if (uriPath.endsWith("/")) {
            uriPath = uriPath.substring(0,idx); // trim trailing slash
            idx = uriPath.lastIndexOf('/'); // move idx to parent context
        }

        return uriPath.substring(0, idx)+"/";
    }
}