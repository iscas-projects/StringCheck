class Src001 {
    public static void main(String[] args) {
        test("xxpointer(id(");
    }
    public static String test(String uri) {
        if (uri.length() == 0) {
            return null;
        }
        String id = uri.substring(1);
        if (id != null && id.startsWith("xpointer(id(")) {
            int i1 = id.indexOf('\'');
            int i2 = id.indexOf('\'', i1+1);
            id = id.substring(i1+1, i2);
        }
        return id;
    }
}