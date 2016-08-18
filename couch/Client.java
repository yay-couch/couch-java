package couch;

import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import couch.util.Util;
import couch.http.Request;
import couch.http.Response;

public class Client
{
    private Couch couch;
    private String host = "localhost";
    private int port = 5984;
    private String username;
    private String password;
    private Request request;
    private Response response;

    public Client(Couch couch) {
        this.couch = couch;
    }

    public Couch getCouch() {
        return this.couch;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public Request getRequest() {
        return this.request;
    }

    public Response getResponse() {
        return this.response;
    }

    public void request(String uri, Object uriParams, Object body,
            HashMap<String, String> headers) throws Exception {
        Pattern pattern = Pattern.compile("^([A-Z]+)\\s+(/.*)");
        Matcher matcher = pattern.matcher(uri);
        if (!matcher.find()) {
            throw new Exception("Usage: <REQUEST METHOD> <REQUEST URI>!");
        }

        this.request = new Request(this);
        this.response = new Response();

        uri = String.format("%s:%s/%s", this.host, this.port, matcher.group(2).replaceAll("\\s+|/+", ""));

        this.request
            .setMethod(matcher.group(1))
            .setUri(uri, uriParams)
            .setBody(body);

        if (headers instanceof HashMap) {
            for (Map.Entry header : headers.entrySet()) {
                this.request.setHeader((String) header.getKey(), (String) header.getValue());
            }
        }

        String result = this.request.send();
        if (result != "") {
            String[] tmp = result.split("\\r\\n\\r\\n", 2);
            if (tmp.length == 2) {
                headers = Util.parseHeaders(tmp[0]);
                System.out.println(headers);
            }

            // System.out.println(tmp[3]);
        }

    }
}
