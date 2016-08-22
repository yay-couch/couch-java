import couch.Couch;
import couch.Client;
import couch.Server;
import couch.http.Request;
import couch.http.Response;
import couch.Query;
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
        dump(server.getAllDatabases().get(0));


        // dump(Arrays.asList(server.getActiveTasks().getClass().getDeclaredMethods()));
    }
}
