package dsq.ersatz.db.target;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import dsq.thedroid.db.DbAccess;
import dsq.thedroid.db.DbUtils;
import dsq.thedroid.db.DefaultDbAccess;

import java.util.ArrayList;
import java.util.List;

public class DefaultTargetDbAdapter implements TargetDbAdapter {
    
    private final SQLiteDatabase db;
    private DbAccess dbAccess = new DefaultDbAccess();

    public DefaultTargetDbAdapter(SQLiteDatabase db) {
        this.db = db;
    }

    public long create(long riposteId, String phone, String contactName) {
        ContentValues values = new ContentValues();
        values.put(TargetTable.RIPOSTE_ID, riposteId);
        values.put(TargetTable.PHONE_NUM, phone);
        values.put(TargetTable.CONTACT_NAME, contactName);
        values.put(TargetTable.LAST_FIRED, 0);
        return dbAccess.create(db, TargetTable.TABLE, values);
    }

    public boolean contains(long id, String phone) {
        return dbAccess.contains(db, TargetTable.TABLE, TargetTable.ALL_COLUMNS, "(" + TargetTable.RIPOSTE_ID + " = " + id + ") AND (" + TargetTable.PHONE_NUM + " = '" + phone + "')",
            new String[] {});
    }

    public boolean deleteById(long id) {
        return dbAccess.deleteById(db, TargetTable.TABLE, id);
    }

    public Cursor fetchAll() {
        return dbAccess.fetchAll(db, TargetTable.TABLE, TargetTable.ALL_COLUMNS);
    }

    public Cursor fetchById(long id) throws SQLException {
        return dbAccess.fetchById(db, TargetTable.TABLE, TargetTable.ALL_COLUMNS, id);
    }
    
    public Cursor fetchByRiposteId(long id) throws SQLException {
        return dbAccess.fetch(db, TargetTable.TABLE, TargetTable.ALL_COLUMNS, TargetTable.RIPOSTE_ID + "= ?", new String[] { id + "" });
    }

    // FIX 24/01/12 Probably move out.
    public List<Target> getTargets(long riposteId) {
        List<Target> r = new ArrayList<Target>();
        Cursor cursor = dbAccess.fetch(db, TargetTable.TABLE, TargetTable.ALL_COLUMNS, TargetTable.RIPOSTE_ID + " = ?", new String[]{"" + riposteId});
        if (cursor.moveToFirst()) {
            do {
                long id = Long.parseLong(DbUtils.getColumn(cursor, TargetTable.ID));
                String name = DbUtils.getColumn(cursor, TargetTable.CONTACT_NAME);
                String number = DbUtils.getColumn(cursor, TargetTable.PHONE_NUM);
                r.add(new Target(id, name, number));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return r;
    }

    public boolean deleteTargets(long riposteId) {
        return dbAccess.delete(db, TargetTable.TABLE, TargetTable.RIPOSTE_ID, "" + riposteId);
    }

}
