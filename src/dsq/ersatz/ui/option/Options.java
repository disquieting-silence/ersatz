package dsq.ersatz.ui.option;

import android.view.Menu;
import android.view.MenuItem;

public interface Options {
    boolean onCreate(Menu menu, int id);
    boolean onClick(MenuItem item);
}