package com.example.desktopapp;

import java.util.prefs.Preferences;

public class Utils {

    private static final String PREFERENCES_NODE = "com.example.desktopapp";

    public static void add(String key, String value) {
        Preferences preferences = Preferences.userRoot().node(PREFERENCES_NODE);
        preferences.put(key, value);
    }

    public static String get(String key) {
        Preferences preferences = Preferences.userRoot().node(PREFERENCES_NODE);
        return preferences.get(key, null);
    }

    public static void rm(String key) {
        Preferences preferences = Preferences.userRoot().node(PREFERENCES_NODE);
        preferences.remove(key);
    }
}
