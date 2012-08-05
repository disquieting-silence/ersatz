package dsq.ersatz.sms;

import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.ArrayList;
import java.util.List;

public class DefaultMessages implements Messages {
    private static final String RAW_MESSAGES = "pdus";

    public List<SmsMessage> parse(Bundle messagesBundle) {
        Object[] rawMessages = (Object[])messagesBundle.get(RAW_MESSAGES);
        List<SmsMessage> messages = new ArrayList<SmsMessage>();
        for (Object rawMessage : rawMessages) {
            SmsMessage sms = SmsMessage.createFromPdu((byte[]) rawMessage);
            messages.add(sms);
        }
        return messages;
    }
}
