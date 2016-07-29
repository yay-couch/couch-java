package couch.util;

import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.lang.IllegalArgumentException;
import javax.xml.bind.DatatypeConverter;

import org.json.*;

public final class Util
{
    public static HashMap<String, Object> map(Object... args) throws IllegalArgumentException {
        int argc = args.length;
        if (argc % 2 != 0) {
            throw new IllegalArgumentException("Wrong args count!");
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 1; i < argc; i += 2) {
            map.put((String) args[i - 1], args[i]);
        }
        return map;
    }

    public static URL urlParse(String url) throws IllegalArgumentException {
        if (url == "") {
            throw new IllegalArgumentException("No valid URL given!");
        }

        if (!url.matches("^https?://(.*)")) {
            url = "http://"+ url;
        }

        try {
            return new URL(url);
        } catch (Exception e) {
            return null;
        }
    }

    public static String jsonEncode(HashMap data) {
        return new JSONObject(data).toString();
    }

    public static JSONObject jsonDecode(String data) {
        return new JSONObject(data);
    }

    public static String base64Encode(String data) {
        return DatatypeConverter.printBase64Binary(data.getBytes());
    }

    public static String base64Decode(String data) {
        return new String(DatatypeConverter.parseBase64Binary(data));
    }

    public static String implode(String x, String[] in) {
        int len = in.length;
        if (len == 0) {
            return "";
        }

        StringBuilder out = new StringBuilder();
        for (int i = 0; i < len; i++) {
            out.append(in[i]).append(x);
        }

        return out.toString().substring(0, out.length() - 1);
    }
}
