package com.mobiquity.coachmarkview;

import android.content.Context;
import android.util.DisplayMetrics;

public class CoachmarkUtils {

    static int dpToPx(float dp, float ppi, DisplayMetrics displayMetrics) {
        dp /= displayMetrics.density;
        int px = Math.round(dp * (ppi/ DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static float getDimension(Context context, int dimenId) {
        return context.getResources().getDimension(dimenId);
    }
}

