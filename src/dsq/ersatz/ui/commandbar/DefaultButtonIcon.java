package dsq.ersatz.ui.commandbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import dsq.ersatz.R;
import dsq.ersatz.action.SimpleAction;

public class DefaultButtonIcon extends LinearLayout implements ButtonIcon {

    private final ImageButton button;

    private boolean enabled = true;
    private final Context context;

    private ButtonImages images;

    public DefaultButtonIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        final TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CommandBarIconLayout);
        final String iconBase = attributes.getString(R.styleable.CommandBarIconLayout_icon);

        button = new ImageButton(context);

        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(params);

        addView(button);
        images = new DefaultButtonImages(iconBase);
        setupSelector();
    }

    private void setupSelector() {
        final StateListDrawable states = new StateListDrawable();
        setSelectorState(states, images.pressed(), new int[]{android.R.attr.state_pressed});
        setSelectorState(states, images.normal(), new int[0]);

        final StateListDrawable disabledStates = new StateListDrawable();
        setSelectorState(disabledStates, images.disabled(), new int[0]);

        button.setImageDrawable(enabled ? states : disabledStates);
        button.setBackgroundDrawable(getImage("transparent"));
    }

    private void setSelectorState(final StateListDrawable states, final String imageName, final int[] stateSet) {
        final Drawable image = getImage(imageName);
        if (image != null) states.addState(stateSet, image);
    }

    private Drawable getImage(final String image) {
        final int pressedId = getResourceByName(image);
        Log.v("ERSATZ", image + " " + pressedId);
        return pressedId != 0 ? getResources().getDrawable(pressedId) : null;
    }

    private int getResourceByName(final String image) {
        return context.getResources().getIdentifier(image, "drawable", context.getPackageName());
    }

    /* FIX: Really wish this was an interface .... Dupe from listless. */
    public void setAction(final SimpleAction action) {
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View view) {
                if (enabled) action.run();
            }
        });
    }

    // FIX 22/12/12 Clean up these button images.
    public void setActionEnabled(final boolean enabled) {
        this.enabled = enabled;
        setupSelector();
    }

    public void setImages(final ButtonImages images) {
        this.images = images;
        setupSelector();
    }
}
