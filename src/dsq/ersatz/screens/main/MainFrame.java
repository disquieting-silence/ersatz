package dsq.ersatz.screens.main;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import dsq.ersatz.R;
import dsq.ersatz.db.general.DbLifecycle;
import dsq.ersatz.db.general.DefaultDbLifecycle;
import dsq.ersatz.db.riposte.*;
import dsq.ersatz.requests.Requests;
import dsq.ersatz.screens.edit.RiposteEdit;
import dsq.ersatz.screens.settings.Settings;
import dsq.thedroid.ui.*;


public class MainFrame extends ListActivity {
    private final DbLifecycle lifecycle = new DefaultDbLifecycle();
    private SQLiteDatabase db;
    private RiposteDbAdapter adapter;

    // FIX 21/01/12 Rename me.
    private final Lists lists = new DefaultLists();
    private final Menus menus = new DefaultMenus();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.riposte_list);

        db = lifecycle.open(this);
        adapter = new DefaultRiposteDbAdapter(db);

        refreshList();
        registerForContextMenu(getListView());
    }

    protected void onDestroy() {
        lifecycle.close();
        super.onDestroy();
    }

    private void refreshList() {
        SimpleCursorAdapter.ViewBinder view = new DefaultRiposteViewBinder(adapter);
        lists.refreshAll(this, adapter, getListView(), new ComponentIndex(R.layout.riposte_row), new RiposteList(), view);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // FIX 21/01/12 Should this call super?
        return menus.options(this, menu, R.menu.main);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menus.context(this, menu, R.menu.main_context);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_riposte:
                long id = adapter.create("", "");
                launchAddRiposte(id);
                return true;
            case R.id.enable_all:
                setCheckboxes(true);
                return true;
            case R.id.disable_all:
                setCheckboxes(false);
                return true;
            case R.id.settings:
                startActivity(new Intent(this, Settings.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private void setCheckboxes(boolean state) {
        adapter.updateAll(state);
        refreshList();
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        long id = info.id;
        switch (item.getItemId()) {
            case R.id.edit_riposte:
                launchEditRiposte(id);
                return true;

            case R.id.delete_riposte:
                adapter.deleteById(id);
                refreshList();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    public void launchAddRiposte(long id) {
        launchRiposteScreen(id, Requests.ADD_RIPOSTE_REQUEST);
    }

    public void launchEditRiposte(long id) {
        launchRiposteScreen(id, Requests.EDIT_RIPOSTE_REQUEST);
    }

    private void launchRiposteScreen(long id, int reqCode) {
        Intent intent = new Intent(this, RiposteEdit.class);
        intent.putExtra(RiposteTable.ID, id);
        startActivityForResult(intent, reqCode);
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (reqCode == Requests.ADD_RIPOSTE_REQUEST && resultCode == Activity.RESULT_CANCELED) {
            long id = data != null ? data.getLongExtra("id", -1) : -1;
            if (id > -1) adapter.deleteById(id);
        }
    }
}
