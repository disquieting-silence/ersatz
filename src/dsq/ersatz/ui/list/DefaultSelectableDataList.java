package dsq.ersatz.ui.list;

import android.app.ListActivity;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import dsq.thedroid.db.DbAdapter;
import dsq.thedroid.ui.ComponentIndex;

public class DefaultSelectableDataList<A> implements SelectableDataList<A> {

    public static final int HIGHLIGHT_COLOUR = 0x55CADBEE;
    private final DbAdapter adapter;
    private final ListActivity activity;
    private final ComponentIndex row;
    private final ListDefinition<A> definition;
    private final ListView listView;

    private long selectedId = -1;

    private ItemAction<A> selectAction;

    public DefaultSelectableDataList(final ListActivity activity, final DbAdapter adapter, final ListDefinition<A> definition, final int row) {
        this.adapter = adapter;
        this.activity = activity;
        this.row = new ComponentIndex(row);
        this.definition = definition;
        this.listView = activity.getListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView<?> adapterView, final View view, final int i, final long l) {
                selectedId = listView.getItemIdAtPosition(i);
                notifySelect();
                refresh();
            }
        });
    }

    private void notifySelect() {
        if (selectedId > -1) {
            final Cursor cursor = adapter.fetchById(selectedId);
            final int count = cursor.getColumnCount();
            if (count > 0) {
                final A data = definition.build(cursor);
                selectAction.run(selectedId, data);
            }
        }
    }

    public long selected() {
        return selectedId;
    }

    public void refresh() {
        final Cursor cursor = adapter.fetchAll();
        final String[] sources = definition.sources();
        final int[] dests = definition.destinations();
        final SimpleCursorAdapter c = new SimpleCursorAdapter(activity, row.value, cursor, sources, dests);
        c.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            public boolean setViewValue(final View view, final Cursor cursor, final int i) {
                final boolean result = definition.setViewValue(view, cursor, i);
                // FIX 22/12/12 Not convinced that I can just get the 0th position.
                final int id = cursor.getInt(0);
                highlight(view, id == selectedId ? HIGHLIGHT_COLOUR : 0xFF00000);
                return result;
            }
        });
        listView.setAdapter(c);
        notifySelect();
    }

    private void highlight(final View cell, final int colour) {
        final LinearLayout parent = (LinearLayout) cell.getParent();
        parent.setBackgroundColor(colour);
    }

    public void onSelect(final ItemAction<A> action) {
        selectAction = action;
    }
}