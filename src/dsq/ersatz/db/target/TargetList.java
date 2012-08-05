package dsq.ersatz.db.target;

import dsq.ersatz.R;
import dsq.thedroid.ui.ComponentIndex;
import dsq.thedroid.ui.ListMapping;

public class TargetList implements ListMapping {

    private final static String[] source = new String[]{
            TargetTable.PHONE_NUM,
            TargetTable.CONTACT_NAME
    };

    private final static ComponentIndex[] dest = new ComponentIndex[]{
            new ComponentIndex(R.id.phone_num),
            new ComponentIndex(R.id.contact_name)
    };

    public String[] source() {
        return source;        
    }

    public ComponentIndex[] dest() {
        return dest;
    }
}
