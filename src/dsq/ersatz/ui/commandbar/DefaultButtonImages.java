package dsq.ersatz.ui.commandbar;

public class DefaultButtonImages implements ButtonImages {

    private final String base;

    public DefaultButtonImages(final String base) {
        this.base = base;
    }

    public String normal() {
        return base;
    }

    public String pressed() {
        return base + "_pressed";
    }

    public String disabled() {
        return base + "_disabled";
    }
}
