package couch;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import couch.util.Util;

public class Document
{
    private final Database database;
    private Map data = Util.map();

    public Document(Database database) {
        this.database = database;
    }

    // public Document(Database database, Object... data) {
    //     this.database = database;
    //     this.setData(data);
    // }

    public Map getData() {
        return this.data;
    }
}
