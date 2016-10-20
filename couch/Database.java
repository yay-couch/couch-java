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

    public JSONObject getDocumentAll(Map query, String[] keys) throws Exception {
        query = Util.param(query);
        if (query.get("include_docs") == null) {
            query.put("include_docs", true);
        }

        if (keys == null) {
            return this.client.get(this.name +"/_all_docs", query).getBodyData().jsonObject();
        } else {
            return this.client.post(this.name +"/_all_docs", query, Util.paramList(
                "keys", keys
            )).getBodyData().jsonObject();
        }
    }

    public JSONObject createDocument(Object document) throws Exception {
        JSONArray data = this.createDocumentAll(new Object[]{document});
        if (data.length() > 0) {
            return data.getJSONObject(0);
        }
        return null;
    }

    public JSONArray createDocumentAll(Object[] documents) throws Exception {
        Map[] docs = Util.mapList(documents.length);

        for (int i = 0; i < documents.length; i++) {
            Map doc = Util.map();
            Object document = documents[i];
            if (document instanceof Document) {
                doc = ((Document) document).getData();
            } else if (document instanceof Map) {
                doc = (Map) document;
            }

            if (docs[i] == null) {
                docs[i] = Util.map();
            }

            for (Map.Entry<String, Object> me : ((HashMap<String, Object>) doc).entrySet()) {
                String key = me.getKey();
                Object value = me.getValue();
                // this is create method, no update allowed
                if (key == "_rev" || key == "_deleted") {
                    continue;
                }
                docs[i].put(key, value);
            }
        }

        return this.client.post(this.name +"/_bulk_docs", null, Util.paramList(
            "docs", docs
        )).getBodyData().jsonArray();
    }

    public JSONObject updateDocument(Object document) throws Exception {
        JSONArray data = this.updateDocumentAll(new Object[]{document});
        if (data.length() > 0) {
            return data.getJSONObject(0);
        }
        return null;
    }

    public JSONArray updateDocumentAll(Object[] documents) throws Exception {
        Map[] docs = Util.mapList(documents.length);

        for (int i = 0; i < documents.length; i++) {
            Map doc = Util.map();
            Object document = documents[i];
            if (document instanceof Document) {
                doc = ((Document) document).getData();
            } else if (document instanceof Map) {
                doc = (Map) document;
            }

            // these are required params
            if (doc.get("_id") == null || doc.get("_rev") == null) {
                throw new Exception("Both _id & _rev fields are required!");
            }

            docs[i] = doc;
        }

        return this.client.post(this.name +"/_bulk_docs", null, Util.paramList(
            "docs", docs
        )).getBodyData().jsonArray();
    }

    public JSONObject deleteDocument(Object document) throws Exception {
        JSONArray data = this.deleteDocumentAll(new Object[]{document});
        if (data.length() > 0) {
            return data.getJSONObject(0);
        }
        return null;
    }

    public JSONArray deleteDocumentAll(Object[] documents) throws Exception {
        Map[] docs = Util.mapList(documents.length);

        for (int i = 0; i < documents.length; i++) {
            Map doc = Util.map();
            Object document = documents[i];
            if (document instanceof Document) {
                doc = ((Document) document).getData();
            } else if (document instanceof Map) {
                doc = (Map) document;
            }

            // just add "_deleted" param into document
            doc.put("_deleted", true);

            docs[i] = doc;
        }

        return this.client.post(this.name +"/_bulk_docs", null, Util.paramList(
            "docs", docs
        )).getBodyData().jsonArray();
    }

    public JSONObject getChanges(Map query, String[] docIds) throws Exception {
        query = Util.param(query);
        if (docIds != null) {
            query.put("filter", "_doc_ids");
        }

        return this.client.post(this.name +"/_changes", query, Util.paramList(
            "doc_ids", docIds
        )).getBodyData().jsonObject();
    }

    public JSONObject compact(String ddoc) throws Exception {
        return this.client.post(this.name +"/_compact/"+
            Util.ifNull(ddoc, "")).getBodyData().jsonObject();
    }

    public JSONObject ensureFullCommit() throws Exception {
        return this.client.post(this.name +"/_ensure_full_commit").getBodyData().jsonObject();
    }

    public JSONObject viewCleanup() throws Exception {
        return this.client.post(this.name +"/_view_cleanup").getBodyData().jsonObject();
    }

    public JSONObject viewTemp(String map, String reduce) throws Exception {
        return this.client.post(this.name +"/_temp_view", null, Util.paramList(
            "map",    map,
            "reduce", reduce
        )).getBodyData().jsonObject();
    }

    public JSONObject getSecurity() throws Exception {
        return this.client.get(this.name +"/_security").getBodyData().jsonObject();
    }

    public JSONObject setSecurity(Map admins, Map members) throws Exception {
        if (admins.get("names") == null || admins.get("roles") == null
            || members.get("names") == null || members.get("roles") == null) {
            throw new Exception("Specify admins and/or members with names=>roles fields!");
        }

        return this.client.put(this.name +"/_security", null, Util.paramList(
            "admins",  admins,
            "members", members
        )).getBodyData().jsonObject();
    }

    public JSONObject purge(String docId, String[] docRevs) throws Exception {
        return this.client.post(this.name +"/_purge", null, Util.paramList(
            docId, docRevs
        )).getBodyData().jsonObject();
    }

    public JSONObject getMissingRevisions(String docId, String[] docRevs) throws Exception {
        return this.client.post(this.name +"/_missing_revs", null, Util.paramList(
            docId, docRevs
        )).getBodyData().jsonObject();
    }

    public JSONObject getMissingRevisionsDiff(String docId, String[] docRevs) throws Exception {
        return this.client.post(this.name +"/_revs_diff", null, Util.paramList(
            docId, docRevs
        )).getBodyData().jsonObject();
    }

    public int getRevisionLimit() throws Exception {
        return Integer.parseInt(
            this.client.get(this.name +"/_revs_limit").getBody().toString().trim());
    }

    public JSONObject setRevisionLimit(int limit) throws Exception {
        return this.client.put(this.name +"/_revs_limit", null, limit).getBodyData().jsonObject();
    }
}
