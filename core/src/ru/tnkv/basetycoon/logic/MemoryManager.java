package ru.tnkv.basetycoon.logic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class MemoryManager {
    private static final Preferences preferences = Gdx.app.getPreferences("CookieClicker");

    public static void addCookies(long count) {
        preferences.putLong("cookie_count", getCookies() + count);
        preferences.flush();
    }

    public static void removeCookies(long count) {
        preferences.putLong("cookie_count", getCookies() - count);
        preferences.flush();
    }

    public static long getCookies() {
        return preferences.getLong("cookie_count", 0);
    }

    public static void addBooster(long count) {
        preferences.putLong("click_booster", getBooster() + count);
        preferences.flush();
    }

    public static long getBooster() {
        return preferences.getLong("click_booster", 1);
    }

    public static void addAutoClicker(long count) {
        preferences.putLong("auto_level", getAutoClicker() + count);
        preferences.flush();
    }

    public static long getAutoClicker() {
        return preferences.getLong("auto_level", 0);
    }

    public static void setClosed(long timestamp) {
        preferences.putLong("last", timestamp);
        preferences.flush();
    }

    public static long getClosed() {
        return preferences.getLong("last", System.currentTimeMillis());
    }
}
