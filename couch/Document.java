package couch;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import couch.util.Util;

public class Document
{
    private final Database database;

    public Document(Database database) {
        this.database = database;
    }

    // public Document(Database database, Object... data) {
    //     this.database = database;
    //     this.setData(data);
    // }
}
