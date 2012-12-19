package dsq.ersatz.screens.main.core;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import dsq.ersatz.R;
import dsq.ersatz.data.data.RiposteId;
import dsq.ersatz.db.riposte.*;
import dsq.thedroid.ui.ComponentIndex;
import dsq.thedroid.ui.DefaultLists;
import dsq.thedroid.ui.Lists;

public class DefaultRipostes implements Ripostes {
    private final Lists lists = new DefaultLists();

    private final ListActivity activity;
    private final RiposteDbAdapter adapter;

    private final RiposteBroadcast broadcast = new DefaultRiposteBroadcast();

    public DefaultRipostes(final ListActivity activity, final SQLiteDatabase db) {
        this.activity = activity;
        adapter = new DefaultRiposteDbAdapter(db);
    }

    public RiposteId nu() {
        final long id = adapter.create("", "");
        return new RiposteId(id);
    }

    public void refresh() {
        SimpleCursorAdapter.ViewBinder view = new DefaultRiposteViewBinder(adapter);
        final ListView listView = activity.getListView();
        final ComponentIndex index = new ComponentIndex(R.layout.riposte_row);
        lists.refreshAll(activity, adapter, listView, index, new RiposteList(), view);
    }

    public void delete(final RiposteId rId) {
        adapter.deleteById(rId.value);
    }

    public void broadcast() {
        broadcast.send(activity, adapter);
    }
}
