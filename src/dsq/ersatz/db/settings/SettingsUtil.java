package dsq.ersatz.db.settings;

import android.database.sqlite.SQLiteDatabase;

public interface SettingsUtil {
    void setCooloff(SQLiteDatabase db, long time);
    long getCooloff(SQLiteDatabase db);
}
