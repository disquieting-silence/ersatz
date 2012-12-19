package dsq.ersatz.screens.edit;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import dsq.ersatz.db.plant.PlantTable;
import dsq.ersatz.requests.Requests;
import dsq.ersatz.screens.plant.Plant;
import dsq.thedroid.ui.DefaultTextInserter;
import dsq.thedroid.ui.TextInserter;

public class DefaultTemplateActions implements TemplateActions {

    private final TextInserter inserter = new DefaultTextInserter();

    private final Activity activity;

    public DefaultTemplateActions(final Activity activity) {
        this.activity = activity;
    }

    public void insertText(final String text) {
        final View view = activity.getCurrentFocus();
        inserter.tryInsert(view, text);
    }

    public void insertTemplate(final Intent template) {
        String s = template.getStringExtra(PlantTable.TEMPLATE);
        insertText(s);
    }

    public void showTemplates() {
        final Intent dest = new Intent(activity, Plant.class);
        activity.startActivityForResult(dest, Requests.INSERT_TEMPLATE_REQUEST);
    }
}
