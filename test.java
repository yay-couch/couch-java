import couch.Couch;
import couch.Client;
import couch.http.Request;
import couch.http.Response;
import couch.util.Util;
import couch.Query;

import org.json.*;

import java.io.*;
import java.util.*;
import java.net.URL;

class Test
{
    public static void main(String[] args) throws Exception {
        Couch couch = new Couch();
        // Couch couch = new Couch(null, true);
        // Couch couch = new Couch(Util.map("debug", true));
        Client client = new Client(couch);
        Request request = new Request(client);

        // request.setMethod("GET");
        // request.setUri("/", null);
        // request.send("");
        // System.out.println(request.toString());

        Response response = client.request("GET /", null, null, null);
        System.out.println(response.toString());

        JSONObject jo = (JSONObject) response.getBodyData();
        System.out.println(jo);

        // request.setMethod("GET");
        // request.setUri("/", null);

        // System.out.println(request.getHeaderAll());

        // HashMap query = Util.map(
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

        // HashMap map = Util.map("name", "foo");

        // String js = Util.jsonEncode(map);
        // System.out.println(js);

        // JSONObject jo = Util.jsonDecode(js);
        // System.out.println(jo.get("name"));

        // JSONObject js = new JSONObject(map);
        // System.out.println(js.get("name") == 1);


        // URL url = Util.urlParse("localhost/foo");
        // System.out.println(url.getHost());
    }
}

// @SuppressWarnings("unchecked")
