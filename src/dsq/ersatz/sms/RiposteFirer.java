package dsq.ersatz.sms;

import android.content.Context;
import dsq.ersatz.db.riposte.Riposte;

import java.util.List;

public interface RiposteFirer {
    void fire(Context context, String number, List<Riposte> ripostes, long currentTime);
}
