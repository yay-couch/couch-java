package couch;

import java.util.Map;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import couch.util.Util;

public class Database
{
    private final Client client;
    private final String name;

    public Database(Client client, String name) {
        this.client = client;
        this.name = name;
    }


}