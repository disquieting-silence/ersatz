package dsq.ersatz.screens.edit;

public interface Actions extends ContactActions, TemplateActions {
    void confirm();
    void revert();
    void insertText(String text);
    void delete(TargetId id);
}
