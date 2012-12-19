package dsq.ersatz.screens.main;

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
import dsq.ersatz.db.general.DbLifecycle;
import dsq.ersatz.db.general.DefaultDbLifecycle;
import dsq.ersatz.db.riposte.DefaultRiposteBroadcast;
import dsq.ersatz.db.riposte.RiposteBroadcast;
import dsq.ersatz.requests.Requests;
import dsq.ersatz.screens.main.action.DefaultActionFactory;
import dsq.ersatz.screens.main.action.UiActions;
import dsq.ersatz.ui.context.Contexts;
import dsq.ersatz.ui.context.DefaultContexts;
import dsq.ersatz.ui.option.DefaultOptions;
import dsq.ersatz.ui.option.Options;
import dsq.ersatz.ui.response.DefaultResponses;
import dsq.ersatz.ui.response.Responses;

import java.util.HashMap;
import java.util.Map;


public class MainFrame extends ListActivity {
    private final DbLifecycle lifecycle = new DefaultDbLifecycle();

    private UiActions actions;

    private Contexts contexts;
    private Options options;
    private Responses responses;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.riposte_list);

        final SQLiteDatabase db = lifecycle.open(this);
        final DefaultActionFactory factory = new DefaultActionFactory();
        actions = factory.nu(this, db);

        contexts = setupContexts();
        options = setupOptions();
        responses = setupResponses();
        registerForContextMenu(getListView());
    }

    private Contexts setupContexts() {
        final Map<Integer, IdAction> mapping = new HashMap<Integer, IdAction>();
        mapping.put(R.id.edit_riposte, actions.launchEdit());
        mapping.put(R.id.delete_riposte, actions.delete());
        return new DefaultContexts(this, mapping);
    }

    private Options setupOptions() {
        final Map<Integer, SimpleAction> mapping = new HashMap<Integer, SimpleAction>();
        mapping.put(R.id.new_riposte, actions.launchAdd());
        mapping.put(R.id.settings, actions.launchSettings());
        return new DefaultOptions(this, mapping);
    }

    private Responses setupResponses() {
        final Map<Integer, IntentAction> failure = new HashMap<Integer, IntentAction>();
        failure.put(Requests.ADD_RIPOSTE_REQUEST, actions.cancel());
        final Map<Integer, IntentAction> success = new HashMap<Integer, IntentAction>();
        return new DefaultResponses(success, failure);
    }

    protected void onDestroy() {
        lifecycle.close();
        super.onDestroy();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return options.onCreate(menu, R.menu.main);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        final boolean result = options.onClick(item);
        return result ? result : super.onOptionsItemSelected(item);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        contexts.onCreate(menu, R.menu.main_context);
    }

    public boolean onContextItemSelected(MenuItem item) {
        final boolean result = contexts.onClick(item);
        return result ? result : super.onContextItemSelected(item);
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        responses.onResult(reqCode, resultCode, data);
    }
}
