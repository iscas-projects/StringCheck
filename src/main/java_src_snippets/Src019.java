import java.util.LinkedHashMap;
import java.util.Map;

// related URL: https://github.com/apache/groovy/pull/1643
public class Src019 {
    public static void main(String[] args) {
        testGetIvyParts1();
        testGetIvyParts2();
        testGetIvyParts3();
        testGetIvyParts4();
    }
    public static void testGetIvyParts1(){
        String allStr = "@M";
        Map<String, Object> ivyParts = Src019.test(allStr);
        assert ivyParts.size() == 3;
    }

    public static void testGetIvyParts2(){
        String allStr = "a@";
        Map<String, Object> ivyParts = Src019.test(allStr);
        assert ivyParts.size() == 2;
    }

    public static void testGetIvyParts3(){
        String allStr = "@";
        Map<String, Object> ivyParts = Src019.test(allStr);
        assert ivyParts.size() == 2;
    }

    public static void testGetIvyParts4(){
        String allStr = ":k:@M";
        Map<String, Object> ivyParts = Src019.test(allStr);
        assert ivyParts.size() == 4;
    }

    public static Map<String, Object> test(String allstr) {
        Map<String, Object> result = new LinkedHashMap<String, Object>();
        String ext = "";
        String[] parts;
        if (allstr.contains("@")) {
            parts = allstr.split("@");
            if (parts.length > 2) return result;
            allstr = parts[0];
            ext = parts[1];
        }
        parts = allstr.split(":");
        if (parts.length > 4) return result;
        if (parts.length > 3) result.put("classifier", parts[3]);
        if (parts.length > 2) result.put("version", parts[2]);
        else result.put("version", "*");
        if (ext.length() > 0) result.put("ext", ext);
        result.put("module", parts[1]);
        result.put("group", parts[0]);
        return result;
    }
}