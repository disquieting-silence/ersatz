package dsq.ersatz.db.riposte;

import dsq.thedroid.db.Table;

public class RiposteTable implements Table {

    public static final String TABLE = "RIPOSTE";

    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String MESSAGE = "message";
    public static final String ENABLED = "enabled";
    public static final String[] ALL_COLUMNS = new String[] { ID, NAME, MESSAGE, ENABLED };

    public static final String CREATE_STATEMENT =
        "CREATE TABLE " + TABLE + "(" +
            ID + " integer primary key autoincrement, " +
            NAME + " text not null, " +
            MESSAGE + " text not null, " +
            ENABLED + " integer not null" +
        ");";

    public static final String DROP_STATEMENT = "DROP TABLE IF EXISTS " + TABLE;

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
