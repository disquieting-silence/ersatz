package dsq.ersatz.db.settings;

public interface SettingsAdapter {
    // FIX 26/01/12 Should this use the settings object like here, or individual things like the other adapters. This
    // offers an easier way of not changing a current value.
    boolean set(Settings config);
    Settings get();
}
