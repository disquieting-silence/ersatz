package dsq.ersatz.ui.list;

import dsq.ersatz.action.IdAction;

// FIX 22/12/12 Unwieldly name.
public interface SelectableDataList {
    long selected();
    void refresh();
    void onSelect(IdAction action);
}
