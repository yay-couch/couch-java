package couch.util;

import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.lang.IllegalArgumentException;
import javax.xml.bind.DatatypeConverter;

import org.json.*;

public final class Util
{
    public static HashMap<String, Object> param(Map param) {
        if (param == null) {
            param = map();
        }
        return (HashMap) param;
    }

    public static HashMap<String, Object> paramList(Object... args) throws IllegalArgumentException {
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

    public static URL parseUrl(String url) throws IllegalArgumentException {
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

    public static String jsonEncode(Map data) {
        return new JSONObject(data).toString();
    }

    public static JSONObject jsonDecode(String data) {
        return new JSONObject(data) {
            @Override
            public Object get(String key) { // skip exception
                try {
                    return super.get(key);
                } catch (JSONException e) {
                    return null;
                }
            }
        };
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

    public static Object ifNull(Object a, Object b) {
        return (a != null) ? a : b;
    }

    public static HashMap<String, String> parseHeaders(String headers) {
        HashMap<String, String> ret = new HashMap<String, String>();
        String[] tmp = headers.split("\r\n");
        if (tmp.length > 0) {
            ret.put("0", tmp[0]);
            for (int i = 1; i < tmp.length; i++) {
                String[] t = tmp[i].split(":", 2);
                if (t.length == 2) {
                    ret.put(t[0].trim(), t[1].trim());
                }
            }
        }
        return ret;
    }

    public static HashMap<String, Object> map() {
        return new HashMap<String, Object>();
    }

    public static void dump(Object arg) {
        System.out.println(arg);
    }
}
