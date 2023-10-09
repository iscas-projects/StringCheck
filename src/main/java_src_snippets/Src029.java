import java.util.HashSet;

// related URL: https://github.com/ryenus/hsqldb/pull/1
public class Src029 {
    public static final int NONE          = 0;
    public static final int SQL_EXCEPTION = 1;
    public static final int SQL_WARNING   = 2;
    public static final int SQL_NOT_FOUND = 3;
    public static final int SQL_STATE     = 4;
    private HashSet conditionGroups = new HashSet();
    private HashSet conditionStates = new HashSet();
    private Statement         statement;

    public static void main(String[] args) {
        (new Src029()).test("");
    }

    public boolean test(String sqlState) {
        if (conditionStates.contains(sqlState)) {
            return true;
        }

        String conditionClass = sqlState.substring(0, 2);

        if (conditionStates.contains(conditionClass)) {
            return true;
        }

        if (conditionClass.equals("01")) {
            return conditionGroups.contains(SQL_WARNING);
        }

        if (conditionClass.equals("02")) {
            return conditionGroups.contains(SQL_NOT_FOUND);
        }

        return conditionGroups.contains(SQL_EXCEPTION);
    }
}