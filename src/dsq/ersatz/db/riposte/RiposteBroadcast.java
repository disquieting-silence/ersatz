package dsq.ersatz.db.riposte;

import android.content.Context;

public interface RiposteBroadcast {
    void send(Context context, RiposteDbAdapter adapter);

    void send(Context context, boolean active);
}
