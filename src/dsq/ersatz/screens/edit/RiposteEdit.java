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

import java.util.Arrays;

public class RiposteEdit extends ListActivity {

    private Long id;
    
    private final DbLifecycle lifecycle = new DefaultDbLifecycle();

    private TargetList targets;
    private RiposteTy ripostes;
    private Actions actions;

    private final Contacts contacts = new DefaultContacts();
    private final StateExtractor extractor = new DefaultStateExtractor();


    
    private SQLiteDatabase db;

    private final Menus menus = new DefaultMenus();

    /* Might make these static */
    private Tabs tabs = new DefaultTabs();
    private Buttons buttons = new DefaultButtons();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.riposte_edit);
        setTitle(R.string.edit_ui_title);

        db = lifecycle.open(this);

        final long rawId = (Long) extractor.strict(RiposteTable.ID, savedInstanceState, getIntent());
        final RiposteId id = new RiposteId(rawId);
        targets = new DefaultTargetList(this, db, id);
        ripostes = new DefaultRiposteTy(this, db, id);
        actions = new DefaultActions(this, ripostes, targets, id);

        setupTabs();
        ripostes.readFromDb();
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
                actions.revert();
            }
        });
    }

    public void onBackPressed() {
        actions.revert();
    }

    private void setupConfirm() {
        buttons.register(this, R.id.confirm, new Click() {
            public void click(final View view) {
                actions.confirm();
            }
        });
    }

    private void setupBrowse() {
        Button browseButton = (Button) findViewById(R.id.browse);
        browseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                actions.browse();
            }
        });
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (reqCode == Requests.PICK_CONTACT_REQUEST && resultCode == Activity.RESULT_OK) {
            actions.addContact(data);
        } else if (reqCode == Requests.INSERT_TEMPLATE_REQUEST && resultCode == Activity.RESULT_OK) {
            String template = data.getStringExtra(PlantTable.TEMPLATE);
            actions.insertText(template);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(RiposteTable.ID, id);
    }
}
