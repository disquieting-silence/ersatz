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
public class SmsReceiver extends BroadcastReceiver {

    private static final boolean EMULATOR_MODE = false;

    private final RiposteFirer fire = new DefaultRiposteFirer();
    private final Messages tools = new DefaultMessages();
    private final RiposteResponse response = new DefaultRiposteResponse();
    private final Converter converter = new DefaultConverter();

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final Bundle bundle = intent.getExtras();
        final List<SmsMessage> messages = tools.parse(bundle);
        final String number = getSmsNumber(messages);
        long currentTime = System.currentTimeMillis();
        List<Riposte> ripostes = response.find(context, number, currentTime);

        if (ripostes.size() > 0) {
            fire.fire(context, number, ripostes, currentTime);
        }
    }

    private String getSmsNumber(final List<SmsMessage> messages) {
        SmsMessage first = messages.get(0);
        String international = first.getOriginatingAddress();
        return EMULATOR_MODE ? international : converter.toLocal(international);
    }
}
