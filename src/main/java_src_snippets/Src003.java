import java.util.ArrayList;

// Bug related URL: https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8279129
public class Src003 {

    private ArrayList<String> arguments;

    public static void main(String[] args) throws Exception {
        String[] stringArray1 = {"a ", "^[1]([3-9])[0-9]{9}$", "", ".\\a.txt"};
        Src003 src003 = new Src003();
        src003.test(stringArray1);
    }

    public void test(String[] args) throws Exception {
        for(int i = 0; i < args.length; ++i) {
            if (args[i].charAt(0) == '-') {
                int j = this.parseArgument(args, i);
                if (j == 0) {
                    throw new Exception();
                }

                i += j;
            } else {
                this.arguments.add(args[i]);
            }
        }

    }

    private int parseArgument(String[] args, int i) {
        return 0;
    }
}
