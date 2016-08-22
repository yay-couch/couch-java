package couch;

import java.util.Map;
import java.util.HashMap;
// import javax.json.JsonArray;

import org.json.JSONArray;
import org.json.JSONObject;

import couch.util.Util;
import static couch.util.Util.toJsonArray;
import static couch.util.Util.toJsonObject;

public class Server
{
    private Client client;

    public Server(Client client) {
        this.client = client;
    }

    public boolean ping() throws Exception {
        return (200 == this.client.head("/", null, null).getStatusCode());
    }

    public JSONObject info() throws Exception {
        return toJsonObject((String) this.client.get("/", null, null).getBody());
    }

    public String version() throws Exception {
        return (String) this.info().get("version");
    }

    public JSONArray getActiveTasks() throws Exception {
        return toJsonArray((String) this.client.get("/_active_tasks", null, null).getBody());
    }

    public JSONArray getAllDatabases() throws Exception {
        return toJsonArray((String) this.client.get("/_all_dbs", null, null).getBody());
    }
}
