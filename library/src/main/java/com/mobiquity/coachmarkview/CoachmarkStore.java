package com.mobiquity.coachmarkview;

import android.content.Context;
import android.content.SharedPreferences;

class CoachmarkStore {
    private static final String COACHMARK_PREFS = "internal_coachmark_prefs";

    private Context context;
    SharedPreferences internalPrefs;

    public CoachmarkStore(Context context) {
        this.context = context;
        internalPrefs = context.getSharedPreferences(COACHMARK_PREFS, Context.MODE_PRIVATE);
    }

    void storeCoachmarkState(int coachmarkId, boolean shouldShow) {
        internalPrefs.edit().putBoolean(Integer.toString(coachmarkId), shouldShow).apply();
    }

    boolean getCoachmarkState(int coachmarkId) {
        return internalPrefs.getBoolean(Integer.toString(coachmarkId), true);
    }
}
