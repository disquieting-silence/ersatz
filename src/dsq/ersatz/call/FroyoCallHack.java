package dsq.ersatz.call;

import android.os.Build;
import com.android.internal.telephony.ITelephony;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;

// http://prasanta-paul.blogspot.com.au/2010/09/call-control-in-android.html --
public class FroyoCallHack implements CallHack {
    public void hangup(final Context context) {
        final Object service = context.getSystemService(Context.TELEPHONY_SERVICE);
        final TelephonyManager manager = (TelephonyManager) service;

        try {
            final Class<?> telephonyClass = Class.forName(manager.getClass().getName());
            final Method method = telephonyClass.getDeclaredMethod("getITelephony");
            method.setAccessible(true);
            final ITelephony telephony = (ITelephony) method.invoke(manager);
            telephony.endCall();

        } catch (Exception exc) {
            Log.e("ERSATZ", "Error during hangup for Froyo.");
            exc.printStackTrace();
        }
    }
}
