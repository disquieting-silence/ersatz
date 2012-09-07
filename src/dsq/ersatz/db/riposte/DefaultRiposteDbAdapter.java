package dsq.ersatz.db.riposte;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import dsq.ersatz.db.target.TargetTable;
import dsq.thedroid.db.DbAccess;
import dsq.thedroid.db.DefaultDbAccess;

import static dsq.ersatz.db.riposte.RiposteTable.*;

public class DefaultRiposteDbAdapter implements RiposteDbAdapter {
    
    private DbAccess dbAccess = new DefaultDbAccess();
    private final SQLiteDatabase db;

    public DefaultRiposteDbAdapter(SQLiteDatabase db) {
        this.db = db;
    }

    public long create(String name, String message) {
        ContentValues values = values(name, message);
        values.put(ENABLED, 0);
        return dbAccess.create(db, TABLE, values);
    }

    public boolean deleteById(long id) {
        // FIX 23/01/12 Manual cascading.
        boolean cascaded = dbAccess.delete(db, TargetTable.TABLE, TargetTable.RIPOSTE_ID, "" + id);
        Log.v("ERSATZ", "Cascaded: " + cascaded);
        return dbAccess.deleteById(db, TABLE, id);
    }

    public Cursor fetchAll() {
        return dbAccess.fetchAll(db, TABLE, ALL_COLUMNS);
    }

    public Cursor fetchById(long id) throws SQLException {
        return dbAccess.fetchById(db, TABLE, ALL_COLUMNS, id);
    }

    public boolean update(long id, String name, String message) {
        ContentValues values = values(name, message);
        return dbAccess.update(db, TABLE, id, values);
    }

    public boolean update(long id, boolean enabled) {
        ContentValues values = enabledValues(enabled);
        return dbAccess.update(db, TABLE, id, values);
    }

    private ContentValues enabledValues(boolean enabled) {
        ContentValues values = new ContentValues();
        values.put(ENABLED, boolToInt(enabled));
        return values;
    }

    public boolean updateAll(boolean enabled) {
        return dbAccess.updateAll(db, TABLE, enabledValues(enabled));
    }

    public boolean hasActive() {
        // FIX 8/09/12 SQL Injection. Look into this properly.
        final Cursor cursor = dbAccess.query(db, "SELECT * FROM " + TABLE + " WHERE ENABLED = " + boolToInt(true), new String[0]);
        final boolean r = cursor.moveToFirst();
        cursor.close();
        return r;
    }

    private ContentValues values(String name, String message) {
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(MESSAGE, message);
        return values;
    }

    private int boolToInt(boolean value) {
        return value ? 1 : 0;
    }
}
