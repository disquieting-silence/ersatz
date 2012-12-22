package dsq.ersatz.ui.button;

import android.app.Activity;
import android.view.View;
import dsq.ersatz.action.SimpleAction;

import java.util.HashMap;
import java.util.Map;

public class DefaultButtons implements Buttons {

    private final Activity activity;
    private final Map<Integer, SimpleAction> actions;

    public DefaultButtons(final Activity activity, final Map<Integer, SimpleAction> actions) {
        this.activity = activity;
        this.actions = new HashMap<Integer, SimpleAction> (actions);
    }

    public void register() {
        for (Integer buttonId : actions.keySet()) {
            register(activity, buttonId, actions.get(buttonId));
        }
    }

    public void trigger(final int id) {
        final SimpleAction action = actions.get(id);
        if (action != null) action.run();
    }

    private void register(final Activity activity, final int id, final SimpleAction action) {
        final View view = activity.findViewById(id);
        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                action.run();
            }
        });
    }
}
