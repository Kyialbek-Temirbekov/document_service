package com.example.document.util;

import org.apache.tika.Tika;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class FileUtil {
    public static String getBase64File(byte[] file) {
        if(file == null) {
            return null;
        }
        String base64Image = Base64.getEncoder().encodeToString(file);
        return "data:" + getMimeType(file) + ";base64," + base64Image;
    }

    public static String getMimeType(byte[] file) {
        return new Tika().detect(file);
    }
    public static String getMimeType(InputStream stream) {
        try {
            return new Tika().detect(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
