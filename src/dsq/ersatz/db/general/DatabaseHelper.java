package dsq.ersatz.db.general;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.util.Log;
import dsq.ersatz.db.plant.PlantTable;
import dsq.ersatz.db.riposte.RiposteTable;
import dsq.ersatz.db.settings.SettingsTable;
import dsq.ersatz.db.target.TargetTable;
import dsq.thedroid.db.DbHelper;
import dsq.thedroid.db.DefaultDbHelper;
import dsq.thedroid.db.Table;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final SQLiteDatabase.CursorFactory LOG_CURSOR_FACTORY = new SQLiteDatabase.CursorFactory() {
        public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {

            Log.v("ERSATZ", query.toString());
            return new SQLiteCursor(db, masterQuery, editTable, query);
        }
    };
    
    private DbHelper helper = new DefaultDbHelper();

    private Table[] tables = new Table[] {
        new RiposteTable(),
        new TargetTable(),
        new SettingsTable(),
        new PlantTable()
    };

    private static final String DATABASE_NAME = "ersatz";
    private static final int DATABASE_VERSION = 25;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, LOG_CURSOR_FACTORY, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        helper.onCreate(sqLiteDatabase, tables);
    }

    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        helper.onUpgrade(sqLiteDatabase, tables, oldVersion, newVersion);
    }

}
