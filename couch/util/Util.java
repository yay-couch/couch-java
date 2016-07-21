package couch.util;

import javax.xml.bind.DatatypeConverter;

public final class Util
{
    public static String base64Encode(String data)
    {
        return DatatypeConverter.printBase64Binary(data.getBytes());
    }

    public static String base64Decode(String data)
    {
        return new String(DatatypeConverter.parseBase64Binary(data));
    }
}
