import couch.http.Request;
import couch.http.Response;

import java.util.*;

class Test
{
    public static void main(String[] args)
    {
        // System.out.println("Hello World!");

        Request req = new Request("");

        // req.setUri("localhost");
        // System.out.println(req.getUri());

        req.setHeader("Foo", "bar..");

        System.out.println(req.getHeaderAll());
        System.out.println(req.getHeader("Foo"));
    }
}
