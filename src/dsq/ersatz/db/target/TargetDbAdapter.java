package dsq.ersatz.db.target;

import android.database.Cursor;
import android.database.SQLException;
import dsq.thedroid.db.DbAdapter;

import java.util.List;

public interface TargetDbAdapter extends DbAdapter {
    long create(long riposteId, String phone, String contactName);
    boolean contains(long id, String phone);

    Cursor fetchByRiposteId(long id) throws SQLException;
    List<Target> getTargets(long riposteId);
    boolean deleteTargets(long riposteId);
}
