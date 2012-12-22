package dsq.ersatz.screens.main.core;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import dsq.ersatz.R;
import dsq.ersatz.action.IdAction;
import dsq.ersatz.data.data.RiposteId;
import dsq.ersatz.db.riposte.*;
import dsq.ersatz.screens.main.Rabbit;
import dsq.ersatz.ui.list.DefaultSelectableDataList;
import dsq.ersatz.ui.list.SelectableDataList;
import dsq.thedroid.ui.ComponentIndex;
import dsq.thedroid.ui.DefaultLists;
import dsq.thedroid.ui.ListMapping;
import dsq.thedroid.ui.Lists;

public class DefaultRipostes implements Ripostes {

    private final ListActivity activity;
    private final RiposteDbAdapter adapter;

    private final SelectableDataList<RiposteV> list;

    private final RiposteBroadcast broadcast = new DefaultRiposteBroadcast();

    public DefaultRipostes(final ListActivity activity, final SQLiteDatabase db, final Rabbit<RiposteV> rabbit) {
        this.activity = activity;
        adapter = new DefaultRiposteDbAdapter(db);
        final RiposteListDefinition definition = new DefaultRiposteListDefinition(activity);
        list = new DefaultSelectableDataList<RiposteV>(activity, adapter, definition, R.layout.riposte_row);

        rabbit.setList(list);
    }

    public RiposteId nu() {
        final long id = adapter.create("", "");
        return new RiposteId(id);
    }

    public void refresh() {
        list.refresh();
    }


    public void delete(final RiposteId rId) {
        adapter.deleteById(rId.value);
    }

    public void broadcast() {
        broadcast.send(activity, adapter);
    }

    public void toggleEnabled(final RiposteId riposteId) {
        final boolean enabled = adapter.isEnabled(riposteId.value);
        update(riposteId, !enabled);
        broadcast();
    }

    private void update(final RiposteId id, final boolean enabled) {
        adapter.update(id.value, enabled);
    }
}
