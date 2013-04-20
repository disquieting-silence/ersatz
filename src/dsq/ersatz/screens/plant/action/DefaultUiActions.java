package dsq.ersatz.screens.plant.action;


import dsq.sycophant.action.SimpleAction;

public class DefaultUiActions implements UiActions {

    private final Actions actions;

    public DefaultUiActions(final Actions actions) {
        this.actions = actions;
    }

    public SimpleAction confirm() {
        return new SimpleAction() {
            public void run() {
                actions.confirm();
            }
        };
    }

    public SimpleAction cancel() {
        return new SimpleAction() {
            public void run() {
                actions.cancel();
            }
        };
    }
}
