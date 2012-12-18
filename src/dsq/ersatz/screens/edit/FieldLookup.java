package dsq.ersatz.screens.edit;

import android.app.Activity;
import android.content.Context;
import dsq.ersatz.db.riposte.RiposteDbAdapter;

public interface FieldLookup {
    void load(final Activity activity, final RiposteDbAdapter adapter, final EditUi ui, long id);
}
