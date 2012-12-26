package dsq.ersatz.sms;

import android.content.Context;
import android.util.Log;
import dsq.ersatz.db.riposte.Riposte;
import dsq.ersatz.location.DefaultLocationSender;
import dsq.ersatz.location.LocationSender;
import dsq.ersatz.query.DefaultRiposteResponse;
import dsq.ersatz.query.RiposteResponse;

import java.util.List;

public class DefaultRiposteFirer implements RiposteFirer {

    private final RiposteResponse response = new DefaultRiposteResponse();
    private final Sender sender = new DefaultSender();
    private final LocationSender locSender = new DefaultLocationSender();

    // FIX 26/12/12 This approach for locations and response updating really doesn't scale well
    // to there being more than one riposte.
    public void fire(final Context context, final String number, final List<Riposte> ripostes, final long currentTime) {
        Log.v("ERSATZ", "Ripostes: " + ripostes.size());
        for (Riposte riposte : ripostes) {
            // FIX 26/01/12 Handle failure.
            final Sender s = riposte.message.contains("#") ? sender : locSender;
            s.send(context, riposte, number);
        }
        response.update(context, ripostes, currentTime);
    }
}
