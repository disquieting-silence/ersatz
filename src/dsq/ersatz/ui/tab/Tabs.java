package dsq.ersatz.ui.tab;

import android.app.Activity;
import android.widget.TabHost;

import java.util.List;

public interface Tabs {
    TabHost install(final Activity activity, final int host, final List<TabInfo> tabs);
}
