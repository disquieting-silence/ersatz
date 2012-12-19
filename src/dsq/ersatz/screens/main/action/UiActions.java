package dsq.ersatz.screens.main.action;

import dsq.ersatz.action.IdAction;
import dsq.ersatz.action.IntentAction;
import dsq.ersatz.action.SimpleAction;

public interface UiActions {
    IdAction delete();
    IdAction launchEdit();
    SimpleAction launchAdd();
    IntentAction cancel();
    SimpleAction launchSettings();
}
