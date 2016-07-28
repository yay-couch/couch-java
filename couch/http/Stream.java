package couch.http;

import java.util.HashMap;

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

    public Object getBody()
    {
        return this.body;
    }

    // public Object getBodyData()
    // {
    // }

    public void setHeader(String key, String value)
    {
        if (value == null) {
            this.headers.remove(key);
        } else {
            this.headers.put(key, value);
        }
    }

    public String getHeader(String key)
    {
        return ((String) this.headers.get(key));
    }

    public HashMap<String, String> getHeaderAll()
    {
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
}
