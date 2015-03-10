package com.mobiquity.coachmarkview;

import android.graphics.Point;
import android.view.View;

import com.mobiquity.coachmarkview.target.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jwashington on 12/12/14.
 */
public class PathGenerator {

    public static enum SegmentPath {
        LEFT, RIGHT, TOP, BOTTOM,
        NONE
    }

    static List<Point> generatePath(Target target, View coachmark, SegmentPath path) {

        Point targetCenter = target.getPoint();
        Point targetTopCenter = new Point(targetCenter.x, targetCenter.y - target.getHeight()/2);
        Point targetLeftCenter = new Point(targetCenter.x - target.getWidth()/2, targetCenter.y);
        Point targetRightCenter = new Point(targetCenter.x + target.getWidth()/2, targetCenter.y);
        Point targetBottomCenter = new Point(targetCenter.x, targetCenter.y + target.getHeight()/2);

        Point coachmarkCenter = new Point(coachmark.getLeft()+coachmark.getWidth()/2, coachmark.getTop()+coachmark.getHeight()/2);
        Point coachmarkTopCenter = new Point(coachmarkCenter.x, coachmarkCenter.y - coachmark.getHeight()/2);
        Point coachmarkLeftCenter = new Point(coachmarkCenter.x - coachmark.getWidth()/2, coachmarkCenter.y);
        Point coachmarkRightCenter = new Point(coachmarkCenter.x + coachmark.getWidth()/2, coachmarkCenter.y);
        Point coachmarkBottomCenter = new Point(coachmarkCenter.x, coachmarkCenter.y + coachmark.getHeight()/2);

        boolean targetHigher = targetCenter.y < coachmark.getTop();
        boolean targetLower = targetCenter.y > coachmark.getBottom();
        boolean targetLeft = targetCenter.x < coachmark.getLeft();
        boolean targetRight = targetCenter.x > coachmark.getRight();

        List<Point> points = new ArrayList<>();


        switch (path) {
            case NONE:
                return null;
            case RIGHT:
                if(targetHigher) {
                    points.add(targetBottomCenter);
                    points.add(new Point(targetBottomCenter.x, coachmarkRightCenter.y));
                    points.add(coachmarkRightCenter);
                } else if(targetLower) {
                    points.add(targetTopCenter);
                    points.add(new Point(targetTopCenter.x, coachmarkRightCenter.y));
                    points.add(coachmarkRightCenter);
                } else {
                    points.add(targetLeftCenter);
                    points.add(new Point(coachmarkRightCenter.x, targetLeftCenter.y));
                }
                break;
            case LEFT:
                if(targetHigher) {
                    points.add(targetBottomCenter);
                    points.add(new Point(targetBottomCenter.x, coachmarkRightCenter.y));
                    points.add(coachmarkLeftCenter);
                } else if(targetLower) {
                    points.add(targetTopCenter);
                    points.add(new Point(targetTopCenter.x, coachmarkRightCenter.y));
                    points.add(coachmarkLeftCenter);
                } else {
                    points.add(targetRightCenter);
                    points.add(new Point(coachmarkLeftCenter.x, targetRightCenter.y));
                }
                break;
            case BOTTOM:
                if(targetLeft) {
                    points.add(targetRightCenter);
                    points.add(new Point(coachmarkBottomCenter.x, targetRightCenter.y ));
                    points.add(coachmarkBottomCenter);
                } else if(targetRight) {
                    points.add(targetLeftCenter);
                    points.add(new Point(coachmarkBottomCenter.x, targetLeftCenter.y ));
                    points.add(coachmarkBottomCenter);
                } else {
                    points.add(targetTopCenter);
                    points.add(new Point(targetTopCenter.x, coachmarkBottomCenter.y));
                }
                break;
            case TOP:
                if(targetLeft) {
                    points.add(targetRightCenter);
                    points.add(new Point(coachmarkTopCenter.x, targetRightCenter.y ));
                    points.add(coachmarkTopCenter);
                } else if(targetRight) {
                    points.add(targetLeftCenter);
                    points.add(new Point(coachmarkTopCenter.x, targetLeftCenter.y ));
                    points.add(coachmarkTopCenter);
                } else {
                    points.add(targetBottomCenter);
                    points.add(new Point(targetBottomCenter.x, coachmarkTopCenter.y));
                }
                break;
        }

        return points;
    }
}
