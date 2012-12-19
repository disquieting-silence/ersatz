package dsq.ersatz.screens.edit.action;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import dsq.ersatz.screens.edit.core.DefaultRiposteTy;
import dsq.ersatz.screens.edit.core.RiposteTy;
import dsq.ersatz.screens.edit.core.TargetList;
import dsq.ersatz.screens.edit.core.DefaultTargetList;
import dsq.ersatz.screens.edit.data.RiposteId;

public class DefaultActionFactory implements ActionFactory {
    public UiActions nu(final ListActivity activity, final SQLiteDatabase db, final RiposteId id) {
        final TargetList targets = new DefaultTargetList(activity, db, id);
        final RiposteTy ripostes = new DefaultRiposteTy(activity, db, id);

        ripostes.readFromDb();
        targets.refresh();

        final DefaultActions actions = new DefaultActions(activity, ripostes, targets, id);
        return new DefaultUiActions(actions);
    }
}
