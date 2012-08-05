package dsq.ersatz.screens.edit;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
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
import dsq.thedroid.contacts.BasicContact;
import dsq.thedroid.contacts.Contacts;
import dsq.thedroid.contacts.DefaultContacts;
import dsq.thedroid.contacts.NoPhoneNumberException;
import dsq.thedroid.db.DbUtils;
import dsq.thedroid.ui.*;
import dsq.thedroid.util.*;
import dsq.ersatz.db.riposte.DefaultRiposteDbAdapter;
import dsq.ersatz.db.riposte.RiposteDbAdapter;
import dsq.ersatz.db.target.*;

import java.util.ArrayList;
import java.util.List;

public class RiposteEdit extends ListActivity {

    private Long id;
    
    private final DbLifecycle lifecycle = new DefaultDbLifecycle();
    private RiposteDbAdapter riposteAdapter;
    private TargetDbAdapter targetAdapter;

    private final Contacts contacts = new DefaultContacts();
    private final StateExtractor extractor = new DefaultStateExtractor();
    private final IdCursor cursors = new DefaultIdCursor();
    private final TextInserter inserter = new DefaultTextInserter();
    
    private SQLiteDatabase db;

    private final Lists lists = new DefaultLists();
    private final Menus menus = new DefaultMenus();

    private EditText txtName;
    private EditText txtMessage;

    private List<Target> originalTargets = new ArrayList<Target>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.riposte_edit);
        setTitle(R.string.edit_ui_title);

        db = lifecycle.open(this);
        riposteAdapter = new DefaultRiposteDbAdapter(db);
        targetAdapter = new DefaultTargetDbAdapter(db);

        id = (Long) extractor.strict(RiposteTable.ID, savedInstanceState, getIntent());

        txtName = (EditText) findViewById(R.id.name);
        txtMessage = (EditText) findViewById(R.id.message);
        

        originalTargets = targetAdapter.getTargets(id);


        setupTabs();
        

        populateFields();
        refreshList();
        registerForContextMenu(getListView());

        setupBrowse();
        setupConfirm();
        setupCancel();
    }

    private void setupTabs() {
        TabHost tabHost=(TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        addTab(tabHost, R.id.tab_general, R.string.tab_general);
        addTab(tabHost, R.id.tab_recipients, R.string.tab_recipients);
    }

    private void addTab(TabHost tabHost, int tabId, int labelId) {
        String label = getString(labelId);
        TabHost.TabSpec spec = tabHost.newTabSpec(label);
        spec.setIndicator(label);
        spec.setContent(tabId);
        tabHost.addTab(spec);
    }

    private void refreshList() {
        SimpleCursorAdapter.ViewBinder view = new DefaultTargetViewBinder(targetAdapter);
        Cursor cursor = targetAdapter.fetchByRiposteId(id);
        lists.refresh(this, cursor, getListView(), new ComponentIndex(R.layout.target_row), new TargetList(), view);
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
        long id = info.id;
        switch (item.getItemId()) {
            case R.id.delete_target:
                targetAdapter.deleteById(id);
                refreshList();
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
        Button cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
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
        revertTargets();
        finishWithResult(RESULT_CANCELED);
    }

    private void revertTargets() {
        boolean delSuccess = targetAdapter.deleteTargets(id);
        for (Target orig : originalTargets) {
            // FIX 24/01/12 Probably make the order of these arguments consistent.
            targetAdapter.create(id, orig.number, orig.name);
        }
    }

    private void setupConfirm() {
        Button confirmButton = (Button) findViewById(R.id.confirm);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = txtName.getText().toString();
                String message = txtMessage.getText().toString();
                Log.v("ERSATZ", "Saving message: " + message);
                riposteAdapter.update(id, name, message);
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
                boolean isDuplicate = targetAdapter.contains(id, contact.number);
                if (!isDuplicate) {
                    targetAdapter.create(id, contact.number, contact.name);
                } 
            } catch (NoPhoneNumberException e) {
                Log.v("ERSATZ", "Phone number not found in contact.");
            } 
        } else if (reqCode == Requests.INSERT_TEMPLATE_REQUEST && resultCode == Activity.RESULT_OK) {
            String template = data.getStringExtra(PlantTable.TEMPLATE);
            inserter.tryInsert(getCurrentFocus(), template);
        }
    }

    private void populateFields() {
        cursors.go(riposteAdapter, this, id, new CursorOperation() {
            public void go(long id, Cursor cursor) {
                String name = DbUtils.getColumn(cursor, RiposteTable.NAME);
                String message = DbUtils.getColumn(cursor, RiposteTable.MESSAGE);
                txtName.setText(name);
                txtMessage.setText(message);
            }
        });
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(RiposteTable.ID, id);
    }

}
