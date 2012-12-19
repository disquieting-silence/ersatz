package dsq.ersatz.screens.edit;

import android.content.Intent;

public interface TemplateActions {
    void insertText(String text);
    void insertTemplate(Intent template);
    void showTemplates();
}
