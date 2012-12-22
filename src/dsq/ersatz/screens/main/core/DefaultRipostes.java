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
import dsq.ersatz.ui.list.DefaultSelectableDataList;
import dsq.ersatz.ui.list.SelectableDataList;
import dsq.thedroid.ui.ComponentIndex;
import dsq.thedroid.ui.DefaultLists;
import dsq.thedroid.ui.ListMapping;
import dsq.thedroid.ui.Lists;

public class DefaultRipostes implements Ripostes {

    private final ListActivity activity;
    private final RiposteDbAdapter adapter;

    private final SelectableDataList list;

    private final RiposteBroadcast broadcast = new DefaultRiposteBroadcast();

    public DefaultRipostes(final ListActivity activity, final SQLiteDatabase db) {
        this.activity = activity;
        adapter = new DefaultRiposteDbAdapter(db);
        final DefaultRiposteViewBinder binder = new DefaultRiposteViewBinder(adapter);
        list = new DefaultSelectableDataList(adapter, binder,
            activity, R.layout.riposte_row, new RiposteList());
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

    public void onSelect(final IdAction action) {
        list.onSelect(action);
    }
}
