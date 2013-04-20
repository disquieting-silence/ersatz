package dsq.ersatz.screens.main;

import dsq.sycophant.datalist.SelectableDataList;

public interface Rabbit<A> {
    SelectableDataList<A> getList();
    void setList(SelectableDataList<A> list);
}
