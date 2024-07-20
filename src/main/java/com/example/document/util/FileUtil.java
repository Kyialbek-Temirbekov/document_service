package com.example.document.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Base64;

public class FileUtil {
    public static String getBase64File(byte[] image) {
        if(image == null) {
            return null;
        }
        String mimeType;
        try {
            mimeType = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(image));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String base64Image = Base64.getEncoder().encodeToString(image);
        return "data:" + mimeType + ";base64," + base64Image;
    }
}
