package dsq.ersatz.db.target;

import android.database.Cursor;
import android.view.View;

public class DefaultTargetViewBinder implements TargetViewBinder {
    
    private final TargetDbAdapter adapter;

    public DefaultTargetViewBinder(TargetDbAdapter adapter) {
        this.adapter = adapter;
    }

    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
        return false;
    }
}
