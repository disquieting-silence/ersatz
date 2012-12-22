package dsq.ersatz.ui.list;

import android.app.ListActivity;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import dsq.ersatz.action.IdAction;
import dsq.thedroid.db.DbAdapter;
import dsq.thedroid.ui.ComponentIndex;
import dsq.thedroid.ui.ListMapping;

public class DefaultSelectableDataList implements SelectableDataList {

    private final DbAdapter adapter;
    private final AdapterBinder binder;
    private final ListActivity activity;
    private final ListMapping mapping;
    private final ComponentIndex row;
    private final ListView listView;

    private long selectedId = -1;

    private IdAction selectAction;

    public DefaultSelectableDataList(final DbAdapter adapter, final AdapterBinder binder, final ListActivity activity, final int row, final ListMapping mapping) {
        this.adapter = adapter;
        this.binder = binder;
        this.activity = activity;
        this.row = new ComponentIndex(row);
        this.mapping = mapping;
        this.listView = activity.getListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int i, final long l) {
                selectedId = listView.getItemIdAtPosition(i);
                selectAction.run(selectedId);
                refresh();
            }
        });
    }

    public long selected() {
        return selectedId;
    }

    public void refresh() {
        final Cursor cursor = adapter.fetchAll();
        final int[] dest = ids(mapping, mapping.dest());
        final SimpleCursorAdapter c = new SimpleCursorAdapter(activity, row.value, cursor, mapping.source(), dest);
        c.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            public boolean setViewValue(final View view, final Cursor cursor, final int i) {
                final boolean result = binder.setViewValue(view, cursor, i);
                // FIX 22/12/12 Not convinced that I can just get the 0th position.
                final int id = cursor.getInt(0);
                if (id == selectedId) {
                    final LinearLayout parent = (LinearLayout) view.getParent();
                    parent.setBackgroundColor(0x55CCDD33);
                }
                return result;
            }
        });
        listView.setAdapter(c);
    }

    public void onSelect(final IdAction action) {
        selectAction = action;
    }

    private int[] ids(final ListMapping mapping, final ComponentIndex[] destUis) {
        final int[] destUiIds = new int [mapping.dest().length];
        for (int i = 0; i < destUis.length; i++) {
            final ComponentIndex destUi = destUis[i];
            destUiIds[i] = destUi.value;
        }
        return destUiIds;
    }
}