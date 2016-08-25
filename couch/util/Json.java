package couch.util;

import org.json.JSONArray;
import org.json.JSONObject;

public final class Json
{
    final String data;

    public Json(String data) {
        this.data = data;
    }

    public JSONArray jsonArray() {
        return Util.jsonArray(this.data);
    }

    public JSONObject jsonObject() {
        return Util.jsonObject(this.data);
    }
}
