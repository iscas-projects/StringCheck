class Src002 {
    public static void main(String[] args) {
        test("abc");
    }
    static public String test(String name) {
        int index = name.indexOf('[');
        if (index >= 0) {
            String array = name.substring(index);
            name = name.substring(0, index);
            while (!array.equals("")) {
                name = name + "[]";
                array = array.substring(array.indexOf(']') + 1);
            }
        }
        return name;
    }
}