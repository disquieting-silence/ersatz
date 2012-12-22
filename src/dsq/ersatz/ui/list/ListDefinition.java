package dsq.ersatz.ui.list;

import android.database.Cursor;
import android.view.View;
import dsq.thedroid.ui.ComponentIndex;

import java.util.Map;

public interface ListDefinition<A> {
    String[] sources();
    int[] destinations();
    A build(Cursor c);
    boolean setViewValue(View view, Cursor cursor, int columnIndex);
}
