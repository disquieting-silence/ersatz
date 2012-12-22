package dsq.ersatz.ui.commandbar;

public class DefaultCommandId implements CommandId {

    private long value = -1;

    public long get() {
        return value;
    }

    public void set(final long id) {
        value = id;
    }

    public boolean isSet() {
        return value != -1;
    }
}
