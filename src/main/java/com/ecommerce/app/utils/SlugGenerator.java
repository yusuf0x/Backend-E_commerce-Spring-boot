package com.ecommerce.app.utils;

public class SlugGenerator {
    public static String generateSlug(String name) {
        if (name != null) {
            return name.toLowerCase().replace(" ", "-");
        }
        return null;
    }
}
