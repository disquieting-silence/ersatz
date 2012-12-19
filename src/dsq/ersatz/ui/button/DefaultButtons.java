package dsq.ersatz.ui.button;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import dsq.ersatz.R;
import dsq.ersatz.screens.edit.EditStruct;

public class DefaultButtons implements Buttons {
    public Button register(final Activity activity, final int id, final Click click) {
        Button r = (Button) activity.findViewById(id);
        r.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                click.click(view);
            }
        });
        return r;
    }
}
