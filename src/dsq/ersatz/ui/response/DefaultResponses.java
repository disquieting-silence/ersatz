package dsq.ersatz.ui.response;

import android.app.Activity;
import android.content.Intent;
import dsq.ersatz.screens.edit.action.IntentAction;

import java.util.HashMap;
import java.util.Map;

public class DefaultResponses implements Responses {

    private final Activity activity;
    private final Map<Integer, IntentAction> actions;

    public DefaultResponses(final Activity activity, final Map<Integer, IntentAction> actions) {
        this.activity = activity;
        this.actions = new HashMap<Integer, IntentAction> (actions);
    }

    public void onResult(final int reqCode, final int resultCode, final Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            final IntentAction action = actions.get(reqCode);
            if (action != null) action.run(data);
        }
    }
}
