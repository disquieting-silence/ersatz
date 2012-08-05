package dsq.ersatz.screens.settings;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import dsq.ersatz.R;
import dsq.ersatz.db.general.DbLifecycle;
import dsq.ersatz.db.general.DefaultDbLifecycle;
import dsq.ersatz.db.settings.DefaultSettingsUtil;
import dsq.ersatz.db.settings.SettingsUtil;


public class Settings extends Activity {
    
    private final DbLifecycle lifecycle = new DefaultDbLifecycle();
    private final SettingsUtil util = new DefaultSettingsUtil();

    private SQLiteDatabase db;
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ersatz_setting);

        db = lifecycle.open(this);

        setupCooloff();
        setupCancel();
        setupConfirm();
    }

    private void setupCooloff() {
        // FIX 26/01/12 http://developer.android.com/resources/tutorials/views/hello-spinner.html
        Spinner spinner = (Spinner) findViewById(R.id.cooloff_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.settings_ui_cooloff_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        long cooloff = util.getCooloff(db);
        Log.v("ERSATZ", "cooloff: " + cooloff);
        int index = adapter.getPosition(String.valueOf(cooloff));
        Log.v("ERSATZ", "value: " + index);
        spinner.setSelection(index);
    }

    protected void onDestroy() {
        lifecycle.close();
        super.onDestroy();
    }

    // FIX 26/01/12 Find some way to reuse this from RiposteEdit
    private void setupCancel() {
        Button cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupConfirm() {
        Button confirmButton = (Button) findViewById(R.id.confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Spinner spinner = (Spinner) findViewById(R.id.cooloff_spinner);
                String value = (String)spinner.getSelectedItem();
                long minutes = Long.parseLong(value);
                Log.v("ERSATZ", "minutes: " + minutes);
                util.setCooloff(db, minutes);
                finish();
            }
        });
    }
}
