package dsq.ersatz.call;

import android.content.Context;

public interface CallReceiver {
    public void onReceive(final Context context, final String number);
}
