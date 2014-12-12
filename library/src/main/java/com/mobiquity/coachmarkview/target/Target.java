package com.mobiquity.coachmarkview.target;

import android.graphics.Point;

import com.mobiquity.coachmarkview.coachmark.Coachmark;

/**
 * Created by jwashington on 12/12/14.
 */
public interface Target {


    public Point getPoint();
    public int getWidth();
    public int getHeight();
    public boolean isRect();

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

        @Override
        public boolean isRect() {
            return false;
        }
    };

}
