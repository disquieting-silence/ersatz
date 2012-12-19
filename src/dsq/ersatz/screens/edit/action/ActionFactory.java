package dsq.ersatz.screens.edit.action;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import dsq.ersatz.screens.edit.data.RiposteId;

public interface ActionFactory {
    UiActions nu(final ListActivity activity, final SQLiteDatabase db, final RiposteId id);
}
