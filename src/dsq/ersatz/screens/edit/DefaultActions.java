package dsq.ersatz.screens.edit;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import dsq.ersatz.requests.Requests;
import dsq.ersatz.screens.main.MainFrame;
import dsq.ersatz.util.DefaultFinish;
import dsq.ersatz.util.Finish;
import dsq.thedroid.contacts.BasicContact;
import dsq.thedroid.contacts.Contacts;
import dsq.thedroid.contacts.DefaultContacts;
import dsq.thedroid.ui.DefaultTextInserter;
import dsq.thedroid.ui.TextInserter;

public class DefaultActions implements Actions {

    private final Finish finish = new DefaultFinish();
    private final TextInserter inserter = new DefaultTextInserter();


    private final Activity activity;
    private final RiposteTy ripostes;
    private final TargetList targets;
    private final RiposteId id;

    private final ContactActions contacts;

    public DefaultActions(final Activity activity, final RiposteTy ripostes, final TargetList targets, final RiposteId id) {
        this.activity = activity;
        this.ripostes = ripostes;
        this.targets = targets;
        this.id = id;

        contacts = new DefaultContactActions(activity, targets);
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

    public void insertText(final String text) {
        final View view = activity.getCurrentFocus();
        inserter.tryInsert(view, text);
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
