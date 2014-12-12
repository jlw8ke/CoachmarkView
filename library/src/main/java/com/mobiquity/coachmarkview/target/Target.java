package com.mobiquity.coachmarkview.target;

import android.graphics.Point;

/**
 * Created by jwashington on 12/12/14.
 */
public interface Target {

    public Point getPoint();
    public int getWidth();
    public int getHeight();

    Target NONE = new Target() {
        @Override
        public Point getPoint() {
            return new Point(10000, 10000);
        }

        @Override
        public int getWidth() {
            return 0;
        }

        @Override
        public int getHeight() {
            return 0;
        }
    };
}
