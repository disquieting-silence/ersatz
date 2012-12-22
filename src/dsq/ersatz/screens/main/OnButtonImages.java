package dsq.ersatz.screens.main;

import dsq.ersatz.ui.commandbar.ButtonImages;

public class OnButtonImages implements ButtonImages {

    public String normal() {
        return "icon_on";
    }

    public String pressed() {
        return "icon_pressed";
    }

    public String disabled() {
        return "icon_disabled";
    }
}