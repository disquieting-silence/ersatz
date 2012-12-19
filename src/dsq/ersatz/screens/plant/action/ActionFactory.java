package dsq.ersatz.screens.plant.action;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;

public interface ActionFactory {
    UiActions nu(final Activity activity, final SQLiteDatabase db);
}
