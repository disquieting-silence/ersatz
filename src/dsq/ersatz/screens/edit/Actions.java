package dsq.ersatz.screens.edit;

import android.os.Bundle;

public interface Actions extends ContactActions, TemplateActions {
    void confirm();
    void revert();
    void insertText(String text);
    void delete(TargetId id);
    void refreshTargets();
    void refreshRiposte();

    void backup(final Bundle outState);
}
