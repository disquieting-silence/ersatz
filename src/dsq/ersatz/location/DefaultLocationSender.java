package dsq.ersatz.location;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import dsq.ersatz.db.riposte.Riposte;
import dsq.ersatz.requests.Requests;

public class DefaultLocationSender implements LocationSender {
    private final LocationReceiver locations = new LocationReceiver();

    // FIX 26/12/12 There could be some logic here to only fetch the location when it is out-of-date.
    public void send(final Context context, final Riposte riposte, final String number) {
        final IntentFilter filter = new IntentFilter();
        filter.addAction(LocationReceiver.LOCATION_UPDATE);
        context.registerReceiver(locations, filter);
        final PendingIntent pendingIntent = setupIntent(context, number, riposte);
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, pendingIntent);
    }

    private PendingIntent setupIntent(final Context context, final String number, final Riposte riposte) {
        final Intent intent = new Intent();
        intent.putExtra(LocationReceiver.LOCATION_NUMBER, number);
        intent.putExtra(LocationReceiver.LOCATION_RIPOSTE, riposte);
        return PendingIntent.getBroadcast(context, Requests.PENDING_LOCATION, intent, 0);
    }
}
