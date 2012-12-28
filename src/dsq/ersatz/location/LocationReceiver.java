package dsq.ersatz.location;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import dsq.ersatz.db.riposte.Riposte;
import dsq.ersatz.requests.Requests;
import dsq.ersatz.sms.*;

public class LocationReceiver extends BroadcastReceiver {

    public static final String LOCATION_UPDATE = "dsq.ersatz.location.update";
    public static final String LOCATION_NUMBER = "LOCATION_NUMBER";
    public static final String LOC_RIPOSTE_ID = "LOC_RIPOSTE_ID";
    public static final String LOC_RIPOSTE_NAME = "LOC_RIPOSTE_NAME";
    public static final String LOC_RIPOSTE_MESSAGE = "LOC_RIPOSTE_MESSAGE";
    public static final String LOC_RIPOSTE_TARGET_ID = "LOC_RIPOSTE_TARGET_ID";

    private final TemplateSender sender = new DefaultTemplateSender();

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final String action = intent.getAction();
        if (action.equals(LOCATION_UPDATE)) sendMessage(context, intent);
    }

    private void sendMessage(final Context context, final Intent intent) {
        Log.v("ERSATZ", "Sending intent: " + intent.getClass().getCanonicalName() + " " + intent);
        final Bundle extras = intent.getExtras();
        final Riposte riposte = extract(extras);
        final String number = extras.getString(LOCATION_NUMBER);
        Log.v("ERSATZ", "Extras: " + String.valueOf(extras));
        final Location loc = (Location) extras.get(LocationManager.KEY_LOCATION_CHANGED);
        if (loc != null) {
            final Bundle templates = template(loc);
            Log.v("ERSATZ", "Templates: " + String.valueOf(templates));
//            sender.send(context, riposte, number, templates);
        }
        stopUpdates(context, intent);
    }

    private Riposte extract(final Bundle extras) {
        final long riposteId = extras.getLong(LOC_RIPOSTE_ID);
        final String riposteName = extras.getString(LOC_RIPOSTE_NAME);
        final String riposteMessage = extras.getString(LOC_RIPOSTE_MESSAGE);
        final long targetId = extras.getLong(LOC_RIPOSTE_TARGET_ID);
        return new Riposte(riposteId, riposteName, riposteMessage, targetId);
    }

    private void stopUpdates(final Context context, final Intent intent) {
        //http://stackoverflow.com/questions/3031630/android-how-to-cancel-a-request-of-location-update-with-intent/3032556#3032556
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Let's just not think about the global state required for this to work. I guess PendingIntents are
//        // some sort of global map when you think about it.
        final PendingIntent original = PendingIntent.getBroadcast(context, Requests.PENDING_LOCATION, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Log.v("ERSATZ", "Pending intent afterwards: " + original);
        original.cancel();
//
//
//        //If the creating application later re-retrieves the same kind of PendingIntent (same operation
//        // //, same Intent action, data, categories, and components, and same flags),
//        original.cancel();
//
        locationManager.removeUpdates(original);
    }

    private Bundle template(final Location loc) {
        final Bundle templates = new Bundle();
        templates.putString("#LAT", String.valueOf(loc.getLatitude()));
        templates.putString("#LONG", String.valueOf(loc.getLongitude()));
        return templates;
    }
}
