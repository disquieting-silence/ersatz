package dsq.ersatz.screens.edit.core;

import dsq.ersatz.screens.edit.data.TargetId;
import dsq.thedroid.contacts.BasicContact;

public interface TargetList {
    void revert();
    void update(BasicContact contact);
    void refresh();
    void delete(TargetId tid);
}
