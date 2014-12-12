package com.mobiquity.coachmarkview.target;

import android.graphics.Point;

import com.mobiquity.coachmarkview.coachmark.Coachmark;

/**
 * Created by jwashington on 12/12/14.
 */
public interface Target {


    public abstract Point getPoint();
    public abstract int getWidth();
    public abstract int getHeight();

    /*Target NONE = new Target() {
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
    };*/

}
