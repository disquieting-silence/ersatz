package dsq.ersatz.db.riposte;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class DefaultRiposteViewBinder implements RiposteViewBinder {

    private final RiposteDbAdapter adapter;
    private final RiposteBroadcast broadcast = new DefaultRiposteBroadcast();
    
    private static final int NAME_COLUMN = 1;
    private static final int ENABLED_COLUMN = 3;

    public DefaultRiposteViewBinder(RiposteDbAdapter adapter) {
        this.adapter = adapter;
    }
    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        final int id = getId(cursor);
        if (columnIndex == ENABLED_COLUMN) {
            setupCheckbox((CheckBox) view, cursor, id);
            return true;
        }
        return false;
    }

    private void setupCheckbox(CheckBox view, Cursor cursor, final int id) {
        int value = cursor.getInt(ENABLED_COLUMN);
        boolean checked = value > 0;
        clearPreviousListeners(view);

        view.setChecked(checked);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                return false;
            }
        });

        final Context context = view.getContext();
        view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                adapter.update(id, b);
                broadcast.send(context, adapter);
            }
        });
    }

    private void clearPreviousListeners(CheckBox checkbox) {
        // First clear the listener of any recycled views.
        checkbox.setOnCheckedChangeListener(null);
    }

    // FIX 02/09/2011 Hmm. This doesn't seem very generic or safe.
    private int getId(Cursor cursor) {
        return cursor.getInt(0);
    }
}
