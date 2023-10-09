import com.sun.org.apache.xerces.internal.xni.Augmentations;
import com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier;

// related URL: https://bugs.java.com/bugdatabase/view_bug.do?bug_id=JDK-8279362
@SuppressWarnings("restriction")
public class Src013 {
    public static void main(String[] args) {
        String string1 = "";
        com.sun.org.apache.xerces.internal.xni.XMLResourceIdentifier xMLResourceIdentifier2 = null;
        String string3 = "a ";
        com.sun.org.apache.xerces.internal.xni.Augmentations augmentations4 = null;
        Src013 src013 = new Src013();
        src013.test(string1, xMLResourceIdentifier2, string3, augmentations4);
    }

    public void test(String name,
                            XMLResourceIdentifier identifier,
                            String encoding, Augmentations augs) {

        boolean dtdEntity = name.equals("[dtd]");
        if (dtdEntity) {
        }
        else if (name.charAt(0) == '%') {
        }

    } // startEntity(String,XMLResourceIdentifier,String)
}