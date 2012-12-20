package dsq.ersatz.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import dsq.ersatz.call.CallHack;
import dsq.ersatz.call.GingerbreadCallHack;
import dsq.ersatz.db.riposte.Riposte;
import dsq.ersatz.query.DefaultRiposteResponse;
import dsq.ersatz.query.RiposteResponse;

import java.util.List;

public class CallReceiver extends BroadcastReceiver {

    private static final boolean EMULATOR_DEBUGGING = true;
    private final RiposteFirer fire = new DefaultRiposteFirer();
    private final RiposteResponse response = new DefaultRiposteResponse();
    private final Converter converter = new DefaultConverter();
    private final CallHack callHack = new GingerbreadCallHack();

    @Override
    public void onReceive(final Context context, final Intent intent) {
        final Bundle bundle = intent.getExtras();
        final String number = getCallNumber(bundle);
        final long currentTime = System.currentTimeMillis();
        final List<Riposte> ripostes = response.find(context, number, currentTime);

        if (ripostes.size() > 0) {
            callHack.hangup(context);
            fire.fire(context, number, ripostes, currentTime);
        }
    }

    private String getCallNumber(final Bundle bundle) {
        final String number = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        return EMULATOR_DEBUGGING ? number : converter.toLocal(number);
    }
}
