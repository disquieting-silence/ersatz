package dsq.ersatz.location;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import dsq.ersatz.db.riposte.Riposte;
import dsq.ersatz.requests.Requests;

public class DefaultLocationSender implements LocationSender {

    private final LocationIntents location = new DefaultLocationIntents();

    // FIX 26/12/12 There could be some logic here to only fetch the location when it is out-of-date.
    public void send(final Context context, final Riposte riposte, final String number) {
        final Intent intent = riposteIntent(number, riposte);
        location.send(context, intent);
    }

    private Intent riposteIntent(final String number, final Riposte riposte) {
        final Intent intent = new Intent(LocationReceiver.LOCATION_UPDATE);
        intent.putExtra(LocationReceiver.LOCATION_NUMBER, number);
        intent.putExtra(LocationReceiver.LOC_RIPOSTE_ID, riposte.id);
        intent.putExtra(LocationReceiver.LOC_RIPOSTE_NAME, riposte.name);
        intent.putExtra(LocationReceiver.LOC_RIPOSTE_MESSAGE, riposte.message);
        intent.putExtra(LocationReceiver.LOC_RIPOSTE_TARGET_ID, riposte.targetId);
        return intent;
    }
}
