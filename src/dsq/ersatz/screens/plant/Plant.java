package dsq.ersatz.screens.plant;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import dsq.ersatz.R;
import dsq.ersatz.db.general.DbLifecycle;
import dsq.ersatz.db.general.DefaultDbLifecycle;
import dsq.ersatz.screens.plant.action.ActionFactory;
import dsq.ersatz.screens.plant.action.DefaultActionFactory;
import dsq.ersatz.screens.plant.action.UiActions;
import dsq.sycophant.action.SimpleAction;
import dsq.sycophant.ui.button.Buttons;
import dsq.sycophant.ui.button.DefaultButtons;

import java.util.HashMap;
import java.util.Map;

public class Plant extends Activity {

    private final DbLifecycle lifecycle = new DefaultDbLifecycle();

    private UiActions actions;
    private Buttons buttons;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_plant);
        setTitle(R.string.insert_plant_ui_title);

        final SQLiteDatabase db = lifecycle.open(this);
        final ActionFactory factory = new DefaultActionFactory();
        actions = factory.nu(this, db);

        buttons = setupButtons();
        init();
    }

    private void init() {
        buttons.register();
    }

    @Override
    protected void onDestroy() {
        lifecycle.close();
        super.onDestroy();
    }

    private Buttons setupButtons() {
        final Map<Integer, SimpleAction> mapping = new HashMap<Integer, SimpleAction>();
        mapping.put(R.id.cancel, actions.cancel());
        mapping.put(R.id.confirm, actions.confirm());
        return new DefaultButtons(this, mapping);
    }
}
