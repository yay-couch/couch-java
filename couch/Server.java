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
}
