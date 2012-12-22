package dsq.ersatz.screens.main;

import dsq.ersatz.ui.list.SelectableDataList;

// FIX 22/12/12 Questionable concept. See how it pans out.
public class DefaultRabbit implements Rabbit {

    private SelectableDataList list = null;

    public SelectableDataList getList() {
        return list;
    }

    public void setList(final SelectableDataList list) {
        this.list = list;
    }
}
