package dsq.ersatz.sms;

import android.content.Context;
import dsq.ersatz.db.riposte.Riposte;

public interface Notifier {
    void notify(Context context, Riposte riposte, String localNumber);
}
