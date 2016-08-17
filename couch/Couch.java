package couch;

import java.util.HashMap;

import couch.util.Util;

public class Couch
{
    public static final String NAME = "Couch";
    public static final String VERSION = "1.0.0";
    public static Boolean DEBUG = false;
    private HashMap<String, Object> config = new HashMap<String, Object>();

    public Couch() {}

    public Couch(HashMap<String, Object> config) {
        this.setConfig(config);
    }

    public Couch(HashMap<String, Object> config, Boolean debug) {
        this.setConfig(config);

        this.DEBUG = debug;
    }

    public void setConfig(HashMap<String, Object> config) {
        if (config == null) {
            return;
        }

        this.config = config;

        Boolean debug = (Boolean) this.config.get("debug");
        if (debug != null) {
            this.DEBUG = debug;
        }
    }

    public HashMap<String, Object> getConfig() {
        return this.config;
    }
}
