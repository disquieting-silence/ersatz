package dsq.ersatz.screens.edit;

import android.app.Activity;
import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;

public interface ActionFactory {
    UiActions nu(final ListActivity activity, final SQLiteDatabase db, final RiposteId id);
}
