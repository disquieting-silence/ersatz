package dsq.ersatz.ui.spinner;

import android.R;
import android.app.Activity;
import android.database.Cursor;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import dsq.thedroid.db.DbAdapter;

public class DefaultSpinners implements Spinners {

    // FIX 18/02/12 Inspired by: http://www.dcpagesapps.com/developer-resources/android/21-android-tutorial-spinners?start=2
    public Spinner keyValue(final Activity activity, final int id, final String key, final String value, final DbAdapter adapter, final SpinnerListener listener) {
        final String[] from = new String[] { key };
        final int[] to = new int[] { android.R.id.text1 };
        final Cursor cursor = adapter.fetchAll();

        // not sure about managing cursors for later versions of Android
        activity.startManagingCursor(cursor);
        final SimpleCursorAdapter ad = new SimpleCursorAdapter(activity, R.layout.simple_spinner_item, cursor, from, to);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final Spinner spinner = (Spinner)activity.findViewById(id);
        spinner.setAdapter(ad);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(final AdapterView<?> adapterView, final View view, final int i, final long l) {
                final Cursor c = (Cursor) spinner.getSelectedItem();
                if (c != null) {
                    final int index = c.getColumnIndex(value);
                    if (index >= 0) listener.select(c.getString(index));
                }
            }

            public void onNothingSelected(final AdapterView<?> adapterView) {
                listener.none();
            }
        });

        return spinner;
    }
}
