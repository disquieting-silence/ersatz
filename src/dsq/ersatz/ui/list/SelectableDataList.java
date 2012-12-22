package dsq.ersatz.ui.list;

import dsq.ersatz.action.IdAction;

// FIX 22/12/12 Unwieldly name.
public interface SelectableDataList<A> {
    long selected();
    void refresh();
    void onSelect(ItemAction<A> action);
}
