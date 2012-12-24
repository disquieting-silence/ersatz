package dsq.ersatz.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import dsq.ersatz.service.DefaultTurnOffService;

public class WidgetServices extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.v("ERSATZ", "Widget services.");
        final Intent service = new Intent(context, DefaultTurnOffService.class);
        final Intent values = service.replaceExtras(intent);
        context.startService(values);
    }
}
