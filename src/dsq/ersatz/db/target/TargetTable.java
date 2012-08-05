package dsq.ersatz.db.target;

import dsq.thedroid.db.Table;

public class TargetTable implements Table {

    public static final String TABLE = "TARGET";

    public static final String ID = "_id";
    public static final String RIPOSTE_ID = "riposte_id";
    public static final String ID_ALIAS = "_id_target";
    public static final String PHONE_NUM = "phone_num";
    public static final String CONTACT_NAME = "contact_name";
    public static final String LAST_FIRED = "last_fired";
    public static final String[] ALL_COLUMNS = new String[] { ID, RIPOSTE_ID, PHONE_NUM, CONTACT_NAME, LAST_FIRED };

    public static final String CREATE_STATEMENT =
        "CREATE TABLE " + TABLE + "(" +
            ID + " integer primary key autoincrement, " +
            RIPOSTE_ID + " integer not null, " +
            PHONE_NUM + " text not null, " +
            CONTACT_NAME + " text not null, " +
            LAST_FIRED + " integer not null, " +
            "FOREIGN KEY (" + RIPOSTE_ID + ") references " + TargetTable.TABLE + "(" + TargetTable.ID + ") ON DELETE CASCADE" +
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
