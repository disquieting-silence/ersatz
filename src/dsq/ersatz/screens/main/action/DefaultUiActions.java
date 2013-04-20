package dsq.ersatz.screens.main.action;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import dsq.ersatz.data.data.RiposteId;
import dsq.ersatz.db.riposte.RiposteDbAdapter;
import dsq.sycophant.action.IdAction;
import dsq.sycophant.action.IntentAction;
import dsq.sycophant.action.SimpleAction;

// FIX 19/12/12 Split me up.
public class DefaultUiActions implements UiActions {

    private Actions actions;

    public DefaultUiActions(final Actions actions) {
        this.actions = actions;
    }

    public IdAction delete() {
        return new IdAction() {
            public void run(final long id) {
                deleteById(id);
            }
        };
    }

    public IdAction launchEdit() {
        return new IdAction() {
            public void run(final long id) {
                actions.launchEdit(new RiposteId(id));
            }
        };
    }

    public SimpleAction launchAdd() {
        return new SimpleAction() {
            public void run() {
                actions.launchAdd();
            }
        };
    }

    public IntentAction cancel() {
        return new IntentAction() {
            public void run(final Intent intent) {
                long id = intent != null ? intent.getLongExtra("id", -1) : -1;
                if (id > -1) {
                    deleteById(id);
                }
            }
        };
    }

    public SimpleAction launchSettings() {
        return new SimpleAction() {
            public void run() {
                actions.launchSettings();
            }
        };
    }

    public IdAction toggleEnabled() {
        return new IdAction() {
            public void run(final long id) {
                actions.toggleEnabled(new RiposteId(id));
            }
        };

    }

    public SimpleAction refresh() {
        return new SimpleAction() {
            public void run() {
                actions.refresh();
            }
        };
    }

    private void deleteById(final long id) {
        final RiposteId rId = new RiposteId(id);
        actions.delete(rId);
    }
}
