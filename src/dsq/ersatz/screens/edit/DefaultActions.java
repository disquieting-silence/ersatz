package dsq.ersatz.screens.edit;

import android.app.Activity;
import android.content.Intent;
import dsq.ersatz.screens.main.MainFrame;
import dsq.ersatz.util.DefaultFinish;
import dsq.ersatz.util.Finish;
import dsq.thedroid.contacts.BasicContact;

public class DefaultActions implements Actions {

    private final Finish finish = new DefaultFinish();


    private final Activity activity;
    private final RiposteTy ripostes;
    private final TargetList targets;
    private final RiposteId id;

    private final ContactActions contacts;
    private final TemplateActions templates;

    public DefaultActions(final Activity activity, final RiposteTy ripostes, final TargetList targets, final RiposteId id) {
        this.activity = activity;
        this.ripostes = ripostes;
        this.targets = targets;
        this.id = id;

        contacts = new DefaultContactActions(activity, targets);
        templates = new DefaultTemplateActions(activity);
    }

    public void confirm() {
        ripostes.writeToDb();
        finishWithResult(Activity.RESULT_OK);
    }

    public void revert() {
        targets.revert();
        final int result = Activity.RESULT_CANCELED;
        finishWithResult(result);
    }

    public void browse() {
        contacts.browse();
    }

    public void delete(final TargetId id) {
        targets.delete(id);
    }

    public void insertText(final String text) {
        templates.insertText(text);
    }

    public void insertTemplate(final Intent template) {
        templates.insertTemplate(template);
    }

    public void showTemplates() {
        templates.showTemplates();
    }

    public void addContact(final BasicContact contact) {
        contacts.addContact(contact);
    }

    public void addContact(final Intent contact) {
        contacts.addContact(contact);
    }

    private void finishWithResult(final int result) {
        finish.resultWithId(activity, result, MainFrame.class, id.value);
    }
}