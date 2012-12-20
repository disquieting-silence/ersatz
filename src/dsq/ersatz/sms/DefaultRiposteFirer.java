package dsq.ersatz.sms;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import dsq.ersatz.db.riposte.Riposte;
import dsq.ersatz.query.DefaultRiposteResponse;
import dsq.ersatz.query.RiposteResponse;

import java.util.List;

public class DefaultRiposteFirer implements RiposteFirer {
    private final Sender sender = new DefaultSender();
    private final Templater templater = new DefaultTemplater();
    private final TemplatesBundle bundles = new DefaultTemplatesBundle();
    private final RiposteResponse response = new DefaultRiposteResponse();

    public void fire(final Context context, final String number, final List<Riposte> ripostes, final long currentTime) {
        Log.v("ERSATZ", "Ripostes: " + ripostes.size());
        for (Riposte riposte : ripostes) {
            Riposte response = riposte.message.contains("#") ? template(context, riposte) : riposte;
            // FIX 26/01/12 Handle failure.
            sender.send(context, response, number);
        }
        response.update(context, ripostes, currentTime);
    }

    private Riposte template(Context context, Riposte riposte) {
        Bundle bundle = bundles.bundle(context);
        String message = templater.template(riposte.message, bundle);
        return new Riposte(riposte.id, riposte.name, message, riposte.targetId);
    }
}