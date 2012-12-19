package dsq.ersatz.screens.edit;

import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import dsq.ersatz.R;
import dsq.ersatz.action.IdAction;
import dsq.ersatz.action.IntentAction;
import dsq.ersatz.action.SimpleAction;
import dsq.ersatz.data.data.RiposteId;
import dsq.ersatz.db.general.DbLifecycle;
import dsq.ersatz.db.general.DefaultDbLifecycle;
import dsq.ersatz.db.riposte.RiposteTable;
import dsq.ersatz.requests.Requests;
import dsq.ersatz.screens.edit.action.*;
import dsq.ersatz.ui.button.Buttons;
import dsq.ersatz.ui.button.DefaultButtons;
import dsq.ersatz.ui.context.Contexts;
import dsq.ersatz.ui.context.DefaultContexts;
import dsq.ersatz.ui.option.DefaultOptions;
import dsq.ersatz.ui.option.Options;
import dsq.ersatz.ui.response.DefaultResponses;
import dsq.ersatz.ui.response.Responses;
import dsq.ersatz.ui.tab.DefaultTabs;
import dsq.ersatz.ui.tab.TabInfo;
import dsq.ersatz.ui.tab.Tabs;
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

    /* Might make these static */
    private Tabs tabs = new DefaultTabs();

    private Buttons buttons;
    private Options options;
    private Contexts contexts;
    private Responses responses;

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

        options = setupOptions();
        contexts = setupContexts();
        responses = setupResponses();
        buttons = setupButtons();

        buttons.register();
    }

    private Responses setupResponses() {
        final Map<Integer, IntentAction> success = new HashMap<Integer, IntentAction>();
        success.put(Requests.PICK_CONTACT_REQUEST, actions.addContact());
        success.put(Requests.INSERT_TEMPLATE_REQUEST, actions.insertTemplate());
        final Map<Integer, IntentAction> failure = new HashMap<Integer, IntentAction>();
        return new DefaultResponses(success, failure);
    }

    private Options setupOptions() {
        final Map<Integer, SimpleAction> mapping = new HashMap<Integer, SimpleAction>();
        mapping.put(R.id.insert_plant, actions.showTemplates());
        return new DefaultOptions(this, mapping);
    }

    private Contexts setupContexts() {
        final Map<Integer, IdAction> mapping = new HashMap<Integer, IdAction>();
        mapping.put(R.id.delete_target, actions.deleteTarget());
        return new DefaultContexts(this, mapping);
    }

    private Buttons setupButtons() {
        final Map<Integer, SimpleAction> commands = new HashMap<Integer, SimpleAction>();
        commands.put(R.id.cancel, actions.revert());
        commands.put(R.id.confirm, actions.confirm());
        commands.put(R.id.browse, actions.browse());
        return new DefaultButtons(this, commands);
    }

    private void setupTabs() {
        tabs.install(this, R.id.tabHost, Arrays.asList(
            new TabInfo(R.id.tab_general, R.string.tab_general),
            new TabInfo(R.id.tab_recipients, R.string.tab_recipients)
        ));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return options.onCreate(menu, R.menu.edit);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        final boolean result = options.onClick(item);
        return result ? result : super.onOptionsItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        contexts.onCreate(menu, R.menu.edit_context);
    }

    public boolean onContextItemSelected(MenuItem item) {
        final boolean result = contexts.onClick(item);
        return result ? result : super.onContextItemSelected(item);
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
        responses.onResult(reqCode, resultCode, data);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        actions.backup(outState);
    }
}
