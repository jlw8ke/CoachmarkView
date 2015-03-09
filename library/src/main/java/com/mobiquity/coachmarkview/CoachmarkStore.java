package com.mobiquity.coachmarkview;

import android.content.Context;
import android.content.SharedPreferences;

class CoachmarkStore {
    private static final String COACHMARK_PREFS = "internal_coachmark_prefs";
    public static final int ALWAYS_SHOW = -1;

    private Context context;
    private SharedPreferences internalPrefs;


    public CoachmarkStore(Context context) {
        this.context = context;
        internalPrefs = context.getSharedPreferences(COACHMARK_PREFS, Context.MODE_PRIVATE);
    }

    void storeCoachmarkState(int coachmarkId, boolean shouldShow) {
        if(coachmarkId != ALWAYS_SHOW) {
            internalPrefs.edit().putBoolean(Integer.toString(coachmarkId), shouldShow).apply();
        }
    }

    boolean getCoachmarkState(int coachmarkId) {
        if(coachmarkId == ALWAYS_SHOW) {
            return true;
        }
        return internalPrefs.getBoolean(Integer.toString(coachmarkId), true);
    }
}
