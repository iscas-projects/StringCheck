import com.sun.org.apache.bcel.internal.classfile.ClassParser;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;

import java.io.File;
import java.io.IOException;

// related URL: https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8279342

public class Src008 {
    public static void main(String[] args) throws IOException {
        String[] stringArray0 = {"i53tfnIq_68", "", " #", "^[1]([3-9])[0-9]{9}$"};
        test(stringArray0);
    }
    public Src008(JavaClass javaClass, String dir) {
    }

    public static void test(final String[] argv) throws IOException {
        final String[] fileName = new String[argv.length];
        int files = 0;
        ClassParser parser = null;
        JavaClass javaClass = null;
        String zipFile = null;
        final char sep = File.separatorChar;
        String dir = "." + sep; // Where to store HTML files
        /*
         * Parse command line arguments.
         */
        for (int i = 0; i < argv.length; i++) {
            if (argv[i].charAt(0) == '-') { // command line switch
                if (argv[i].equals("-d")) { // Specify target directory, default '.'
                    dir = argv[++i];
                    if (!dir.endsWith("" + sep)) {
                        dir = dir + sep;
                    }
                    final File store = new File(dir);
                    if (!store.isDirectory()) {
                        final boolean created = store.mkdirs(); // Create target directory if necessary
                        if (!created && !store.isDirectory()) {
                            System.out.println("Tried to create the directory " + dir + " but failed");
                        }
                    }
                } else if (argv[i].equals("-zip")) {
                    zipFile = argv[++i];
                } else {
                    System.out.println("Unknown option " + argv[i]);
                }
            } else {
                fileName[files++] = argv[i];
            }
        }
        if (files == 0) {
            System.err.println("Class2HTML: No input files specified.");
        } else { // Loop through files ...
            for (int i = 0; i < files; i++) {
                System.out.print("Processing " + fileName[i] + "...");
                if (zipFile == null) {
                    parser = new ClassParser(fileName[i]); // Create parser object from file
                } else {
                    parser = new ClassParser(zipFile, fileName[i]); // Create parser object from zip file
                }
                javaClass = parser.parse();
                new Src008(javaClass, dir);
                System.out.println("Done.");
            }
        }
    }
}