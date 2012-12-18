package dsq.ersatz.ui.tab;

import android.app.Activity;
import android.widget.TabHost;
import dsq.ersatz.R;

import java.util.List;

public class DefaultTabs implements Tabs {
    public void install(final Activity activity, final int host, final List<TabInfo> tabs) {
        final TabHost tabHost = (TabHost)activity.findViewById(host);
        tabHost.setup();

        for (TabInfo tab : tabs) {
            add(activity, tabHost, tab);
        }
    }

    private void add(final Activity activity, final TabHost tabHost, final TabInfo tab) {
        final String label = activity.getString(tab.label);
        final TabHost.TabSpec spec = tabHost.newTabSpec(label);
        spec.setIndicator(label);
        spec.setContent(tab.content);
        tabHost.addTab(spec);
    }
}
