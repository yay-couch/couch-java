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

    public Client getClient() {
        return this.client;
    }

    public String getName() {
        return this.name;
    }

    public boolean ping() throws Exception {
        return (200 == this.client.head(this.name).getStatusCode());
    }
}
