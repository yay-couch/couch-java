package couch.util;

import java.util.HashMap;
import java.util.Arrays;
import java.lang.IllegalArgumentException;
import javax.xml.bind.DatatypeConverter;

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
