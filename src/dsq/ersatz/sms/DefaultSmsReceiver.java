package dsq.ersatz.sms;

import android.content.Context;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import dsq.ersatz.db.riposte.Riposte;
import dsq.ersatz.query.DefaultRiposteResponse;
import dsq.ersatz.query.RiposteResponse;

import java.util.List;

public class DefaultSmsReceiver implements SmsReceiver {

    private final boolean emulatorMode;

    private final Converter converter = new DefaultConverter();
    private final RiposteResponse response = new DefaultRiposteResponse();
    private final Sender sender = new DefaultSender();
    private final Templater templater = new DefaultTemplater();
    private final TemplatesBundle bundles = new DefaultTemplatesBundle();

    public DefaultSmsReceiver(final boolean emulatorMode) {
        this.emulatorMode = emulatorMode;
    }

    public void onReceive(final Context context, final List<SmsMessage> messages) {
        // FIX 25/01/12 Should do this for all of them.
        SmsMessage sms = messages.get(0);

        String international = sms.getOriginatingAddress();
        String local = emulatorMode ? international : converter.toLocal(international);
        Log.v("ERSATZ", "Local number: " + local);

        long currentTime = System.currentTimeMillis();
        List<Riposte> ripostes = response.find(context, local, currentTime);
        Log.v("ERSATZ", "Ripostes: " + ripostes.size());
        for (Riposte riposte : ripostes) {
            Riposte response = riposte.message.contains("#") ? template(context, riposte) : riposte;
            // FIX 26/01/12 Handle failure.
            sender.send(context, response, local);
        }
        response.update(context, ripostes, currentTime);

    }

    private Riposte template(Context context, Riposte riposte) {
        Bundle bundle = bundles.bundle(context);
        String message = templater.template(riposte.message, bundle);
        return new Riposte(riposte.id, riposte.name, message, riposte.targetId);
    }
}
