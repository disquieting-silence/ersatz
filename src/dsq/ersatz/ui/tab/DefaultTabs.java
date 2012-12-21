package dsq.ersatz.ui.tab;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.TabHost;
import dsq.ersatz.R;
import dsq.ersatz.ui.keyboard.KeyboardUtil;

import java.util.List;

public class DefaultTabs implements Tabs {

    public TabHost install(final Activity activity, final int host, final List<TabInfo> tabs) {
        final TabHost tabHost = (TabHost)activity.findViewById(host);
        tabHost.setup();

        for (TabInfo tab : tabs) {
            add(activity, tabHost, tab);
        }
        return tabHost;
    }

    private void add(final Activity activity, final TabHost tabHost, final TabInfo tab) {
        final String label = activity.getString(tab.label);
        final TabHost.TabSpec spec = tabHost.newTabSpec(label);
        spec.setIndicator(label);
        spec.setContent(tab.content);
        tabHost.addTab(spec);
    }
}
