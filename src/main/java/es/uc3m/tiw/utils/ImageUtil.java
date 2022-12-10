package es.uc3m.tiw.utils;

import org.apache.tomcat.util.codec.binary.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;


public class ImageUtil {
    public String  getImgData(String byteData) {
        byte[] data = java.util.Base64.getDecoder().decode(byteData);
        return "data:image/png;base64," +
                StringUtils.newStringUtf8(Base64.encodeBase64(data, false));
    }
}
