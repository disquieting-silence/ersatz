package dsq.ersatz.screens.edit;

import android.widget.EditText;

public class DefaultEditUi implements EditUi {
    private final EditText name;
    private final EditText message;

    public DefaultEditUi(final EditText name, final EditText message) {
        this.name = name;
        this.message = message;
    }

    public final void set(final String name, final String message) {
        this.name.setText(name);
        this.message.setText(message);
    }

    public final EditStruct get() {
        String n = name.getText().toString();
        String m = message.getText().toString();
        return new EditStruct(n, m);
    }
}
