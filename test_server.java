import couch.*;
import couch.http.*;
import couch.util.*;
import static couch.util.Util.dump;

import org.json.*;

import java.io.*;
import java.util.*;
import java.net.URL;

class TestServer
{
    public static void main(String[] args) throws Exception {
        Couch couch = new Couch(null, true);
        Client client = new Client(couch);
        Server server = new Server(client);

        // dump(server.ping());
        // dump(server.info());
        // dump(server.version());
        // dump(server.getActiveTasks());
        // dump(server.getAllDatabases().get(0));
        // dump(server.getDatabaseUpdates());
        // dump(server.getLogs());
        // dump(server.getStats());
        // dump(server.getStats("couchdb/request_time"));
        // dump(server.getUuid());
        // dump(server.getUuids(3)[0]);
        // dump(server.replicate(Util.paramList("source", "foo", "target", "foo_replicate")));
        // dump(server.restart());
        // dump(server.getConfig());
        // dump(server.getConfig("couchdb"));
        // dump(server.getConfig("couchdb", "uuid"));
        // dump(server.setConfig("couch", "foo", "The foo!"));
        // dump(server.getConfig("couch", "foo"));
        // dump(server.removeConfig("couch", "foo"));

        // Query q = new Query();
        // q.set("foo", 1);
        // q.set("bar", true);
        // q.set("baz", new String[]{"x","y","z"});
        // dump(q.toString());

        // dump(Arrays.asList(server.getActiveTasks().getClass().getDeclaredMethods()));
    }
}
