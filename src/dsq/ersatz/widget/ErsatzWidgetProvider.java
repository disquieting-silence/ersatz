package dsq.ersatz.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import dsq.ersatz.R;
import dsq.ersatz.requests.Requests;
import dsq.ersatz.service.DefaultTurnOffService;

public class ErsatzWidgetProvider extends BroadcastReceiver {
    
    public static final String RIPOSTE_FIRED = "dsq.ersatz.riposte.fired";
    public static final String ERSATZ_ACTIVE = "ersatz.active";
    public static final String STOP_ALL = "dsq.ersatz.ripostes.stop";

    private PendingIntent clicker(final Context context) {
        final Intent intent = new Intent(STOP_ALL);
        intent.putExtra("WIDGET_UPDATE_ALL", false);
        return PendingIntent.getBroadcast(context, Requests.TURN_ALL_OFF, intent, Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (intent.getAction().equals(RIPOSTE_FIRED)) {
            final boolean active = intent.getBooleanExtra(ERSATZ_ACTIVE, false);
            Log.v("ERSATZ", "Active: " + active);
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            final int[] ids = manager.getAppWidgetIds(new ComponentName(context.getPackageName(), getClass().getName()));
            final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setOnClickPendingIntent(R.id.widget_button_cancel, clicker(context));
            views.setViewVisibility(R.id.widget_container, active ? View.VISIBLE : View.GONE);

            manager.updateAppWidget(ids, views);
        }
    }
}
