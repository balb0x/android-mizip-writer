package com.balbox.mizipwriter.preferences;

import android.content.Context;
import android.content.SharedPreferences;

public class GlobalPreferences {
    //Class to store the application preferences
    private static final String PREFS_FILENAME = "com.balbox.mizipwriter.prefs.global";
    private static GlobalPreferences instance;
    private SharedPreferences.Editor editor;
    private final SharedPreferences settings;
    private final Context context;

    GlobalPreferences(Context context) {
        this.context = context;
        settings = context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    public static GlobalPreferences getInstance(Context context) {
        // Use “double-locking” to avoid decrease performance
        if (instance == null) {
            synchronized (GlobalPreferences.class) {
                if (instance == null) {
                    instance = new GlobalPreferences(context);
                }
            }
        }

        return instance;
    }
}
