package dsq.ersatz.sms;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

public class DefaultTemplatesBundle implements TemplatesBundle {
    public Bundle bundle(Context context) {
        Bundle bundle = new Bundle();
        Location loc = location(context);
        bundle.putString("#LAT", String.valueOf(loc.getLatitude()));
        bundle.putString("#LONG", String.valueOf(loc.getLongitude()));
        bundle.putString("#EQ", "=");
        return bundle;
    }

    private Location location(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
    }
}
