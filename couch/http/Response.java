package couch.http;

import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Response extends Stream
{
    private String status;
    private int statusCode = 0;
    private String statusText = "";

    public Response() {
        this.type = Stream.TYPE_RESPONSE;
        this.httpVersion = "1.0";
        this.headers = new HashMap<String, String>();
    }

    public void setStatus(String status) {
        Pattern pattern = Pattern.compile("^HTTP/(\\d+\\.\\d+)\\s+(\\d+)\\s+(.+)");
        Matcher matcher = pattern.matcher(status);
        if (!matcher.find()) {
            return;
        }

        this.status = status.trim();
        this.httpVersion = matcher.group(1);
        this.setStatusCode(Integer.valueOf(matcher.group(2)));
        this.setStatusText(matcher.group(3));
    }
    public String getStatus() {
        return this.status;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
    public String getStatusText() {
        return this.statusText;
    }

    public void setBody(Object body) {
        if (body != null) {
            this.body = (String) body;
        }
    }

    public String toString() {
        return super.toString(String.format(
            "HTTP/%s %s %s\r\n", this.httpVersion, this.statusCode, this.statusText));
    }
}
