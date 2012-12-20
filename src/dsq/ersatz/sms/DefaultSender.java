package dsq.ersatz.sms;

import android.content.Context;
import android.telephony.SmsManager;
import dsq.ersatz.db.riposte.Riposte;

public class DefaultSender implements Sender {

    public static final String PREFIX = "[ERSATZ]: ";

    public void send(Context context, Riposte riposte, String localNumber) {
        SmsManager manager = SmsManager.getDefault();
        manager.sendTextMessage(localNumber, null, riposte.message, null, null);

        // FIX 26/01/12 Wrap this up so that it is only notifying if the send was successful
        Notifier notes = new DefaultNotifier();
        notes.notify(context, riposte, localNumber);
    }
}