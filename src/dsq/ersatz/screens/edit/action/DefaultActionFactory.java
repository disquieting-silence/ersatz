package dsq.ersatz.screens.edit.action;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import dsq.ersatz.data.data.RiposteId;
import dsq.ersatz.screens.edit.core.DefaultRiposte;
import dsq.ersatz.screens.edit.core.Riposte;
import dsq.ersatz.screens.edit.core.Targets;
import dsq.ersatz.screens.edit.core.DefaultTargets;

public class DefaultActionFactory implements ActionFactory {
    public UiActions nu(final ListActivity activity, final SQLiteDatabase db, final RiposteId id) {
        final Targets targets = new DefaultTargets(activity, db, id);
        final Riposte riposte = new DefaultRiposte(activity, db, id);

        riposte.readFromDb();
        targets.refresh();

        final Actions actions = new DefaultActions(activity, riposte, targets, id);
        return new DefaultUiActions(actions);
    }
}
