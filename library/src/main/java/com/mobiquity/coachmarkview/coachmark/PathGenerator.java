package com.mobiquity.coachmarkview.coachmark;

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
        LEFT, RIGHT, UP, DOWN,
        LEFT_DOWN, LEFT_UP,
        RIGHT_DOWN, RIGHT_UP,
        UP_LEFT, UP_RIGHT,
        DOWN_LEFT, DOWN_RIGHT,
        NONE
    }

    public static List<Point> generatePath(Target target, View coachmark, SegmentPath path ) {

        Point targetCenter = target.getPoint();
        Point targetTopCenter = new Point(targetCenter.x, targetCenter.y - target.getHeight()/2);
        Point targetLeftCenter = new Point(targetCenter.x - target.getWidth()/2, targetCenter.y);
        Point targetRightCenter = new Point(targetCenter.x + target.getWidth()/2, targetCenter.y);
        Point targetBottomCenter = new Point(targetCenter.x, targetCenter.y + target.getHeight()/2);

        Point coachmarkCenter = new Point((int)coachmark.getX()+coachmark.getWidth()/2, (int)coachmark.getY()+coachmark.getHeight()/2);
        Point coachmarkTopCenter = new Point(coachmarkCenter.x, coachmarkCenter.y - coachmark.getHeight()/2);
        Point coachmarkLeftCenter = new Point(coachmarkCenter.x - coachmark.getWidth()/2, coachmarkCenter.y);
        Point coachmarkRightCenter = new Point(coachmarkCenter.x + coachmark.getWidth()/2, coachmarkCenter.y);
        Point coachmarkBottomCenter = new Point(coachmarkCenter.x, coachmarkCenter.y + coachmark.getHeight()/2);

        List<Point> points = new ArrayList<>();


        switch (path) {
            case NONE:
                return null;
            case LEFT:
                points.add(targetRightCenter);
                points.add(new Point(coachmarkLeftCenter.x, targetRightCenter.y ));
                break;
            case RIGHT:
                points.add(targetLeftCenter);
                points.add(new Point(coachmarkRightCenter.x, targetLeftCenter.y ));
                break;

        }

        return points;
    }
}
