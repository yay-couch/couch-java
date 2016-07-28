package couch.http;

import java.util.HashMap;

import couch.Couch;
import couch.Client;
import couch.Query;
import couch.util.Util;

public class Request extends Stream
{
    public static final String METHOD_HEAD   = "HEAD";
    public static final String METHOD_GET    = "GET";
    public static final String METHOD_POST   = "POST";
    public static final String METHOD_PUT    = "PUT";
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_COPY   = "COPY";
    public Client client;
    private String method;
    private String uri;

    public Request(Client client) {
        this.type = Stream.TYPE_REQUEST;
        this.httpVersion = "1.0";
        this.client = client;
        this.headers = new HashMap<String, String>();
        this.headers.put("Host", this.client.host +":"+ this.client.port);
        this.headers.put("Connection", "close");
        this.headers.put("Accept", "application/json");
        this.headers.put("Content-Type", "application/json");
        this.headers.put("User-Agent",
            Couch.NAME +"/v"+ Couch.VERSION +" (+http://github.com/yay-couch/couch-java)");
        if (this.client.username != null && this.client.password != null) {
            this.headers.put("Authorization", "Basic "+
                Util.base64Encode(this.client.username +":"+ this.client.password));
        }
    }

    public Request setMethod(String method) {
        this.method = method.toUpperCase();
        if (this.method != Request.METHOD_HEAD
            && this.method != Request.METHOD_GET
            && this.method != Request.METHOD_POST
        ) {
            this.setHeader("X-HTTP-Method-Override", this.method);
        }
        return this;
    }

    public Request setUri(String uri) {
        this.uri = uri;
        return this;
    }
    public Request setUri(String uri, Object uriParams) {
        this.uri = uri;
        String query = "";
        if (uriParams instanceof HashMap) {
            query = (new Query((HashMap) uriParams)).toString();
        } else if (uriParams instanceof Query) {
            query = ((Query) uriParams).toString();
        }
        if (query != "") {
            this.uri += "?"+ query;
        }
        System.out.println(this.uri);
        return this;
    }
}
