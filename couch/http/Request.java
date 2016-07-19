package couch.http;

public class Request extends Stream
{
    String uri;

    public Request(String uri)
    {
        this.type = Stream.TYPE_REQUEST;
        this.httpVersion = "1.0";
    }

    public Request setUri(String uri)
    {
        this.uri = uri;
        return this;
    }

    public String getUri()
    {
        return this.uri;
    }

    public String version() {
        return this.httpVersion;
    }
}
