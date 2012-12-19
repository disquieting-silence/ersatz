package dsq.ersatz.screens.edit.action;

import android.os.Bundle;
import dsq.ersatz.action.IdAction;
import dsq.ersatz.action.IntentAction;
import dsq.ersatz.action.SimpleAction;

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
