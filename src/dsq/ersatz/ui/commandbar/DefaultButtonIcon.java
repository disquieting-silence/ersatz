package dsq.ersatz.ui.commandbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import dsq.ersatz.R;
import dsq.ersatz.action.SimpleAction;

public class DefaultButtonIcon extends LinearLayout implements ButtonIcon {

    private final ImageButton button;

    private boolean enabled = true;

    public DefaultButtonIcon(Context context, AttributeSet attrs) {
        super(context, attrs);

        final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CommandBarIconLayout);
        final String value = attributes.getString(R.styleable.CommandBarIconLayout_icon);

        button = new ImageButton(context);

        final int drawable = context.getResources().getIdentifier(value, "drawable", context.getPackageName());
        if (drawable > -1) button.setBackgroundResource(drawable);

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(params);

        addView(button);
    }

    /* FIX: Really wish this was an interface .... Dupe from listless. */
    public void setAction(final SimpleAction action) {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                if (enabled) action.run();
            }
        });
    }

    public void setActionEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
}
