package couch;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import couch.util.Util;
import couch.http.Response;

public class Server
{
    private final Client client;

    public Server(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return this.client;
    }

    public boolean ping() throws Exception {
        return (200 == this.client.head("/").getStatusCode());
    }

    public JSONObject info() throws Exception {
        return this.client.get("/").getBodyData().jsonObject();
    }

    public String version() throws Exception {
        return (String) this.info().get("version");
    }

    public JSONArray getActiveTasks() throws Exception {
        return this.client.get("/_active_tasks").getBodyData().jsonArray();
    }

    public JSONArray getAllDatabases() throws Exception {
        return this.client.get("/_all_dbs").getBodyData().jsonArray();
    }

    public JSONObject getDatabaseUpdates() throws Exception {
        return this.getDatabaseUpdates(null);
    }
    public JSONObject getDatabaseUpdates(Object query) throws Exception {
        return this.client.get("/_db_updates", query).getBodyData().jsonObject();
    }

    public String getLogs() throws Exception {
        return this.getLogs(null);
    }
    public String getLogs(Object query) throws Exception {
        return (String) this.client.get("/_log", query).getBody();
    }

    public JSONObject getStats() throws Exception {
        return this.getStats("");
    }
    public JSONObject getStats(String path) throws Exception {
        return this.client.get("/_stats/"+ path).getBodyData().jsonObject();
    }

    public String getUuid() {
        try {
            return this.getUuids(1)[0];
        } catch (Exception e) {
            return null;
        }
    }

    public String[] getUuids(int count) throws Exception {
        Map query = Util.paramList("count", count);
        String[] uuids = new String[count];
        JSONObject data = this.client.get("/_uuids", query).getBodyData().jsonObject();
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
        return this.client.post("/_replicate", null, body).getBodyData().jsonObject();
    }

    public boolean restart() throws Exception {
       return (202 == this.client.post("/_restart").getStatusCode());
    }

    public JSONObject getConfig() throws Exception {
        return this.client.get("/_config").getBodyData().jsonObject();
    }
    public JSONObject getConfig(String section) throws Exception {
        return this.client.get("/_config/"+ section).getBodyData().jsonObject();
    }
    public String getConfig(String section, String key) throws Exception {
        Response response = this.client.delete("/_config/"+ section +"/"+ key);
        String value = null;
        if (response.getStatusCode() == 200) {
            value = (String) response.getBody();
            if (value != null) {
                value = Util.quoteUnwrap(value.replaceAll("\\\\\"", "\""));
            }
        }
        return value;
    }

    public String setConfig(String section, String key, String value) throws Exception {
        if (Util.isEmpty(section) || Util.isEmpty(key)) {
            throw new Exception("Both section & key required!");
        }
        return (String) this.client.put("/_config/"+ section +"/"+ key, null, value).getBody();
    }

    public String removeConfig(String section, String key) throws Exception {
        if (Util.isEmpty(section) || Util.isEmpty(key)) {
            throw new Exception("Both section & key required!");
        }
        Response response = this.client.delete("/_config/"+ section +"/"+ key);
        return response.getStatusCode() == 200 ? (String) response.getBody() : null;
    }
}
