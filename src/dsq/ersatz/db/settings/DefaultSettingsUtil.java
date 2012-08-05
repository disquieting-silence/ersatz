package dsq.ersatz.db.settings;

import android.database.sqlite.SQLiteDatabase;

public class DefaultSettingsUtil implements SettingsUtil {
    
    public void setCooloff(SQLiteDatabase db, long time) {
        SettingsAdapter adapter = new DefaultSettingsAdapter(db);
        adapter.set(new Settings(time));
    }

    public long getCooloff(SQLiteDatabase db) {
        SettingsAdapter adapter = new DefaultSettingsAdapter(db);
        return adapter.get().cooloff;
    }
}
