package couch.util;

import java.net.URL;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

public final class Util
{
    public static HashMap<String, Object> param(Map param) {
        if (param == null) {
            param = map();
        }
        return (HashMap<String, Object>) param;
    }

    public static HashMap<String, Object> paramList(Object... args) throws Exception {
        int argc = args.length;
        if (argc % 2 != 0) {
            throw new Exception("Wrong args count!");
        }
        HashMap<String, Object> map = new HashMap<String, Object>();
        for (int i = 1; i < argc; i += 2) {
            map.put((String) args[i - 1], args[i]);
        }
        return map;
    }

    public static URL parseUrl(String url) throws Exception {
        if (url == "") {
            throw new Exception("No valid URL given!");
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

    public static String jsonString(Object data) {
        return JSONObject.valueToString(data);
    }

    public static JSONArray jsonArray(String data) {
        return new JSONArray(data) {
            @Override
            public Object get(int key) { // skip exception
                try {
                    return super.get(key);
                } catch (JSONException e) {
                    return null;
                }
            }
        };
    }

    public static JSONObject jsonObject(String data) {
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

    public static String implode(String delimiter, String[] in) {
        int len = in.length;
        if (len == 0) {
            return "";
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < len; i++) {
            out.append(in[i]).append(delimiter);
        }
        return out.toString().substring(0, out.length() - delimiter.length());
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
    // public static List<HashMap<String, Object>> mapList() {
    //     return new ArrayList<HashMap<String, Object>>();
    // }
    // public static HashMap<String, Object>[] mapList() {
    //     return new HashMap[1];
    // }

    public static void dump(Object arg) {
        System.out.println(arg);
    }

    public static String quote(String in) {
        return in.replaceAll("\"", "\\\\\"");
    }
    public static String quoteEncode(String in) {
        return in.replaceAll("\"", "%22");
    }
    public static String quoteWrap(String in) {
        if (!in.matches("^\"(?=[\\{\\[]).+")) {
            in = "\""+ in +"\"";
        }
        return in;
    }
    public static String quoteUnwrap(String in) {
        return in.replaceAll("^\"+|\"+$", "");
    }

    public static boolean isEmpty(final String in) {
        return (in == null || in.trim().isEmpty());
    }
}
