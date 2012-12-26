package dsq.ersatz.service;

import android.app.IntentService;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import dsq.ersatz.db.general.DbLifecycle;
import dsq.ersatz.db.general.DefaultDbLifecycle;
import dsq.ersatz.db.riposte.DefaultRiposteBroadcast;
import dsq.ersatz.db.riposte.DefaultRiposteDbAdapter;
import dsq.ersatz.db.riposte.RiposteBroadcast;
import dsq.ersatz.db.riposte.RiposteDbAdapter;

public class DefaultTurnOffService extends IntentService implements TurnOffService {

    public static final String WIDGET_UPDATE_ALL = "WIDGET_UPDATE_ALL";

    private final DbLifecycle lifecycle = new DefaultDbLifecycle();
    private final RiposteBroadcast broadcast = new DefaultRiposteBroadcast();
    private SQLiteDatabase db;

    public DefaultTurnOffService() {
        super("ersatz turn off service.");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        db = lifecycle.open(this);
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        if (intent.hasExtra(WIDGET_UPDATE_ALL)) {
            turnOff();
            stopSelf();
        }
    }

    /* FIX: I don't like this approach, but this is how binding is done apparently */
    public void turnOff() {
        final RiposteDbAdapter adapter = new DefaultRiposteDbAdapter(db);
        adapter.updateAll(false);
        broadcast.send(this, false);
    }
}
