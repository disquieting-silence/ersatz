package dsq.ersatz.sms;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import dsq.ersatz.db.riposte.Riposte;

public class DefaultTemplateSender implements TemplateSender {

    private final Sender sender = new DefaultSender();
    private final Templater templater = new DefaultTemplater();

    public void send(final Context context, final Riposte riposte, final String number, final Bundle bundle) {
        final String message = templater.template(riposte.message, bundle);
        final Riposte newRiposte = new Riposte(riposte.id, riposte.name, message, riposte.targetId);
        sender.send(context, newRiposte, number);
    }
}
