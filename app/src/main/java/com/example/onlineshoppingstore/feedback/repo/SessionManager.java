package com.example.onlineshoppingstore.feedback.repo;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.UUID;

public class SessionManager {
    private final Application app;
    private static SessionManager instance;

    private static final String PREF_NAME = "com.example.onlineshoppingstore";
    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_ID = "id";

    private SessionManager(Application application) {
        app = application;
    }

    public static SessionManager getInstance(Application application) {
        if (instance == null) {
            instance = new SessionManager(application);
        }
        return instance;
    }

    public void createLoginSession(UUID Uid, String username) {
        // Storing login value as TRUE
        app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putBoolean(KEY_IS_LOGGED_IN, true).apply();
        app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putString(KEY_USERNAME, username).apply();
        app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().putString(KEY_ID, Uid.toString()).apply();
    }

    // Get Login State
    public boolean isLoggedIn() {
        SharedPreferences prefs = app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false) && prefs.getString(KEY_USERNAME, null) != null && prefs.getString(KEY_ID, null) != null;
    }

    public String getUsername() {
        SharedPreferences prefs = app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_USERNAME, null);
    }

    public UUID getUid() {
        SharedPreferences prefs = app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return UUID.fromString(prefs.getString(KEY_ID, null));
    }

    public void logout() {
        app.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit().clear().apply();
    }

}
