package dsq.ersatz.db.general;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public interface DbLifecycle {
    SQLiteDatabase open(Context context) throws SQLException;
    void close();
}
