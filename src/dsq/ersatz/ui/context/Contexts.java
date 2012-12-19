package dsq.ersatz.ui.context;

import android.view.ContextMenu;
import android.view.MenuItem;

public interface Contexts {
    void onCreate(ContextMenu menu, int id);
    boolean onClick(MenuItem item);
}
