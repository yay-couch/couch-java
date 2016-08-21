package couch.http;

import java.util.Map;
import java.util.HashMap;

import org.json.*;

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

    public Object getBodyData()
    {
        Object bodyData = null;
        if (this.getHeader("Content-Type").equals("application/json")) {
            String body = (String) Util.ifNull(this.body, "");
            try {
                bodyData = Util.jsonDecode(body);
            } catch (Exception e) {}
        }

        return bodyData;
    }

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

    public void setError(String body)
    {
        body = (String) Util.ifNull(body, Util.ifNull(this.body, ""));
        try {
            JSONObject bodyJson = Util.jsonDecode(body);
            String error = (String) bodyJson.get("error"),
                   reason = (String) bodyJson.get("reason");
            this.error = String.format("Stream Error >> error: '%s', reason: '%s'", error, reason);
            this.errorData.put("error", error);
            this.errorData.put("reason", reason);
        } catch (Exception e) {}
    }

    public String getError()
    {
        return this.error;
    }

    public HashMap<String, String> getErrorData()
    {
        return this.errorData;
    }

    public String toString(String firstLine) {
        String ret = firstLine;
        for (Map.Entry header : this.headers.entrySet()) {
            String key = (String) header.getKey();
            String value = (String) header.getValue();
            if (value != null && key != "0") {
                ret += String.format("%s: %s\r\n", key, value);
            }
        }
        ret += "\r\n";
        ret += Util.ifNull(this.getBody(), "");

        return ret;
    }
}
