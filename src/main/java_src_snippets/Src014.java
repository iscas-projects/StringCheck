// related URL: https://gitee.com/openeuler/bishengjdk-8/issues/I4MWI1?from=project-issue
public class Src014 {
    public static void main(String[] args) {
        test(new String[] {});
    }
    public static void test(String[] args) {
        if (args.length != 1) {
        }

        String[] arg2 = args[0].split(":");
        if (arg2.length != 2) {
        }
        String hostname = arg2[0];
        int port = -1;
        try {
            port = Integer.parseInt(arg2[1]);
        } catch (NumberFormatException x) {
        }
        if (port < 0) {
        }

    }
}