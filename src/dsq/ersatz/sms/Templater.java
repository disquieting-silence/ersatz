package dsq.ersatz.sms;

import android.os.Bundle;

public interface Templater  {
    String template(String source, Bundle bundle);
}
