package dsq.ersatz.screens.edit.action;

import android.content.Intent;
import android.os.Bundle;
import dsq.ersatz.data.data.TargetId;
import dsq.sycophant.action.IdAction;
import dsq.sycophant.action.IntentAction;
import dsq.sycophant.action.SimpleAction;

public class DefaultUiActions implements UiActions {

    private final Actions actions;

    public DefaultUiActions(final Actions actions) {
        this.actions = actions;
    }

    public IntentAction addContact() {
        return new IntentAction() {
            public void run(final Intent intent) {
                actions.addContact(intent);
            }
        };
    }

    public IntentAction insertTemplate() {
        return new IntentAction() {
            public void run(final Intent intent) {
                actions.insertTemplate(intent);
            }
        };
    }

    public SimpleAction confirm() {
        return new SimpleAction() {
            public void run() {
                actions.confirm();
            }
        };
    }

    public SimpleAction revert() {
        return new SimpleAction() {
            public void run() {
                actions.revert();
            }
        };
    }

    public SimpleAction browse() {
        return new SimpleAction() {
            public void run() {
                actions.browse();
            }
        };
    }

    public IdAction deleteTarget() {
        return new IdAction() {
            public void run(final long id) {
                final TargetId tid = new TargetId(id);
                actions.delete(tid);
            }
        };
    }

    public SimpleAction showTemplates() {
        return new SimpleAction() {
            public void run() {
                actions.showTemplates();
            }
        };
    }

    public SimpleAction refreshTargets() {
        return new SimpleAction() {
            public void run() {
                actions.refreshTargets();
            }
        };
    }

    public SimpleAction refreshRiposte() {
        return new SimpleAction() {
            public void run() {
                actions.refreshRiposte();
            }
        };
    }

    public void backup(final Bundle outState) {
        actions.backup(outState);
    }
}
