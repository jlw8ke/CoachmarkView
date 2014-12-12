package com.mobiquity.coachmarkview.target;

import android.app.Activity;
import android.graphics.Point;
import android.view.View;

import com.mobiquity.coachmarkview.coachmark.Coachmark;

/**
 * Created by jwashington on 12/12/14.
 */
public class ViewTarget extends BaseTarget{
    private final View view;
    private Coachmark coachmark;

    public ViewTarget(View view) {
        this.view = view;
    }

    public ViewTarget(int viewId, Activity activity) {
        view = activity.findViewById(viewId);
    }

    @Override
    public Point getPoint() {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        int x = location[0] + view.getWidth() / 2;
        int y = location[1] + view.getHeight() / 2;
        return new Point(x, y);
    }

    @Override
    public int getWidth() {
        return view.getWidth();
    }

    @Override
    public int getHeight() {
        return view.getHeight();
    }
}
