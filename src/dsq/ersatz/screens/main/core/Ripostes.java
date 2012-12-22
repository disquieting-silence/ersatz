package dsq.ersatz.screens.main.core;

import dsq.ersatz.action.IdAction;
import dsq.ersatz.data.data.RiposteId;
import dsq.ersatz.data.data.TargetId;
import dsq.thedroid.contacts.BasicContact;

public interface Ripostes {
    RiposteId nu();
    void refresh();
    void delete(RiposteId rId);

    void broadcast();
    void onSelect(IdAction action);
}
