package dsq.ersatz.util;

import android.app.Activity;
import android.content.Intent;

public class DefaultFinish implements Finish {
    public void resultWithId(final Activity activity, final int result, final Class<?> cls, final long id) {
        final Intent intent = new Intent(activity, cls);
        // FIX 24/01/12 Use constants for these intent keys.
        intent.putExtra("id", id);
        activity.setResult(result, intent);
        activity.finish();
    }

    public void result(final Activity activity, final int result, final Class<?> cls) {
        final Intent intent = new Intent(activity, cls);
        activity.setResult(result, intent);
        activity.finish();
    }

    public void resultWith(final Activity activity, final int result, final Class<?> cls, final String key, final String value) {
        final Intent intent = new Intent(activity, cls);
        intent.putExtra(key, value);
        activity.setResult(result, intent);
        activity.finish();
    }
}