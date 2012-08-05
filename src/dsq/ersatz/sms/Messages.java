package dsq.ersatz.sms;

import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.List;

public interface Messages {
    List<SmsMessage> parse(Bundle messagesBundle);
}
