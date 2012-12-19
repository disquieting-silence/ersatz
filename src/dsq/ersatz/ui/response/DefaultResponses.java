package dsq.ersatz.ui.response;

import android.app.Activity;
import android.content.Intent;
import dsq.ersatz.action.IntentAction;

import java.util.HashMap;
import java.util.Map;

public class DefaultResponses implements Responses {

    private final Map<Integer, IntentAction> success;
    private final Map<Integer, IntentAction> failure;

    public DefaultResponses(final Map<Integer, IntentAction> success, final Map<Integer, IntentAction> failure) {
        this.success = new HashMap<Integer, IntentAction>(success);
        this.failure = new HashMap<Integer, IntentAction>(failure);
    }

    public void onResult(final int reqCode, final int resultCode, final Intent data) {
        final IntentAction action = resultCode == Activity.RESULT_OK ? success.get(reqCode) : failure.get(reqCode);
        if (action != null) action.run(data);
    }
}
