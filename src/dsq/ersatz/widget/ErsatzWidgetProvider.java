package dsq.ersatz.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import dsq.ersatz.R;

public class ErsatzWidgetProvider extends AppWidgetProvider {
    
    public static final String RIPOSTE_FIRED = "dsq.ersatz.riposte.fired";
    public static final String ERSATZ_ACTIVE = "ersatz.active";

    @Override
    public void onUpdate(final Context context, final AppWidgetManager appWidgetManager, final int[] appWidgetIds) {
        
    }

    @Override
    public void onReceive(final Context context, final Intent intent) {
        if (intent.getAction().equals(RIPOSTE_FIRED)) {
            final boolean active = intent.getBooleanExtra(ERSATZ_ACTIVE, false);
            Log.v("ERSATZ", "Active: " + active);
            AppWidgetManager manager = AppWidgetManager.getInstance(context);
            final int[] ids = manager.getAppWidgetIds(new ComponentName(context.getPackageName(), getClass().getName()));
            final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
            views.setViewVisibility(R.id.widget_container, active ? View.VISIBLE : View.GONE);
            manager.updateAppWidget(ids, views);
        }
    }
}
