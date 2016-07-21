package couch;

import couch.http.Request;
import couch.http.Response;

public class Client
{
    private Couch couch;
    public String host = "localhost";
    public int port = 5984;
    public String username;
    public String password;
    private Request request;
    private Response response;

    public Client(Couch couch)
    {
        this.couch = couch;
    }
}
