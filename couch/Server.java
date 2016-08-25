package couch;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import couch.util.Util;
import static couch.util.Util.jsonArray;
import static couch.util.Util.jsonObject;

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
        return jsonObject((String) this.client.get("/", null, null).getBody());
    }

    public String version() throws Exception {
        return (String) this.info().get("version");
    }

    public JSONArray getActiveTasks() throws Exception {
        return jsonArray((String) this.client.get("/_active_tasks", null, null).getBody());
    }

    public JSONArray getAllDatabases() throws Exception {
        return jsonArray((String) this.client.get("/_all_dbs", null, null).getBody());
    }

    public JSONObject getDatabaseUpdates(Object query) throws Exception {
        return jsonObject((String) this.client.get("/_db_updates", query, null).getBody());
    }

    public String getLogs(Object query) throws Exception {
        return (String) this.client.get("/_log", query, null).getBody();
    }

    public JSONObject getStats(String path) throws Exception {
        return jsonObject((String) this.client.get("/_stats/"+ Util.ifNull(path, ""), null, null).getBody());
    }

    public String getUuid() {
        try {
            return (String) this.getUuids(1)[0];
        } catch (Exception e) {
            return null;
        }
    }

    public String[] getUuids(int count) throws Exception {
        Map query = Util.paramList("count", count);
        String[] uuids = new String[count];
        JSONObject data = jsonObject((String) this.client.get("/_uuids", query, null).getBody());
        if (data.has("uuids")) {
            List<Object> uuidsList = ((JSONArray) data.get("uuids")).toList();
            for (int i = 0; i < uuidsList.size(); i++) {
                uuids[i] = (String) uuidsList.get(i);
            }
        }
        return uuids;
    }

    public JSONObject replicate(Map body) throws Exception {
        body = Util.param(body);
        if (body.get("source") == null || body.get("target") == null) {
            throw new Exception("Both source & target required!");
        }
        return jsonObject((String) this.client.post("/_replicate", null, body, null).getBody());
    }
}
