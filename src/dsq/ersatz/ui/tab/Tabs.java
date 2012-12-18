package dsq.ersatz.ui.tab;

import android.app.Activity;

import java.util.List;

public interface Tabs {
    void install(final Activity activity, final int host, final List<TabInfo> tabs);
}
