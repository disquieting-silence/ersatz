package dsq.ersatz.location;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import dsq.ersatz.db.riposte.Riposte;
import dsq.ersatz.sms.*;

public class LocationReceiver extends BroadcastReceiver {

    public static final String LOCATION_UPDATE = "dsq.ersatz.location_update";
    public static final String LOCATION_RIPOSTE = "LOCATION_RIPOSTE";
    public static final String LOCATION_NUMBER = "LOCATION_NUMBER";

    private final TemplateSender sender = new DefaultTemplateSender();

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final String action = intent.getAction();
        if (action.equals(LOCATION_UPDATE)) sendMessage(context, intent);
    }

    private void sendMessage(final Context context, final Intent intent) {
        final Bundle extras = intent.getExtras();
        final Riposte riposte = (Riposte)extras.get(LOCATION_RIPOSTE);
        final String number = extras.getString(LOCATION_NUMBER);
        final Location loc = (Location) extras.get(LocationManager.KEY_LOCATION_CHANGED);
        final Bundle templates = template(loc);
        sender.send(context, riposte, number, templates);
        context.unregisterReceiver(this);
    }

    private Bundle template(final Location loc) {
        final Bundle templates = new Bundle();
        templates.putString("#LAT", String.valueOf(loc.getLatitude()));
        templates.putString("#LONG", String.valueOf(loc.getLongitude()));
        return templates;
    }
}
