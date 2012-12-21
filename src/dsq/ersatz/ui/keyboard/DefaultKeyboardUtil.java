package dsq.ersatz.ui.keyboard;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class DefaultKeyboardUtil implements KeyboardUtil {
    public void hide(final Context context, final View view) {
        final InputMethodManager manager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public void show(final Context context, final View view) {
        final InputMethodManager manager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }
}
