package dsq.ersatz.ui.option;

import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import dsq.sycophant.action.SimpleAction;
import dsq.thedroid.ui.DefaultMenus;
import dsq.thedroid.ui.Menus;

import java.util.HashMap;
import java.util.Map;

public class DefaultOptions implements Options {

    private final Activity activity;
    private final Map<Integer, SimpleAction> actions;

    private final Menus menus = new DefaultMenus();

    public DefaultOptions(final Activity activity, final Map<Integer, SimpleAction> actions) {
        this.activity = activity;
        this.actions = new HashMap<Integer, SimpleAction>(actions);
    }

    public boolean onCreate(final Menu menu, final int id) {
        return menus.options(activity, menu, id);
    }

    public boolean onClick(final MenuItem item) {
        final SimpleAction action = actions.get(item.getItemId());
        if (action != null) {
            action.run();
            return true;
        }
        return false;
    }
}