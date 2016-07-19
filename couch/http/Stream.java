package couch.http;

import java.util.HashMap;

class Stream
{
    public static final int TYPE_REQUEST = 1;
    public static final int TYPE_RESPONSE = 2;

    protected int type;
    protected String httpVersion;

    protected HashMap headers = null;

    public Stream()
    {
        this.headers = new HashMap<String, String>();
    }

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
        return (String) this.headers.get(key);
    }

    public HashMap getHeaderAll()
    {
        return this.headers;
    }
}
