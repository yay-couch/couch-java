package couch;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import couch.util.Util;
import static couch.util.Util.toJsonArray;
import static couch.util.Util.toJsonObject;

public class Server
{
    private final Client client;

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

    public JSONObject getDatabaseUpdates(Object query) throws Exception {
        return toJsonObject((String) this.client.get("/_db_updates", query, null).getBody());
    }

    public String getLogs(Object query) throws Exception {
        return (String) this.client.get("/_log", query, null).getBody();
    }

    public JSONObject getStats(String path) throws Exception {
        return toJsonObject((String) this.client.get("/_stats/"+ Util.ifNull(path, ""), null, null).getBody());
    }

    public String getUuid() throws Exception {
        try {
            return (String) this.getUuids(1)[0];
        } catch (Exception e) {
            return null;
        }
    }

    public String[] getUuids(int count) throws Exception {
        Map query = Util.paramList("count", count);
        String[] uuids = new String[count];
        JSONObject data = toJsonObject((String) this.client.get("/_uuids", query, null).getBody());
        if (data.has("uuids")) {
            List uuidsList = ((JSONArray) data.get("uuids")).toList();
            for (int i = 0; i < uuidsList.size(); i++) {
                uuids[i] = (String) uuidsList.get(i);
            }
        }
        return uuids;
    }
}
