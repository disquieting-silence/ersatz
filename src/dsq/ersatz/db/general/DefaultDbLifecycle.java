package dsq.ersatz.db.general;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class DefaultDbLifecycle implements DbLifecycle {
    private DatabaseHelper helper;

    public SQLiteDatabase open(Context context) throws SQLException {
        helper = new DatabaseHelper(context);
        return helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }
}
