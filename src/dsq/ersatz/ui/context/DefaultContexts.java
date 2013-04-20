package dsq.ersatz.ui.context;

import android.app.Activity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.widget.AdapterView;
import dsq.sycophant.action.IdAction;
import dsq.thedroid.ui.DefaultMenus;
import dsq.thedroid.ui.Menus;

import java.util.HashMap;
import java.util.Map;

public class DefaultContexts implements Contexts {

    private final Menus menus = new DefaultMenus();

    private final Activity activity;
    private final Map<Integer, IdAction> actions;

    public DefaultContexts(final Activity activity, final Map<Integer, IdAction> actions) {
        this.activity = activity;
        this.actions = new HashMap<Integer, IdAction>(actions);
    }

    public void onCreate(final ContextMenu menu, final int id) {
        menus.context(activity, menu, id);
    }

    public boolean onClick(final MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final IdAction action = actions.get(item.getItemId());
        if (action != null) {
            action.run(info.id);
            return true;
        }
        return false;
    }
}