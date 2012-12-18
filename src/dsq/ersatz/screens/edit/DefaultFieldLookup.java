package dsq.ersatz.screens.edit;

import android.app.Activity;
import android.database.Cursor;
import dsq.ersatz.db.riposte.RiposteDbAdapter;
import dsq.ersatz.db.riposte.RiposteTable;
import dsq.thedroid.db.DbUtils;
import dsq.thedroid.util.CursorOperation;
import dsq.thedroid.util.DefaultIdCursor;
import dsq.thedroid.util.IdCursor;

public class DefaultFieldLookup implements FieldLookup {

    private final IdCursor cursors = new DefaultIdCursor();

    public void load(final Activity activity, final RiposteDbAdapter adapter, final EditUi ui, final long id) {
        cursors.go(adapter, activity, id, new CursorOperation() {
            public void go(long id, Cursor cursor) {
                String name = DbUtils.getColumn(cursor, RiposteTable.NAME);
                String message = DbUtils.getColumn(cursor, RiposteTable.MESSAGE);
                ui.set(name, message);
            }
        });
    }
}
