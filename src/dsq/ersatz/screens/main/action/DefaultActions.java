package dsq.ersatz.screens.main.action;

import android.app.ListActivity;
import android.content.Intent;
import dsq.ersatz.data.data.RiposteId;
import dsq.ersatz.db.riposte.RiposteTable;
import dsq.ersatz.requests.Requests;
import dsq.ersatz.screens.edit.RiposteEdit;
import dsq.ersatz.screens.main.core.Ripostes;
import dsq.ersatz.screens.settings.Settings;

public class DefaultActions implements Actions {

    private final ListActivity activity;
    private final Ripostes ripostes;

    public DefaultActions(final ListActivity activity, final Ripostes ripostes) {
        this.activity = activity;
        this.ripostes = ripostes;
    }

    public RiposteId nu() {
        return ripostes.nu();
    }

    public void delete(final RiposteId id) {
        ripostes.delete(id);
        ripostes.refresh();
    }

    public void launchEdit(final RiposteId id) {
        launch(id, Requests.EDIT_RIPOSTE_REQUEST);
    }

    public void launchAdd() {
        final RiposteId rId = nu();
        launch(rId, Requests.ADD_RIPOSTE_REQUEST);
    }

    public void launchSettings() {
        final Intent intent = new Intent(activity, Settings.class);
        activity.startActivity(intent);
    }

    public void toggleEnabled(final RiposteId riposteId) {
        ripostes.toggleEnabled(riposteId);
        ripostes.refresh();
    }

    private void launch(final RiposteId id, int requestCode) {
        final Intent intent = new Intent(activity, RiposteEdit.class);
        intent.putExtra(RiposteTable.ID, id.value);
        activity.startActivityForResult(intent, requestCode);
    }
}
