import javax.sql.rowset.serial.SerialException;
import java.sql.*;
import java.io.*;

// related URL: https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8279341
public class Src009 {
    private char buf[];
    private long len;

    public Src009(char[] chars) {
    }

    public static void main(String[] args) throws SerialException {
        long long1 = -1L;
        String string2 = "{\"key\":null}";
        int int3 = -1;
        int int4 = -1;
        char[] charArray5 = {'\''};
        Src009 src009 = new Src009(charArray5);
        src009.test(long1, string2, int3, int4);
    }

    public int setString(long pos, String str) throws SerialException {
        return (test(pos, str, 0, str.length()));
    }

    private void isValid() throws SerialException {
        if (buf == null) {
            throw new SerialException("Error: You cannot call a method on a "
                    + "SerialClob instance once free() has been called.");
        }
    }

    public int test(long pos, String str, int offset, int length)
            throws SerialException {
        isValid();
        if (offset < 0 || offset > str.length()) {
            throw new SerialException("Invalid offset in String object set");
        }

        if (length < 0) {
            throw new SerialException("Invalid arguments: length cannot be "
                    + "negative");
        }

        if (pos < 1 || pos > len + 1) {
            throw new SerialException("Invalid position in Clob object set");
        }

        if (length > str.length() - offset) {
            // need check to ensure length + offset !> str.length
            throw new SerialException("Invalid OffSet. Cannot have combined offset " +
                    " and length that is greater than the length of str");
        }

        if (pos - 1 + length > Integer.MAX_VALUE) {
            throw new SerialException("Invalid length. Cannot have combined pos " +
                    "and length that is greater than Integer.MAX_VALUE");
        }

        pos--;  //values in the array are at position one less
        if (pos + length > len) {
            len = pos + length;
            char[] newbuf = new char[(int)len];
            System.arraycopy(buf, 0, newbuf, 0, (int)pos);
            buf = newbuf;
        }

        String temp = str.substring(offset, offset + length);
        char cPattern[] = temp.toCharArray();
        System.arraycopy(cPattern, 0, buf, (int)pos, length);
        return length;
    }
}