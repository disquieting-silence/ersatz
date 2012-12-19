package dsq.ersatz.screens.edit.core;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;
import dsq.ersatz.R;
import dsq.ersatz.db.riposte.DefaultRiposteDbAdapter;
import dsq.ersatz.db.riposte.RiposteDbAdapter;
import dsq.ersatz.db.riposte.RiposteTable;
import dsq.ersatz.screens.edit.data.EditStruct;
import dsq.ersatz.screens.edit.data.RiposteId;
import dsq.thedroid.db.DbUtils;
import dsq.thedroid.util.CursorOperation;
import dsq.thedroid.util.DefaultIdCursor;
import dsq.thedroid.util.IdCursor;

public class DefaultRiposteTy implements RiposteTy {

    private final Activity activity;
    private final RiposteDbAdapter adapter;
    private final EditUi ui;
    private final RiposteId id;

    private final IdCursor cursors = new DefaultIdCursor();

    public DefaultRiposteTy(final Activity activity, final SQLiteDatabase db, final RiposteId id) {
        this.activity = activity;
        this.id = id;
        adapter = new DefaultRiposteDbAdapter(db);
        ui = setupUi(activity);
    }

    private DefaultEditUi setupUi(final Activity activity) {
        final EditText txtName = (EditText)activity.findViewById(R.id.name);
        final EditText txtMessage = (EditText)activity.findViewById(R.id.message);
        return new DefaultEditUi(txtName, txtMessage);
    }

    public void readFromDb() {
        cursors.go(adapter, activity, id.value, new CursorOperation() {
            public void go(long id, Cursor cursor) {
                String name = DbUtils.getColumn(cursor, RiposteTable.NAME);
                String message = DbUtils.getColumn(cursor, RiposteTable.MESSAGE);
                ui.set(name, message);
            }
        });
    }

    public void writeToDb() {
        final EditStruct values = ui.get();
        Log.v("ERSATZ", "Saving message: " + values.message);
        adapter.update(id.value, values.name, values.message);
    }
}
