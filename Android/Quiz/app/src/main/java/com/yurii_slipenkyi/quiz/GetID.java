package com.yurii_slipenkyi.quiz;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class GetID {
    private static final String PREFERENCE_LAST_NOTIFY_ID = "PREFERENCE_LAST_NOTIFY_ID";

    public static int getNextNotifyId(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        int id = sharedPreferences.getInt(PREFERENCE_LAST_NOTIFY_ID, 0) + 1;
        if (id == Integer.MAX_VALUE) { id = 0; }
        sharedPreferences.edit().putInt(PREFERENCE_LAST_NOTIFY_ID, id).apply();
        return id;
    }
}
