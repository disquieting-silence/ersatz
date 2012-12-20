package dsq.ersatz.sms;

import android.content.Context;
import android.telephony.SmsMessage;

import java.util.List;

public interface SmsReceiver {
    void onReceive(Context context, List<SmsMessage> messages);
}
