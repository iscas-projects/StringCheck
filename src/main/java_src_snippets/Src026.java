import jdk.nashorn.internal.ir.Node;

// related URL: https://github.com/openjdk/jdk/commit/e67420cefce8c5c26274b02a5430d31411a404a9
public class Src026 {
    public static void main(String[] args) {
        test(null, "");
    }

    public static boolean test(Node startNode, String value) {
        String id = value.trim();
        if (id.charAt(0) == '#') {
            id = id.substring(1);
        }
        return false;
    }
}