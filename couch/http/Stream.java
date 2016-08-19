package couch.http;

import java.util.Map;
import java.util.HashMap;

import couch.util.Util;

abstract class Stream
{
    public static final int TYPE_REQUEST = 1;
    public static final int TYPE_RESPONSE = 2;

    protected int type;
    protected String httpVersion;

    protected HashMap<String, String> headers;
    protected Object body;

    protected String error;
    protected HashMap<String, String> errorData = new HashMap<String, String>();

    public Object getBody() {
        return this.body;
    }

    // public Object getBodyData()
    // {
    // }

    public void setHeader(String key, Object value) {
        if (value == null) {
            this.headers.remove(key);
        } else {
            this.headers.put(key, (String) value);
        }
    }

    public String getHeader(String key) {
        return (String) this.headers.get(key);
    }

    public HashMap<String, String> getHeaderAll() {
        return this.headers;
    }

    // public void setError(String body)
    // {
    // }

    // public HashMap<String, String> getError()
    // {
    // }

    // public String getErrorData()
    // {
    // }

    public String toString(String firstLine) {
        String ret = firstLine;
        for (Map.Entry header : this.headers.entrySet()) {
            String key = (String) header.getKey();
            String value = (String) header.getValue();
            if (value != null) {
                if (key == "0") {
                    continue;
                }
                ret += String.format("%s: %s\r\n", key, value);
            }
        }
        ret += "\r\n";
        ret += Util.ifNull(this.getBody(), "");

        return ret;
    }
}
