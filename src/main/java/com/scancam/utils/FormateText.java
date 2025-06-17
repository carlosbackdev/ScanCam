package com.scancam.utils;

import org.apache.commons.text.WordUtils;

public abstract class FormateText {
    public static String extractCity(String url) {
        int lastSlashIndex = url.lastIndexOf("/");
        int secondLastSlashIndex = url.lastIndexOf("/", lastSlashIndex - 1);
        String city = url.substring(secondLastSlashIndex + 1, lastSlashIndex);
        return WordUtils.capitalize(city);
    }

    public static String extractLocation(String url) {
        int indexLast = url.lastIndexOf("/");
        String location = url.substring(indexLast + 1, url.lastIndexOf(".html"));
        location = location.replace("-", " ");
        return WordUtils.capitalize(location);
    }


}
