package couch;

import java.util.HashMap;

public class Couch
{
    public static final String NAME = "Couch";
    public static final String VERSION = "1.0.0";
    public static Boolean DEBUG = false;
    private HashMap config = new HashMap<String, Object>();

    public Couch() {}

    // public Couch(HashMap config) {}

    public Couch(HashMap config, Boolean debug) {
        this.config = config;
        this.DEBUG = debug;
    }
}
