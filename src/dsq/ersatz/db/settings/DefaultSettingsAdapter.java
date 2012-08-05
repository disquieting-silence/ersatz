package dsq.ersatz.db.settings;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import dsq.thedroid.db.DbAccess;
import dsq.thedroid.db.DbUtils;
import dsq.thedroid.db.DefaultDbAccess;

public class DefaultSettingsAdapter implements SettingsAdapter {
    
    private final SQLiteDatabase db;
    private final DbAccess dbAccess = new DefaultDbAccess();

    public DefaultSettingsAdapter(SQLiteDatabase db) {
        this.db = db;
    }

    public boolean set(Settings config) {
        db.execSQL("DELETE FROM " + SettingsTable.TABLE);
        ContentValues values = new ContentValues();
        values.put(SettingsTable.COOLOFF, config.cooloff);
        return db.insert(SettingsTable.TABLE, null, values) > 0;
    }

    public Settings get() {
        Cursor cursor = dbAccess.query(db, "SELECT * FROM " + SettingsTable.TABLE, new String[0]);
        long cooloff = cursor.moveToFirst() ? readValue(cursor) : 0;
        Settings config = new Settings(cooloff);
        cursor.close();
        return config;
    }

    private long readValue(Cursor cursor) {
        return Long.parseLong(DbUtils.getColumn(cursor, SettingsTable.COOLOFF));
    }
}
