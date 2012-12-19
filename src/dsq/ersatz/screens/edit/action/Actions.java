package dsq.ersatz.screens.edit.action;

import android.os.Bundle;
import dsq.ersatz.screens.edit.data.TargetId;

public interface Actions extends ContactActions, TemplateActions {
    void confirm();
    void revert();
    void insertText(String text);
    void delete(TargetId id);
    void refreshTargets();
    void refreshRiposte();

    void backup(final Bundle outState);
}
