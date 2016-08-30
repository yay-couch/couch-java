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

    public JSONObject info() throws Exception {
        return this.client.get(this.name).getBodyData().jsonObject();
    }

    public boolean create() throws Exception {
        return (201 == this.client.put(this.name).getStatusCode());
    }

    public boolean remove() throws Exception {
        return (200 == this.client.delete(this.name).getStatusCode());
    }

    public JSONObject replicate(String target, Boolean targetCreate) throws Exception {
        Map body = Util.paramList(
            "source",        this.name,
            "target",        target,
            "create_target", targetCreate
        );

        return this.client.post("/_replicate", null, body).getBodyData().jsonObject();
    }

    public JSONObject getDocument(String key) throws Exception {
        Map query = Util.paramList(
            "include_docs", true,
            "key"         , Util.quoteWrap(key)
        );

        JSONObject data = this.client.get(this.name +"/_all_docs", query).getBodyData().jsonObject();
        if (data.has("rows")) {
            JSONArray rows = data.getJSONArray("rows");
            if (rows.length() > 0) {
                return rows.getJSONObject(0);
            }
        }

        return null;
    }
}
