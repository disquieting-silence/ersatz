package dsq.ersatz.util;

import android.app.Activity;
import android.content.Intent;

public interface Finish {
    void resultWithId(Activity activity, int result, Class<?> cls, long id);
}
