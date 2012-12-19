package dsq.ersatz.screens.edit;

import dsq.thedroid.contacts.BasicContact;

public interface Actions extends ContactActions {
    void confirm();
    void revert();
    void insertText(String text);
}
