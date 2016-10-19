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
        // dump(database.create());
        // dump(database.remove());
        // dump(database.replicate("foo_rep_1", true));

        // dump(database.getDocument("0f1eb3ba90772b64aee2f44b3c00055b"));
        // dump(database.getDocumentAll(null, new String[]{"0f1eb3ba90772b64aee2f44b3c00055b1"}));

        // dump(Util.mapList());

        Object[] documents = new Object[1];
        documents[0] = new Document(database);

        database.createDocumentAll(documents);
        // dump(database.createDocumentAll(documents));
    }
}
