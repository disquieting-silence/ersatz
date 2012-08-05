package dsq.ersatz.db.riposte;

public class Riposte {
    public final long id;
    public final String name;
    public final String message;
    public final long targetId;

    public Riposte(long id, String name, String message, long targetId) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.targetId = targetId;
    }
}
