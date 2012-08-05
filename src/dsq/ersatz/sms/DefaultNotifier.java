package dsq.ersatz.sms;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import dsq.ersatz.R;
import dsq.ersatz.db.riposte.Riposte;
import dsq.ersatz.db.riposte.RiposteTable;
import dsq.ersatz.requests.Requests;
import dsq.ersatz.screens.edit.RiposteEdit;

public class DefaultNotifier implements Notifier {

    private static final int NO_FLAGS = 0;

    // FIX 26/01/12 I think notify should take all the ripostes and accumulate.
    public void notify(Context context, Riposte riposte, String localNumber) {
        PendingIntent emptyIntent = PendingIntent.getBroadcast(context, 0, new Intent(), NO_FLAGS);

        // FIX 26/01/12 Probably make a little better.
        NotificationManager notifications = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // FIX 26/01/12 Translate.
        String text = riposte.name + "->" + localNumber;
        // FIX 26/01/12 Get a better icon
        Notification note = new Notification(R.drawable.icon, text, System.currentTimeMillis());

        Intent editScreen = new Intent(context, RiposteEdit.class);
        editScreen.putExtra(RiposteTable.ID, riposte.id);
        // FIX 26/01/12 Translate.
        note.setLatestEventInfo(context, "Ersatz", "Riposte " + riposte.name + " fired to " + localNumber,
                PendingIntent.getActivity(context, Requests.SHOW_RIPOSTE, editScreen, NO_FLAGS));
        notifications.notify(Requests.NOTIFY_REQUEST, note);
    }
}
