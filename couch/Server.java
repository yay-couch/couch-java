package couch;

import java.util.Map;
import java.util.HashMap;

import org.json.JSONObject;

import couch.util.Util;

public class Server
{
    private Client client;

    public Server(Client client) {
        this.client = client;
    }

    public Boolean ping() throws Exception {
        return (200 == this.client.head("/", null, null).getStatusCode());
    }

    public JSONObject info() throws Exception {
        return (JSONObject) this.client.get("/", null, null).getBodyData();
    }

    public String version() throws Exception {
        return (String) this.info().get("version");
    }

    public JSONObject[] getActiveTasks() throws Exception {
        return (JSONObject[]) this.client.get("/_active_tasks", null, null).getBodyData();
    }
}
