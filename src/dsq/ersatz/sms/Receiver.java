package dsq.ersatz.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import dsq.ersatz.db.riposte.Riposte;
import dsq.ersatz.query.DefaultRiposteResponse;
import dsq.ersatz.query.RiposteResponse;

import java.util.List;

//http://www.androidcompetencycenter.com/2008/12/android-api-sms-handling/
public class Receiver extends BroadcastReceiver {

    private final Messages tools = new DefaultMessages();
    private final Converter converter = new DefaultConverter();
    private final RiposteResponse response = new DefaultRiposteResponse();
    private final Sender sender = new DefaultSender();
    private final Templater templater = new DefaultTemplater();
    private final TemplatesBundle bundles = new DefaultTemplatesBundle();

    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();

        List<SmsMessage> messages = tools.parse(bundle);
        
        // FIX 25/01/12 Should do this for all of them.
        SmsMessage sms = messages.get(0);

        String international = sms.getOriginatingAddress();
        String local = converter.toLocal(international);

        long currentTime = System.currentTimeMillis();
        List<Riposte> ripostes = response.find(context, local, currentTime);
        for (Riposte riposte : ripostes) {
            Riposte response = riposte.message.indexOf("#") > -1 ? template(context, riposte) : riposte;
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
