package dsq.ersatz.screens.edit;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;

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
