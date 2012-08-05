package dsq.ersatz.db.plant;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import dsq.thedroid.db.DbAccess;
import dsq.thedroid.db.DefaultDbAccess;

public class DefaultPlantDbAdapter implements PlantDbAdapter {
    
    private DbAccess dbAccess = new DefaultDbAccess();
    
    private final SQLiteDatabase db;

    public DefaultPlantDbAdapter(SQLiteDatabase db) {
        this.db = db;
    }

    public boolean deleteById(long id) {
        return dbAccess.deleteById(db, PlantTable.TABLE, id);
    }

    public Cursor fetchAll() {
        return dbAccess.fetchAll(db, PlantTable.TABLE, PlantTable.ALL_COLUMNS);
    }

    public Cursor fetchById(long id) throws SQLException {
        return dbAccess.fetchById(db, PlantTable.TABLE, PlantTable.ALL_COLUMNS, id);
    }
}
