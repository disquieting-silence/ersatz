package dsq.ersatz.screens.main;

import dsq.ersatz.ui.list.SelectableDataList;

public interface Rabbit<A> {
    SelectableDataList<A> getList();
    void setList(SelectableDataList<A> list);
}
