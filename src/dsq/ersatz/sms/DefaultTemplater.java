package dsq.ersatz.sms;

import android.os.Bundle;

import java.util.Set;

public class DefaultTemplater implements Templater {
    public String template(String source, Bundle bundle) {
        String r = source;
        Set<String> keys = bundle.keySet();
        for (String key : keys) {
            String value = bundle.getString(key);
            r = r.replaceAll(key, value);
        }
        return r;
    }
}
