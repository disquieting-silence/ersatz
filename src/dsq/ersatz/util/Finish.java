package dsq.ersatz.util;

import android.app.Activity;

public interface Finish {
    void resultWithId(Activity activity, int result, Class<?> cls, long id);
    void result(Activity activity, int result, Class<?> cls);
    void resultWith(Activity activity, int result, Class<?> cls, String key, String value);
}
