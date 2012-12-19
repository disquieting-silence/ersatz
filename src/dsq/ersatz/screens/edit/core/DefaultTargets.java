package dsq.ersatz.screens.edit.core;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import dsq.ersatz.R;
import dsq.ersatz.data.data.RiposteId;
import dsq.ersatz.data.data.TargetId;
import dsq.ersatz.db.target.*;
import dsq.thedroid.contacts.BasicContact;
import dsq.thedroid.ui.ComponentIndex;
import dsq.thedroid.ui.DefaultLists;
import dsq.thedroid.ui.Lists;

import java.util.List;

public class DefaultTargets implements Targets {
    private final TargetDbAdapter adapter;
    private final List<Target> original;
    private final RiposteId id;

    private final Lists lists = new DefaultLists();
    private final ListActivity activity;

    public DefaultTargets(final ListActivity activity, final SQLiteDatabase db, final RiposteId id) {
        this.activity = activity;
        adapter = new DefaultTargetDbAdapter(db);
        this.id = id;
        original = adapter.getTargets(id.value);
    }

    public void revert() {
        boolean delSuccess = adapter.deleteTargets(id.value);
        for (Target orig : original) {
            // FIX 24/01/12 Probably make the order of these arguments consistent.
            adapter.create(id.value, orig.number, orig.name);
        }
    }

    public void update(final BasicContact contact) {
        boolean isDuplicate = adapter.contains(id.value, contact.number);
        if (!isDuplicate) {
            adapter.create(id.value, contact.number, contact.name);
        }
    }

    public void refresh() {
        final SimpleCursorAdapter.ViewBinder view = new DefaultTargetViewBinder(adapter);
        final Cursor cursor = adapter.fetchByRiposteId(id.value);
        final ListView listView = activity.getListView();
        final ComponentIndex rowIndex = new ComponentIndex(R.layout.target_row);
        final TargetListMapping mapping = new TargetListMapping();
        lists.refresh(activity, cursor, listView, rowIndex, mapping, view);
    }

    public void delete(final TargetId tid) {
        adapter.deleteById(tid.value);
        refresh();
    }
}
