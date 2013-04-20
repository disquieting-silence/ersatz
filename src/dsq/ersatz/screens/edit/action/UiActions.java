package dsq.ersatz.screens.edit.action;

import android.os.Bundle;
import dsq.sycophant.action.IdAction;
import dsq.sycophant.action.IntentAction;
import dsq.sycophant.action.SimpleAction;


public interface UiActions {
    IntentAction addContact();
    IntentAction insertTemplate();
    SimpleAction confirm();
    SimpleAction revert();
    SimpleAction browse();
    IdAction deleteTarget();
    SimpleAction showTemplates();
    SimpleAction refreshTargets();
    SimpleAction refreshRiposte();

    void backup(final Bundle outState);
}
