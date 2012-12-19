package dsq.ersatz.screens.main.action;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;

public interface ActionFactory {
    UiActions nu(final ListActivity activity, final SQLiteDatabase db);
}
