package dsq.ersatz.screens.main.action;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import dsq.ersatz.screens.main.Rabbit;
import dsq.ersatz.screens.main.core.DefaultRipostes;
import dsq.ersatz.screens.main.core.Ripostes;

public class DefaultActionFactory implements ActionFactory {

    public UiActions nu(final ListActivity activity, final SQLiteDatabase db, final Rabbit rabbit) {
        final Ripostes ripostes = new DefaultRipostes(activity, db, rabbit);
        final Actions actions = new DefaultActions(activity, ripostes);
        ripostes.refresh();
        ripostes.broadcast();

        return new DefaultUiActions(actions);
    }
}
