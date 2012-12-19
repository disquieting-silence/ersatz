package dsq.ersatz.screens.plant.action;

import android.app.Activity;
import dsq.ersatz.db.plant.PlantTable;
import dsq.ersatz.screens.edit.RiposteEdit;
import dsq.ersatz.screens.plant.core.Plant;
import dsq.ersatz.util.DefaultFinish;
import dsq.ersatz.util.Finish;

public class DefaultActions implements Actions {

    private final Finish finish = new DefaultFinish();

    private final Activity activity;
    private final Plant plant;

    public DefaultActions(final Activity activity, final Plant plant) {
        this.activity = activity;
        this.plant = plant;
    }

    public void confirm() {
        final String template = plant.get();
        if (template != null) {
            finish.resultWith(activity, Activity.RESULT_OK, RiposteEdit.class, PlantTable.TEMPLATE, template);
        } else {
            finish.result(activity, Activity.RESULT_CANCELED, RiposteEdit.class);
        }
    }

    public void cancel() {
        finish.result(activity, Activity.RESULT_CANCELED, RiposteEdit.class);
    }
}
