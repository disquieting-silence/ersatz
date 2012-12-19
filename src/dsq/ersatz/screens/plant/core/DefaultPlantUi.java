package dsq.ersatz.screens.plant.core;

import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class DefaultPlantUi implements PlantUi {

    private final TextView preview;
    private final Spinner spinner;

    private String current = null;

    public DefaultPlantUi(final TextView preview, final Spinner spinner) {
        this.preview = preview;
        this.spinner = spinner;
    }

    public void setPreview(final String text) {
        current = text;
        preview.setText(text);
    }

    public String get() {
        return current;
    }

    public void noPreview(final String placeholder) {
        current = null;
        preview.setText(placeholder);
    }
}
