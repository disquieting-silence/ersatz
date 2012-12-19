package dsq.ersatz.screens.edit;

import android.app.Activity;
import dsq.ersatz.screens.main.MainFrame;
import dsq.ersatz.util.DefaultFinish;
import dsq.ersatz.util.Finish;

public class DefaultActions implements Actions {

    private final Finish finish = new DefaultFinish();

    private final Activity activity;
    private final RiposteTy ripostes;
    private final TargetList targets;
    private final RiposteId id;

    public DefaultActions(final Activity activity, final RiposteTy ripostes, final TargetList targets, final RiposteId id) {
        this.activity = activity;
        this.ripostes = ripostes;
        this.targets = targets;
        this.id = id;
    }

    public void confirm() {
        ripostes.writeToDb();
        finishWithResult(Activity.RESULT_OK);
    }

    public void revert() {
        targets.revert();
        final int result = Activity.RESULT_CANCELED;
        finishWithResult(result);
    }

    public void browse() {
    }

    private void finishWithResult(final int result) {
        finish.resultWithId(activity, result, MainFrame.class, id.value);
    }
}
