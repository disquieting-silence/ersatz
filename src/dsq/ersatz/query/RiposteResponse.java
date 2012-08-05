package dsq.ersatz.query;

import android.content.Context;
import dsq.ersatz.db.riposte.Riposte;

import java.util.List;

public interface RiposteResponse {
    List<Riposte> find(Context context, String localNumber, long time);
    boolean update(Context context, List<Riposte> ripostes, long time);

}
