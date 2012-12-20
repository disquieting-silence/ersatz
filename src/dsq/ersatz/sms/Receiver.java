package dsq.ersatz.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import dsq.ersatz.db.riposte.Riposte;
import dsq.ersatz.query.DefaultRiposteResponse;
import dsq.ersatz.query.RiposteResponse;

import java.util.List;

//http://www.androidcompetencycenter.com/2008/12/android-api-sms-handling/
public class Receiver extends BroadcastReceiver {

    private final Messages tools = new DefaultMessages();

    private final static boolean EMULATOR_DEBUGGING = true;
    private final SmsReceiver sms = new DefaultSmsReceiver(EMULATOR_DEBUGGING);

    public void onReceive(Context context, Intent intent) {
        Log.v("ERSATZ", "Receiving message on emulator");

        Bundle bundle = intent.getExtras();

        List<SmsMessage> messages = tools.parse(bundle);
        if (messages.isEmpty()) {

        } else {
            sms.onReceive(context, messages);
        }
    }
}
