package dsq.ersatz.screens.plant.core;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Spinner;
import android.widget.TextView;
import dsq.ersatz.R;
import dsq.ersatz.db.plant.DefaultPlantDbAdapter;
import dsq.ersatz.db.plant.PlantDbAdapter;
import dsq.ersatz.db.plant.PlantTable;
import dsq.ersatz.ui.spinner.DefaultSpinners;
import dsq.ersatz.ui.spinner.SpinnerListener;
import dsq.ersatz.ui.spinner.Spinners;

public class DefaultPlant implements Plant {

    private final PlantUi ui;
    private final Activity activity;
    private final PlantDbAdapter adapter;

    public DefaultPlant(final Activity activity, final SQLiteDatabase db) {
        this.activity = activity;
        adapter = new DefaultPlantDbAdapter(db);
        ui = setupUi();

    }

    private final Spinners spinners = new DefaultSpinners();

    private PlantUi setupUi() {
        final Spinner spinner = spinners.keyValue(activity, R.id.plant_spinner, PlantTable.TAG, PlantTable.TEMPLATE, adapter, new SpinnerListener() {
            public void none() {
                String text = activity.getString(R.string.insert_plant_no_template);
                ui.noPreview(text);
            }

            public void select(final String text) {
                ui.setPreview(text);
            }
        });
        final TextView preview = (TextView)activity.findViewById(R.id.preview);
        return new DefaultPlantUi(preview, spinner);
    }

    public String get() {
        return ui.get();
    }
}
