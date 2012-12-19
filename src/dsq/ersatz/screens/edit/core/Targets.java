package dsq.ersatz.screens.edit.core;

import dsq.ersatz.data.data.TargetId;
import dsq.thedroid.contacts.BasicContact;

public interface Targets {
    void revert();
    void update(BasicContact contact);
    void refresh();
    void delete(TargetId tid);
}
