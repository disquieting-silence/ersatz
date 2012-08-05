package dsq.ersatz.db.settings;

import dsq.thedroid.db.Table;

public class SettingsTable implements Table {
    
    public static final String TABLE = "ERSATZ_SETTINGS";
    public static final String COOLOFF = "cooloff";

    // FIX 26/01/12 Make the other ones private as well.
    private static final String CREATE_STATEMENT = 
        "CREATE TABLE " + TABLE + " (" + 
            COOLOFF + " integer not null " + 
        ")";

    private static final String DROP_STATEMENT = "DROP TABLE IF EXISTS " + TABLE;
    private final String[] ALL_COLUMNS = new String[]{COOLOFF};


    public String create() {
        return CREATE_STATEMENT;
    }

    public String name() {
        return TABLE;
    }

    public String drop() {
        return DROP_STATEMENT;
    }

    public String[] load() {
        return new String[0];
    }

    public String[] allColumns() {
        return ALL_COLUMNS;
    }
}