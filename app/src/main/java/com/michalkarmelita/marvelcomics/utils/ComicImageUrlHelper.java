package com.michalkarmelita.marvelcomics.utils;

public class ComicImageUrlHelper {

    public static String getSmallImageUrl(String baseUrl, String extension) {
        return baseUrl + "/standard_small." + extension;
    }

    public static String getHeaderImageUrl(String baseUrl) {
        return baseUrl + "landscape_amazing.jpg";
    }
}
