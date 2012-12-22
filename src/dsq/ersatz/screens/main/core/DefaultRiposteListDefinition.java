package dsq.ersatz.screens.main.core;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import dsq.ersatz.R;
import dsq.ersatz.db.riposte.RiposteTable;
import dsq.thedroid.ui.ComponentIndex;

import java.util.HashMap;
import java.util.Map;

public class DefaultRiposteListDefinition implements RiposteListDefinition {

    private final Activity activity;

    public DefaultRiposteListDefinition(final Activity activity) {
        this.activity = activity;
    }

    public String[] sources() {
        return new String[] {
            RiposteTable.ENABLED,
            RiposteTable.NAME
        };
    }

    public int[] destinations() {
        return new int[] {
            R.id.riposte_sphere,
            R.id.name
        };
    }

    public RiposteV build(final Cursor c) {
        return c.moveToFirst() ? safeBuild(c) : new RiposteV(-1, "", false);
    }

    private RiposteV safeBuild(final Cursor c) {
        final int id = getId(c);
        final String name = getName(c);
        final boolean enabled = isEnabled(c);
        return new RiposteV(id, name, enabled);
    }

    private boolean isEnabled(final Cursor c) {
        final int enabledCol = c.getColumnIndex(RiposteTable.ENABLED);
        return c.getInt(enabledCol) != 0;
    }

    private String getName(final Cursor c) {
        final int nameCol = c.getColumnIndex(RiposteTable.NAME);
        return c.getString(nameCol);
    }

    private int getId(final Cursor c) {
        if (!c.moveToFirst()) return -1;
        final int idCol = c.getColumnIndex(RiposteTable.ID);
        return idCol < c.getColumnCount() ? c.getInt(idCol) : -1;
    }

    public boolean setViewValue(final View view, final Cursor cursor, final int columnIndex) {
        final int id = view.getId();
        if (id == R.id.riposte_sphere) {
            final ImageView iview = (ImageView) view;
            final boolean enabled = isEnabled(cursor);
            final Drawable drawable = activity.getResources().getDrawable(enabled ? R.drawable.icon_on_16 : R.drawable.transparent);
            iview.setImageDrawable(drawable);
            return true;
        }
        return false;
    }
}
