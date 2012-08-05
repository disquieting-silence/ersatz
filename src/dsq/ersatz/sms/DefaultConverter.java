package dsq.ersatz.sms;

public class DefaultConverter implements Converter {
    public String toLocal(String international) {
        // FIX 25/01/12 Probably should throw an error if not the correct format.
        return international.length() >= 3 ? "0" + international.substring(3) : international;
    }
}
