package dsq.ersatz.screens.plant;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import dsq.ersatz.R;
import dsq.ersatz.db.general.DbLifecycle;
import dsq.ersatz.db.general.DefaultDbLifecycle;
import dsq.ersatz.db.plant.DefaultPlantDbAdapter;
import dsq.ersatz.db.plant.DefaultPlantViewBinder;
import dsq.ersatz.db.plant.PlantDbAdapter;
import dsq.thedroid.ui.Buttons;
import dsq.thedroid.ui.DefaultButtons;
import dsq.ersatz.db.plant.PlantTable;

public class Plant extends Activity {

    private final DbLifecycle lifecycle = new DefaultDbLifecycle();
    private PlantDbAdapter adapter;

    private SQLiteDatabase db;

    private final Buttons buttons = new DefaultButtons();
    private TextView preview;
    private Spinner spinner;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_plant);
        setTitle(R.string.insert_plant_ui_title);

        db = lifecycle.open(this);
        adapter = new DefaultPlantDbAdapter(db);        

        preview = (TextView)findViewById(R.id.preview);
        spinner = (Spinner) findViewById(R.id.plant_spinner);
        setupTemplates();
        
        buttons.cancel(R.id.cancel, this);
        buttons.confirm(R.id.confirm, this, new View.OnClickListener() {
            public void onClick(View view) {
                String template = getTemplate();
                if (template != null) {
                    Intent intent = new Intent();
                    intent.putExtra(PlantTable.TEMPLATE, template);
                    setResult(RESULT_OK, intent);
                } else {
                    setResult(RESULT_CANCELED);
                }
            }
        });
    }

    private void setupTemplates() {
        SimpleCursorAdapter.ViewBinder view = new DefaultPlantViewBinder();

        // FIX 18/02/12 Inspired by: http://www.dcpagesapps.com/developer-resources/android/21-android-tutorial-spinners?start=2
        String[] from = new String[] {PlantTable.TAG};
        int[] to = new int[] { android.R.id.text1 };
        Cursor cursor = adapter.fetchAll();
        startManagingCursor(cursor);
        SimpleCursorAdapter spinnerAdapter =
                new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item, cursor, from, to );
        spinnerAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
        
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String template = getTemplate();
                preview.setText(template);
            }

            public void onNothingSelected(AdapterView<?> parentView) {
                String text = getString(R.string.insert_plant_no_template);
                preview.setText(text);
            }

        });
    }

    private String getTemplate() {
        Cursor selectedCursor = (Cursor)spinner.getSelectedItem();
        if (selectedCursor == null) return null;
        int colIndex = selectedCursor.getColumnIndex(PlantTable.TEMPLATE);
        return selectedCursor.getString(colIndex);        
    }
}
