package dsq.ersatz.db.plant;

import android.database.Cursor;
import android.view.View;

public class DefaultPlantViewBinder implements PlantViewBinder {
    public boolean setViewValue(View view, Cursor cursor, int i) {
        return false;
    }
}
