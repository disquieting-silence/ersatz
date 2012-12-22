package dsq.ersatz.screens.main.action;

import dsq.ersatz.action.IdAction;
import dsq.ersatz.data.data.RiposteId;

public interface Actions {
    RiposteId nu();
    void delete(RiposteId id);
    void launchEdit(RiposteId id);
    void launchAdd();
    void launchSettings();

    void updateUi(final long id);
}
