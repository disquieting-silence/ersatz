package dsq.ersatz.screens.main.action;


import dsq.sycophant.action.IdAction;
import dsq.sycophant.action.IntentAction;
import dsq.sycophant.action.SimpleAction;

public interface UiActions {
    IdAction delete();
    IdAction launchEdit();
    SimpleAction launchAdd();
    IntentAction cancel();
    SimpleAction launchSettings();
    IdAction toggleEnabled();
    SimpleAction refresh();
}
