package dsq.ersatz.ui.list;

public interface ItemAction<A> {
    void run(long id, A v);
}
