package couch;

import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.net.URLEncoder;

public class Query
{
    private HashMap data;
    private String dataString = "";

    public Query() {
    }
    public Query(HashMap data) {
        this.data = data;
    }

    public Query set(String key, Object value) {
        this.data.put(key.toLowerCase(), value);
        return this;
    }

    public Object get(String key) {
        return this.data.get(key);
    }

    public HashMap toArray() {
        return this.data;
    }

    public String toString() {
        if (this.dataString != "") {
            return this.dataString;
        }

        int i = 0;
        int[] data = new int[this.data.size()];
        Iterator iter = this.data.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry me = (Map.Entry) iter.next();
            if (me.getValue() == null) {
                continue;
            }
            String key = (String) me.getKey();
            Object value = (Object) me.getValue();
            if (value instanceof Integer) {
                value = String.valueOf(value);
            } else if (value instanceof Boolean) {
                value = String.valueOf(value).toLowerCase();
            }
            data[i++] = URLEncoder.encode(key) +"="+ URLEncoder.encode((String) value);
            System.out.println(data);
        }

        return this.dataString;
    }
}
