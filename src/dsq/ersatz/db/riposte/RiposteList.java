package dsq.ersatz.db.riposte;

import dsq.ersatz.R;
import dsq.thedroid.ui.ComponentIndex;
import dsq.thedroid.ui.ListMapping;

public class RiposteList implements ListMapping {

    private final static String[] source = new String[]{
            RiposteTable.NAME
//            RiposteTable.ENABLED
    };

    private final static ComponentIndex[] dest = new ComponentIndex[]{
            new ComponentIndex(R.id.name)
//            new ComponentIndex(R.id.enabled)
    };

    public String[] source() {
        return source;
    }

    public ComponentIndex[] dest() {
        return dest;
    }
}
