package com.mobiquity.coachmarkview.target;

import android.graphics.Point;

public abstract class Target {

    public enum TargetStyle {
        CIRCLE, RECT
    }

    public enum TargetSize {
        NORMAL, MINI
    }

    protected TargetStyle targetStyle = TargetStyle.CIRCLE;

    public abstract Point getPoint();
    public abstract int getWidth();
    public abstract int getHeight();

    public TargetStyle getTargetStyle() {
        return targetStyle;
    }

    public void setTargetStyle(TargetStyle targetStyle) {
        this.targetStyle = targetStyle;
    }

    public class None extends Target {
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
