package dsq.ersatz.ui.commandbar;

import dsq.ersatz.action.IdAction;

public interface Commandbar {
    void trigger(int actionId);
    void update();
    void register();

    ButtonIcon get(int actionId);
}
