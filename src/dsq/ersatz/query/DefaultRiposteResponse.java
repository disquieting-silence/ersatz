package dsq.ersatz.query;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import dsq.ersatz.db.general.DbLifecycle;
import dsq.ersatz.db.general.DefaultDbLifecycle;
import dsq.ersatz.db.riposte.Riposte;
import dsq.ersatz.db.riposte.RiposteTable;
import dsq.ersatz.db.settings.DefaultSettingsUtil;
import dsq.ersatz.db.settings.SettingsUtil;
import dsq.ersatz.db.target.TargetTable;
import dsq.thedroid.db.DbAccess;
import dsq.thedroid.db.DbUtils;
import dsq.thedroid.db.DefaultDbAccess;

import java.util.ArrayList;
import java.util.List;

public class DefaultRiposteResponse implements RiposteResponse {
    
    private final DbLifecycle lifecycle = new DefaultDbLifecycle();
    private final DbAccess dbAccess = new DefaultDbAccess();
    private final SettingsUtil util = new DefaultSettingsUtil();
    private static final long MILLIS_IN_MINUTE = 60 * 1000L;

    public List<Riposte> find(Context context, String localNumber, long time) {
        SQLiteDatabase db = lifecycle.open(context);

        long cooloff = util.getCooloff(db) * MILLIS_IN_MINUTE;

        // FIX 26/01/12 Find a way to use parameter injection.
        String sql = 
                "SELECT " + RiposteTable.TABLE + "." + RiposteTable.ID + ", " + 
                TargetTable.TABLE + "." + TargetTable.ID + " AS " + TargetTable.ID_ALIAS + ", " + 
                RiposteTable.NAME + ", " + RiposteTable.MESSAGE + ", " + TargetTable.PHONE_NUM + " " + "" +
                
                "FROM " +
                RiposteTable.TABLE + " inner join " + TargetTable.TABLE + 
                " on (" + RiposteTable.TABLE + "." + RiposteTable.ID + " = " + TargetTable.RIPOSTE_ID + ")" + " " +
                
                "WHERE (" + RiposteTable.ENABLED + "=1) AND (" + TargetTable.PHONE_NUM + "='" + localNumber + "') AND " + 
                "(" + TargetTable.LAST_FIRED + " < " + time + " - " + cooloff + ")";

        Cursor cursor = dbAccess.query(db, sql, new String[0]);
        List<Riposte> r = ripostes(cursor);
        lifecycle.close();
        return r;
    }

    public boolean update(Context context, List<Riposte> ripostes, long time) {
        SQLiteDatabase db = lifecycle.open(context);
        
        boolean success = false;
        ContentValues values = new ContentValues();
        values.put(TargetTable.LAST_FIRED, time);
        for (Riposte riposte : ripostes) {
            success = success || dbAccess.update(db, TargetTable.TABLE, riposte.targetId, values);
        }
        
        lifecycle.close();
        return success;
    }

    private List<Riposte> ripostes(Cursor cursor) {
        List<Riposte> r = new ArrayList<Riposte>();
        if (cursor.moveToFirst()) {
            do {
                // FIX 26/01/12 Move out.
                long id = Long.parseLong(DbUtils.getColumn(cursor, RiposteTable.ID));
                String name = DbUtils.getColumn(cursor, RiposteTable.NAME);
                String message = DbUtils.getColumn(cursor, RiposteTable.MESSAGE);
                long targetId = Long.parseLong(DbUtils.getColumn(cursor, TargetTable.ID_ALIAS));
                Riposte riposte = new Riposte(id, name, message, targetId);
                r.add(riposte);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return r;
    }
}
