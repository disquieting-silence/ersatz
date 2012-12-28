package dsq.ersatz.location;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import dsq.ersatz.requests.Requests;

public class DefaultLocationIntents implements LocationIntents {
    public void send(final Context context, final Intent intent) {
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(context, Requests.PENDING_LOCATION, intent, PendingIntent.FLAG_ONE_SHOT);
        Log.v("ERSATZ", "Pending intent initially: " + pendingIntent);
        final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, pendingIntent);
    }
}
