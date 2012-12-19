package dsq.ersatz.util;

import android.app.Activity;
import android.content.Intent;

public class DefaultFinish implements Finish {
    public void resultWithId(final Activity activity, final int result, final Class<?> cls, final long id) {
        final Intent dest = new Intent(activity, cls);
        // FIX 24/01/12 Use constants for these intent keys.
        dest.putExtra("id", id);
        activity.setResult(result);
        activity.finish();
    }
}