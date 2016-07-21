import couch.Couch;
import couch.Client;
import couch.http.Request;
import couch.http.Response;
import couch.util.Util;
import couch.Query;

import java.util.*;

class Test
{
    public static void main(String[] args)
    {
        // Couch couch = new Couch();
        // Client client = new Client(couch);
        // Request request = new Request(client);

        // request.setMethod("GET");
        // request.setUri("/", null);

        // System.out.println(request.getHeaderAll());

        HashMap map = Util.map(
            "foo", "bar",
            "x", true,
            "y", 1,
            "z", null
        );
        // System.out.println(map);

        Query query = new Query(map);
        System.out.println(query.toString());
    }
}
