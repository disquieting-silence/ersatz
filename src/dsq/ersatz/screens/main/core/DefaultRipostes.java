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
import dsq.ersatz.data.data.RiposteId;
import dsq.ersatz.db.riposte.*;
import dsq.ersatz.ui.list.DefaultSelectableDataList;
import dsq.ersatz.ui.list.SelectableDataList;
import dsq.thedroid.ui.ComponentIndex;
import dsq.thedroid.ui.DefaultLists;
import dsq.thedroid.ui.ListMapping;
import dsq.thedroid.ui.Lists;

public class DefaultRipostes implements Ripostes {
    private final Lists lists = new DefaultLists();

    private final ListActivity activity;
    private final RiposteDbAdapter adapter;

    private final SelectableDataList list;

    private final RiposteBroadcast broadcast = new DefaultRiposteBroadcast();

    public DefaultRipostes(final ListActivity activity, final SQLiteDatabase db) {
        this.activity = activity;
        adapter = new DefaultRiposteDbAdapter(db);
        list = new DefaultSelectableDataList(adapter, new DefaultRiposteViewBinder(adapter),
            activity, R.layout.riposte_row, new RiposteList());
    }

    public RiposteId nu() {
        final long id = adapter.create("", "");
        return new RiposteId(id);
    }

    public void refresh() {
        list.refresh();
//        SimpleCursorAdapter.ViewBinder view = new DefaultRiposteViewBinder(adapter);
//        final ListView listView = activity.getListView();
//        final ComponentIndex index = new ComponentIndex(R.layout.riposte_row);
//        updateList(view, listView, index);
    }

//    private void updateList(final SimpleCursorAdapter.ViewBinder binder, final ListView listView, final ComponentIndex index) {
//        final Cursor cursor = adapter.fetchAll();
//        final RiposteList mapping = new RiposteList();
//        final int[] dest = ids(mapping, mapping.dest());
//        SimpleCursorAdapter c = new SimpleCursorAdapter(activity, index.value, cursor, mapping.source(), dest);
//        c.setViewBinder(binder);
//        listView.setAdapter(c);
//
//        // Not sure if this replaces or not.
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(final AdapterView<?> adapterView, final View view, final int i, final long l) {
//                final long item = listView.getItemIdAtPosition(i);
//                Log.v("ERSATZ", String.valueOf(item));
//                adapter.update(item, !adapter.isEnabled(item));
//                refresh();
//            }
//        });
////        lists.refreshAll(activity, adapter, listView, index, mapping, view);
//    }
//
//    private int[] ids(final ListMapping mapping, final ComponentIndex[] destUis) {
//        final int[] destUiIds = new int [mapping.dest().length];
//        for (int i = 0; i < destUis.length; i++) {
//            ComponentIndex destUi = destUis[i];
//            destUiIds[i] = destUi.value;
//        }
//        return destUiIds;
//    }

    public void delete(final RiposteId rId) {
        adapter.deleteById(rId.value);
    }

    public void broadcast() {
        broadcast.send(activity, adapter);
    }

    /*
    activity.startManagingCursor(cursor);
        final int[] dest = ids(mapping, mapping.dest());
        SimpleCursorAdapter c = new SimpleCursorAdapter(activity, rowUi.value, cursor, mapping.source(), dest);
        c.setViewBinder(binder);
        view.setAdapter(c);
        view.setOnItemClickListener(new ListView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapterView.showContextMenuForChild(view);
            }
        });
     */
}
