package dsq.ersatz.ui.spinner;

import android.app.Activity;
import android.widget.Spinner;
import dsq.thedroid.db.DbAdapter;

public interface Spinners {
    Spinner keyValue(final Activity activity, final int id, final String key, final String value, final DbAdapter adapter, final SpinnerListener listener);
}
