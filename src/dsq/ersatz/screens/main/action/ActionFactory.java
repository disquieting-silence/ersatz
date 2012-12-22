package dsq.ersatz.screens.main.action;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import dsq.ersatz.screens.main.Rabbit;

public interface ActionFactory {
    UiActions nu(final ListActivity activity, final SQLiteDatabase db, final Rabbit rabbit);
}
