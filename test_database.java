import couch.*;
import couch.http.*;
import couch.util.*;
import static couch.util.Util.dump;

import org.json.*;

import java.io.*;
import java.util.*;
import java.net.URL;

class TestDatabase
{
    public static void main(String[] args) throws Exception {
        Couch couch = new Couch(null, true);
        Client client = new Client(couch);
        Database database = new Database(client, "foo");

        // dump(database.ping());
        // dump(database.info());
    }
}
