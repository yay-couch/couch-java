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

        Object[] documents = new Object[2];
        documents[0] = new Document(database);
        // documents[1] = new HashMap<String, Object>();
        // documents[1].put("foo", 1);

        Map document = new HashMap<String, Object>();
        document.put("foo",1);
        // documents[1] = document;

        // dump(database.createDocument(document));
        // dump(database.createDocumentAll(documents));

        document.put("_id", "884af610d6eb386450bce4785f0029f41");
        document.put("_rev", "5-a93c93a75a737719b221243e3629803a");

        // database.updateDocument(document);
        // database.updateDocumentAll(new Object[]{document});

        // database.deleteDocument(document);
        // database.deleteDocumentAll(new Object[]{document});

        // dump(database.getChanges(null, new String[]{"884af610d6eb386450bce4785f0029f41"}));

        // dump(database.compact(""));
        // dump(database.ensureFullCommit());
        // dump(database.viewCleanup());
        // dump(database.viewTemp("function(doc){if(doc.type=='tmp') emit(null,doc)}", null));

        // dump(database.getSecurity());
        // dump(database.setSecurity(null, null));

        // dump(database.getRevisionLimit());
        // dump(database.setRevisionLimit(1000));

    }
}
