package dsq.ersatz.screens.plant.action;

import android.app.Activity;
import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import dsq.ersatz.screens.plant.core.DefaultPlant;
import dsq.ersatz.screens.plant.core.Plant;

public class DefaultActionFactory implements ActionFactory {
    public UiActions nu(final Activity activity, final SQLiteDatabase db) {
        final Plant plant = new DefaultPlant(activity, db);
        final Actions actions = new DefaultActions(activity, plant);
        return new DefaultUiActions(actions);
    }
}
