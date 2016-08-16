package couch;

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
}
