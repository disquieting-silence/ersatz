package dsq.ersatz.db.plant;

import android.database.DatabaseUtils;
import dsq.thedroid.db.Table;

public class PlantTable implements Table {

    public static final String TABLE = "PLANT";
    
    public static final String ID = "_id";
    public static final String TAG = "tag";
    public static final String TEMPLATE = "template";

    public static final String[] ALL_COLUMNS = new String[] { ID, TAG, TEMPLATE };

    public static final String CREATE_STATEMENT =
        "CREATE TABLE " + TABLE + "(" +
                ID + " integer primary key autoincrement, " +
                TAG + " text not null, " +
                TEMPLATE + " text not null" +
        ");";
    
  
    public String create() {
        return CREATE_STATEMENT;
    }

    public String name() {
        return TABLE;
    }

    public static final String DROP_STATEMENT = "DROP TABLE IF EXISTS " + TABLE;
    
    private String insert(String rawTag, String rawTemplate) {
        String tag = DatabaseUtils.sqlEscapeString(rawTag);
        String template = DatabaseUtils.sqlEscapeString(rawTemplate);
        return "INSERT INTO " + TABLE + "(" + TAG + ", " + TEMPLATE + ") VALUES(" + tag + ", " + template + ");";
    }

    public String drop() {
        return DROP_STATEMENT;

    }

    public String[] load() {
        return new String[] {
            insert("Open Street Maps", "http://www.openstreetmap.org/?mlat=#LAT&mlon=#LONG&zoom=15&layers=M"),
            insert("Unavailable.", "Hi #NAME. Thanks for sending me a message. I'm unavailable though, so you just wasted your money.")
        };
    }

    public String[] allColumns() {
        return ALL_COLUMNS;
    }
}