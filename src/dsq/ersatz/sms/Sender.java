package dsq.ersatz.sms;

import android.content.Context;
import dsq.ersatz.db.riposte.Riposte;

public interface Sender {
    void send(Context context, Riposte riposte, String localNumber);
}
