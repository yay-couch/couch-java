package couch.http;

import java.util.Map;
import java.util.HashMap;
import java.net.URL;
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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
    private Client client;
    private String method;
    private String uri;

    public Request(Client client) {
        this.type = Stream.TYPE_REQUEST;
        this.httpVersion = "1.0";
        this.client = client;
        this.headers = new HashMap<String, String>();
        this.headers.put("Host", this.client.getHost() +":"+ this.client.getPort());
        this.headers.put("Connection", "close");
        this.headers.put("Accept", "application/json");
        this.headers.put("Content-Type", "application/json");
        this.headers.put("User-Agent",
            Couch.NAME +"/v"+ Couch.VERSION +" (+http://github.com/yay-couch/couch-java)");
        String username = this.client.getUsername(),
               password = this.client.getPassword();
        if (username != null && password != null) {
            this.headers.put("Authorization", "Basic "+ Util.base64Encode(username +":"+ password));
        }
    }

    public Client getClient() {
        return this.client;
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
            query = new Query((HashMap) uriParams).toString();
        } else if (uriParams instanceof Query) {
            query = ((Query) uriParams).toString();
        }
        if (query != "") {
            this.uri += "?"+ query;
        }
        return this;
    }

    public String send(Object body) throws IOException {
        URL url = Util.urlParse(this.uri);
        Socket sock = null;
        IOException err = null;
        String send = "", recv = "";

        send += String.format("%s %s?%s HTTP/%s\r\n",
            this.method, url.getPath(), Util.ifNull(url.getQuery(), ""), this.httpVersion);
        for (Map.Entry header : this.headers.entrySet()) {
            String key = (String) header.getKey();
            String value = (String) header.getValue();
            if (value != null) {
                send += String.format("%s: %s\r\n", key, value);
            }
        }

        send += "\r\n";
        send += Util.ifNull(this.getBody(), "");

        try {
            Socket socket = new Socket(this.client.getHost(), this.client.getPort());
            OutputStream output = socket.getOutputStream();
            output.write(send.getBytes());
            output.flush();

            int ch;
            InputStream input = socket.getInputStream();
            while ((ch = input.read()) != -1) {
                recv += String.valueOf((char) ch);
            }
            socket.close();
        } catch (IOException e) {
            err = e;
        }

        if (this.client.getCouch().DEBUG) {
            System.out.println(send);
            System.out.println(recv);
            if (err != null) {
                throw err;
            }
        }

        return recv;
    }

    public Request setBody(Object body) {
        if (body != null
            && this.method != Request.METHOD_HEAD
            && this.method != Request.METHOD_GET
        ) {
            if (this.getHeader("Content-Type") == "application/json") {
                body = Util.jsonEncode((HashMap) body);
            }
            this.body = ""+ body;
            this.setHeader("Content-Length", ""+ (""+ body).length());
        }

        return this;
    }

    @Override
    public String toString() {
        URL url = Util.urlParse(this.uri);

        return super.toString(String.format("%s %s?%s HTTP/%s\r\n",
            this.method, url.getPath(), Util.ifNull(url.getQuery(), ""), this.httpVersion));
    }
}
