import couch.Couch;
import couch.Client;
import couch.http.Request;
import couch.http.Response;
import couch.Query;
import couch.util.*;

import org.json.*;

import java.io.*;
import java.util.*;
import java.net.URL;

class Test
{
    public static void main(String[] args) throws Exception {
        Couch couch = new Couch();
        // Couch couch = new Couch(null, true);
        // Couch couch = new Couch(Util.paramList("debug", true));
        Client client = new Client(couch);
        Request request = new Request(client);

        // request.setMethod("GET");
        // request.setUri("/", null);
        // request.send("");
        // System.out.println(request.toString());

        // Response response = client.request("GET /", null, null, null);
        Response response = client.head("/", null, null);
        System.out.println(client.getRequest().toString());
        System.out.println(client.getResponse().toString());

        System.out.println("---");

        // JSONObject jo = (JSONObject) response.getBodyData();
        // System.out.println(jo.get("couchdb"));
        // System.out.println(((JSONObject)jo.get("vendor")).get("name"));

        // request.setMethod("GET");
        // request.setUri("/", null);

        // System.out.println(request.getHeaderAll());

        // HashMap query = Util.paramList(
        //     "foo", "bar",
        //     "x", true,
        //     "y", 1,
        //     "z", "[hello][boo]"
        // );
        // // System.out.println(map);

        // // Query query = new Query(map);
        // Query query = new Query();
        // query.set("111");
        // System.out.println(query.toString());
        // request.setUri("/", query);

        // HashMap map = new HashMap();
        // map.put("name", "kerem");
        // request.setBody(map);
        // System.out.println(request.getBody());

        // HashMap map = new HashMap();
        // map.put("name", "foo");
        // String json = JSONValue.toJSONString(map);

        // HashMap map = Util.paramList("name", "foo");

        // String js = Util.toJsonString(map);
        // System.out.println(js);

        // JSONObject jo = Util.toJsonObject(js);
        // System.out.println(jo.get("name"));

        // JSONObject js = new JSONObject(map);
        // System.out.println(js.get("name") == 1);

        // Json json = Util.toJsonObject("{\"foo\":123}");
        // System.out.println(json.get("foo"));

        // URL url = Util.parseUrl("localhost/foo");
        // System.out.println(url.getHost());

        // Map p = Util.paramList("debug", 1);
        // Map p = Util.param(new HashMap<String,Object>());
        // System.out.println(p);
    }
}

// @SuppressWarnings("unchecked")
