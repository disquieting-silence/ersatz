package dsq.ersatz.screens.edit;

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
import dsq.ersatz.R;
import dsq.ersatz.db.general.DbLifecycle;
import dsq.ersatz.db.general.DefaultDbLifecycle;
import dsq.ersatz.db.riposte.RiposteTable;
import dsq.ersatz.requests.Requests;
import dsq.ersatz.screens.edit.action.*;
import dsq.ersatz.screens.edit.data.RiposteId;
import dsq.ersatz.ui.button.Buttons;
import dsq.ersatz.ui.button.Click;
import dsq.ersatz.ui.button.DefaultButtons;
import dsq.ersatz.ui.tab.DefaultTabs;
import dsq.ersatz.ui.tab.TabInfo;
import dsq.ersatz.ui.tab.Tabs;
import dsq.thedroid.ui.DefaultMenus;
import dsq.thedroid.ui.Menus;
import dsq.thedroid.util.DefaultStateExtractor;
import dsq.thedroid.util.StateExtractor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RiposteEdit extends ListActivity {

    private final DbLifecycle lifecycle = new DefaultDbLifecycle();
    private UiActions actions;

    private final StateExtractor extractor = new DefaultStateExtractor();
    private SQLiteDatabase db;

    private final Menus menus = new DefaultMenus();

    /* Might make these static */
    private Tabs tabs = new DefaultTabs();
    private Buttons buttons = new DefaultButtons();

    private Map<Integer, IdAction> context = new HashMap<Integer, IdAction>();

    private Map<Integer, SimpleAction> option = new HashMap<Integer, SimpleAction>();
    private Map<Integer, IntentAction> response = new HashMap<Integer, IntentAction>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.riposte_edit);
        setTitle(R.string.edit_ui_title);

        db = lifecycle.open(this);

        final long rawId = (Long) extractor.strict(RiposteTable.ID, savedInstanceState, getIntent());
        final RiposteId id = new RiposteId(rawId);

        final ActionFactory factory = new DefaultActionFactory();
        actions = factory.nu(this, db, id);

        final SimpleAction load = actions.refreshRiposte();
        load.run();

        setupTabs();
        registerForContextMenu(getListView());

        context.put(R.id.delete_target, actions.deleteTarget());
        option.put(R.id.insert_plant, actions.showTemplates());

        response.put(Requests.PICK_CONTACT_REQUEST, actions.addContact());
        response.put(Requests.INSERT_TEMPLATE_REQUEST, actions.insertTemplate());

        setupButtons();
    }

    private void setupButtons() {
        final Map<Integer, SimpleAction> commands = new HashMap<Integer, SimpleAction>();
        commands.put(R.id.cancel, actions.revert());
        commands.put(R.id.confirm, actions.confirm());
        commands.put(R.id.browse, actions.browse());

        for (final Integer buttonId : commands.keySet()) {
            buttons.register(this, buttonId, new Click() {
                public void click(final View view) {
                    final SimpleAction runner = commands.get(buttonId);
                    runner.run();
                }
            });
        }
    }

    private void setupTabs() {
        tabs.install(this, R.id.tabHost, Arrays.asList(
            new TabInfo(R.id.tab_general, R.string.tab_general),
            new TabInfo(R.id.tab_recipients, R.string.tab_recipients)
        ));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // FIX 21/01/12 Should this call super?
        return menus.options(this, menu, R.menu.edit);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menus.context(this, menu, R.menu.edit_context);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        final SimpleAction action = option.get(item.getItemId());
        if (action != null) {
            action.run();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final IdAction action = context.get(item.getItemId());
        if (action != null) {
            action.run(info.id);
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        lifecycle.close();
        super.onDestroy();
    }


    public void onBackPressed() {
        actions.revert();
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            final IntentAction action = response.get(reqCode);
            if (action != null) action.run(data);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        actions.backup(outState);
    }
}
