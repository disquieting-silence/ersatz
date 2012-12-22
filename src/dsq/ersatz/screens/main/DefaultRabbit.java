package dsq.ersatz.screens.main;

import dsq.ersatz.ui.list.SelectableDataList;

// FIX 22/12/12 Questionable concept. See how it pans out.
public class DefaultRabbit<A> implements Rabbit<A> {

    private SelectableDataList<A> list = null;

    public SelectableDataList<A> getList() {
        return list;
    }

    public void setList(final SelectableDataList<A> list) {
        this.list = list;
    }
}
