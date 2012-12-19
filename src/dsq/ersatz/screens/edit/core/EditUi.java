package dsq.ersatz.screens.edit.core;

import dsq.ersatz.data.data.EditStruct;

public interface EditUi {
    void set(String name, String message);

    EditStruct get();
}
