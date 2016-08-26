package couch;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import couch.util.Util;

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
        return this.client.get("/", null, null).getBodyData().jsonObject();
    }

    public String version() throws Exception {
        return (String) this.info().get("version");
    }

    public JSONArray getActiveTasks() throws Exception {
        return this.client.get("/_active_tasks", null, null).getBodyData().jsonArray();
    }

    public JSONArray getAllDatabases() throws Exception {
        return this.client.get("/_all_dbs", null, null).getBodyData().jsonArray();
    }

    public JSONObject getDatabaseUpdates(Object query) throws Exception {
        return this.client.get("/_db_updates", query, null).getBodyData().jsonObject();
    }

    public String getLogs() throws Exception {
        return this.getLogs(null);
    }
    public String getLogs(Object query) throws Exception {
        return (String) this.client.get("/_log", query, null).getBody();
    }

    public JSONObject getStats() throws Exception {
        return this.getStats("");
    }
    public JSONObject getStats(String path) throws Exception {
        return this.client.get("/_stats/"+ path, null, null).getBodyData().jsonObject();
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
        JSONObject data = this.client.get("/_uuids", query, null).getBodyData().jsonObject();
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
        return this.client.post("/_replicate", null, body, null).getBodyData().jsonObject();
    }

    public boolean restart() throws Exception {
       return (202 == this.client.post("/_restart", null, null, null).getStatusCode());
    }

    public JSONObject getConfig() throws Exception {
        return this.getConfig("", "");
    }
    public JSONObject getConfig(String section) throws Exception {
        return this.getConfig(section, "");
    }
    public JSONObject getConfig(String section, String key) throws Exception {
        return this.client.get("/_config/"+ section +"/"+ key, null, null).getBodyData().jsonObject();
    }
}
