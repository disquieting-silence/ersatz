package dsq.ersatz.db.riposte;

import dsq.thedroid.db.DbAdapter;

public interface RiposteDbAdapter extends DbAdapter {
    long create(String name, String description);
    boolean update(long id, String name, String description);
    boolean update(long id, boolean enabled);
    boolean updateAll(boolean enabled);
}
