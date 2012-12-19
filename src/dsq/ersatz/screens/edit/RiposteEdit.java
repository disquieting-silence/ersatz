package dsq.ersatz.screens.edit;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import dsq.ersatz.R;
import dsq.ersatz.db.general.DbLifecycle;
import dsq.ersatz.db.general.DefaultDbLifecycle;
import dsq.ersatz.db.plant.PlantTable;
import dsq.ersatz.db.riposte.RiposteTable;
import dsq.ersatz.requests.Requests;
import dsq.ersatz.screens.main.MainFrame;
import dsq.ersatz.screens.plant.Plant;
import dsq.ersatz.ui.button.Buttons;
import dsq.ersatz.ui.button.Click;
import dsq.ersatz.ui.button.DefaultButtons;
import dsq.ersatz.ui.tab.DefaultTabs;
import dsq.ersatz.ui.tab.TabInfo;
import dsq.ersatz.ui.tab.Tabs;
import dsq.thedroid.contacts.BasicContact;
import dsq.thedroid.contacts.Contacts;
import dsq.thedroid.contacts.DefaultContacts;
import dsq.thedroid.contacts.NoPhoneNumberException;

import dsq.thedroid.ui.*;
import dsq.thedroid.util.*;
import dsq.ersatz.db.riposte.DefaultRiposteDbAdapter;
import dsq.ersatz.db.riposte.RiposteDbAdapter;
import dsq.ersatz.db.target.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RiposteEdit extends ListActivity {

    private Long id;
    
    private final DbLifecycle lifecycle = new DefaultDbLifecycle();
    private RiposteDbAdapter riposteAdapter;
    private TargetList targets;

    private final Contacts contacts = new DefaultContacts();
    private final StateExtractor extractor = new DefaultStateExtractor();

    private final TextInserter inserter = new DefaultTextInserter();
    
    private SQLiteDatabase db;

    private final Lists lists = new DefaultLists();
    private final Menus menus = new DefaultMenus();
    private final FieldLookup lookup = new DefaultFieldLookup();

    private EditUi ui;

    /* Might make these static */
    private Tabs tabs = new DefaultTabs();
    private Buttons buttons = new DefaultButtons();

    private List<Target> originalTargets = new ArrayList<Target>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.riposte_edit);
        setTitle(R.string.edit_ui_title);

        db = lifecycle.open(this);
        riposteAdapter = new DefaultRiposteDbAdapter(db);



        id = (Long) extractor.strict(RiposteTable.ID, savedInstanceState, getIntent());
        targets = new DefaultTargetList(this, db, new RiposteId(id));

        final EditText txtName = (EditText) findViewById(R.id.name);
        final EditText txtMessage = (EditText) findViewById(R.id.message);
        ui = new DefaultEditUi(txtName, txtMessage);

        setupTabs();
        lookup.load(this, riposteAdapter, ui, id);
        targets.refresh();
        registerForContextMenu(getListView());

        setupBrowse();
        setupConfirm();
        setupCancel();
    }

    private void setupTabs() {
        tabs.install(this, R.id.tabHost, Arrays.asList(
                new TabInfo(R.id.tab_general, R.string.tab_general),
                new TabInfo(R.id.tab_recipients, R.string.tab_recipients)
        ));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // FIX 21/01/12 Should this call super?
        return menus.options(this, menu, R.menu.edit);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menus.context(this, menu, R.menu.edit_context);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insert_plant:
                startActivityForResult(new Intent(this, Plant.class), Requests.INSERT_TEMPLATE_REQUEST);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.delete_target:
                targets.delete(new TargetId(info.id));
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        lifecycle.close();
        super.onDestroy();
    }

    private void setupCancel() {
        buttons.register(this, R.id.cancel, new Click() {
            public void click(final View view) {
                reject();
            }
        });
    }

    private void finishWithResult(int result) {
        Intent intent = new Intent(this, MainFrame.class);
        // FIX 24/01/12 Use constants for these intent keys.
        intent.putExtra("id", id);
        setResult(result, intent);
        finish();
    }

    public void onBackPressed() {
        reject();
    }

    private void reject() {
        targets.revert();
        finishWithResult(RESULT_CANCELED);
    }

    private void setupConfirm() {
        Button confirmButton = (Button) findViewById(R.id.confirm);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final EditStruct values = ui.get();
                Log.v("ERSATZ", "Saving message: " + values.message);
                riposteAdapter.update(id, values.name, values.message);
                finishWithResult(RESULT_OK);
            }
        });
    }

    private void setupBrowse() {
        Button browseButton = (Button) findViewById(R.id.browse);
        browseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                browseContacts();
            }
        });
    }

    private void browseContacts() {
        contacts.browse(this, Requests.PICK_CONTACT_REQUEST);
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (reqCode == Requests.PICK_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            try {
                BasicContact contact = contacts.process(this, data);
                targets.update(contact);
            } catch (NoPhoneNumberException e) {
                Log.v("ERSATZ", "Phone number not found in contact.");
            } 
        } else if (reqCode == Requests.INSERT_TEMPLATE_REQUEST && resultCode == Activity.RESULT_OK) {
            String template = data.getStringExtra(PlantTable.TEMPLATE);
            inserter.tryInsert(getCurrentFocus(), template);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(RiposteTable.ID, id);
    }

}
