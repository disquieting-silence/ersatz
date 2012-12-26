package dsq.ersatz.sms;

import android.content.Context;
import android.os.Bundle;
import dsq.ersatz.db.riposte.Riposte;

public interface TemplateSender {
    void send(Context context, Riposte riposte, String localNumber, Bundle bundle);
}
