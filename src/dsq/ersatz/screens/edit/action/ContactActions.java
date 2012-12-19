package dsq.ersatz.screens.edit.action;

import android.content.Intent;
import dsq.thedroid.contacts.BasicContact;

public interface ContactActions {
    void addContact(BasicContact contact);
    void addContact(Intent contact);
    void browse();
}
