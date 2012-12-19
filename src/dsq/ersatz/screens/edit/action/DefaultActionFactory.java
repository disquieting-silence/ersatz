package dsq.ersatz.screens.edit.action;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import dsq.ersatz.screens.edit.core.DefaultRipostes;
import dsq.ersatz.screens.edit.core.Ripostes;
import dsq.ersatz.screens.edit.core.Targets;
import dsq.ersatz.screens.edit.core.DefaultTargets;
import dsq.ersatz.screens.edit.data.RiposteId;

public class DefaultActionFactory implements ActionFactory {
    public UiActions nu(final ListActivity activity, final SQLiteDatabase db, final RiposteId id) {
        final Targets targets = new DefaultTargets(activity, db, id);
        final Ripostes ripostes = new DefaultRipostes(activity, db, id);

        ripostes.readFromDb();
        targets.refresh();

        final DefaultActions actions = new DefaultActions(activity, ripostes, targets, id);
        return new DefaultUiActions(actions);
    }
}
