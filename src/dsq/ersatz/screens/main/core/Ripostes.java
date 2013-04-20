package dsq.ersatz.screens.main.core;

import dsq.ersatz.data.data.RiposteId;

public interface Ripostes {
    RiposteId nu();
    void refresh();
    void delete(RiposteId rId);

    void broadcast();

    void toggleEnabled(RiposteId riposteId);
}
