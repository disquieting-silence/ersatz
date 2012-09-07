package dsq.ersatz.db.riposte;

import android.content.Context;
import android.content.Intent;
import dsq.ersatz.widget.ErsatzWidgetProvider;

public class DefaultRiposteBroadcast implements RiposteBroadcast {

    public void send(final Context context, RiposteDbAdapter adapter) {
        final boolean active = adapter.hasActive();
        send(context, active);
    }

    public void send(final Context context, final boolean active) {
        final Intent go = new Intent();
        go.setAction(ErsatzWidgetProvider.RIPOSTE_FIRED);
        go.putExtra(ErsatzWidgetProvider.ERSATZ_ACTIVE, active);
        context.sendBroadcast(go);
    }


}
