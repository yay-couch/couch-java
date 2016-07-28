package couch;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Arrays;
import java.net.URLEncoder;

import couch.util.Util;

public class Query
{
    private HashMap<String, Object> data;
    private String dataString = "";

    public Query() {
    }
    public Query(HashMap<String, Object> data) {
        this.data = data;
    }

    public Query set(String key, Object value) {
        this.data.put(key.toLowerCase(), value);
        return this;
    }

    public Object get(String key) {
        return this.data.get(key);
    }

    public HashMap<String, Object> toArray() {
        return this.data;
    }

    public String toString() {
        if (this.data == null) {
            return "";
        }

        if (this.dataString != "") {
            return this.dataString;
        }

        int i = 0;
        int size = this.data.size();
        if (size == 0) {
            return "";
        }

        for (Map.Entry me : this.data.entrySet()) {
            String key = (String) me.getKey();
            Object value = (Object) me.getValue();
            if (value == null) {
                continue;
            }
            if (value instanceof Integer) {
                value = String.valueOf(value);
            } else if (value instanceof Boolean) {
                value = String.valueOf(value).toLowerCase();
            }
            this.dataString += URLEncoder.encode(key) +"="+ URLEncoder.encode((String) value) + "&";
        }

        int len = this.dataString.length();
        if (len != 0) {
            this.dataString = this.dataString.substring(0, len - 1);
        }

        this.dataString = this.dataString.replace("%5B", "[").replace("%5D", "]");

        return this.dataString;
    }
}
