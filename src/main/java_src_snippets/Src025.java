import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

// related URL: https://github.com/openjdk/jdk/commit/96a1333c77a8473d9ae06f304b9fdbe21212bd18
public class Src025 {
    public static void main(String[] args) throws IOException {
        test(new File("").toPath());
    }

    private static String nullSafeFileName(Path file) throws IOException {
        Path filename = file.getFileName();
        if (filename == null) {
            throw new IOException("Path has no file name");
        }
        return filename.toString();
    }

    public static String test(Path file) throws IOException {
        String f = nullSafeFileName(file);
        return f.substring(0, f.length() - ".jfc".length());
    }
}