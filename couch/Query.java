package couch;

import java.net.URLEncoder;
import java.util.Map;
import java.util.HashMap;

import couch.util.Util;

public class Query
{
    private HashMap<String, Object> data;
    private String dataString = "";

    public Query() {
        this.data = new HashMap<String, Object>();
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

    public Query skip(int num) {
        this.data.put("skip", num);
        return this;
    }

    public Query limit(int num) {
        this.data.put("limit", num);
        return this;
    }

    public HashMap<String, Object> toArray() {
        return this.data;
    }

    public String toString() {
        return this.toString("UTF-8");
    }

    public String toString(String charset) {
        if (this.dataString != "") {
            return this.dataString;
        }

        if (this.data.size() == 0) {
            return "";
        }

        for (Map.Entry<String, Object> me : this.data.entrySet()) {
            String key = me.getKey();
            Object value = me.getValue();
            if (value == null) {
                continue;
            }
            if (value instanceof Integer || value instanceof Boolean) {
                value = String.valueOf(value);
            } else if (value instanceof String[]) {
                value = String.format("[\"%s\"]", Util.implode("\",\"", (String[]) value));
            }

            try {
                this.dataString += URLEncoder.encode(key, charset) +"="+
                                   URLEncoder.encode((String) value, charset) + "&";
            } catch (Exception e) {}
        }

        this.dataString = this.dataString.replaceAll("&$", "")
            .replace("%5B", "[").replace("%5D", "]").replace("%2C", ",");

        return this.dataString;
    }
}
