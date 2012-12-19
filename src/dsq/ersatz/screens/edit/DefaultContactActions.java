package dsq.ersatz.screens.edit;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import dsq.ersatz.requests.Requests;
import dsq.thedroid.contacts.BasicContact;
import dsq.thedroid.contacts.Contacts;
import dsq.thedroid.contacts.DefaultContacts;
import dsq.thedroid.contacts.NoPhoneNumberException;

public class DefaultContactActions implements ContactActions {

    private final Contacts contacts = new DefaultContacts();

    private final Activity activity;
    private final TargetList targets;

    public DefaultContactActions(final Activity activity, final TargetList targets) {
        this.activity = activity;
        this.targets = targets;
    }

    public void addContact(final BasicContact contact) {
        targets.update(contact);
    }

    public void addContact(final Intent contact) {
        try {
            BasicContact b = contacts.process(activity, contact);
            addContact(b);
        } catch (NoPhoneNumberException e) {
            Log.v("ERSATZ", "Phone number not found in contact.");
        }
    }

    public void browse() {
        contacts.browse(activity, Requests.PICK_CONTACT_REQUEST);
    }
}
