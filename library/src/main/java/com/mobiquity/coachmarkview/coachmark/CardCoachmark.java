package com.mobiquity.coachmarkview.coachmark;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobiquity.coachmarkview.R;
import com.mobiquity.coachmarkview.target.Target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jwashington on 12/12/14.
 */
public class CardCoachmark extends Coachmark{

    private String content;
    private Target target;
    private boolean hasPath;

    private TextView titleText;
    private TextView contentText;

    int strokeWidth;
    int circleRadius;

    public CardCoachmark(Context context, String title, String content, Target target, boolean hasPath) {
        super(context, title);
        this.content = content;
        this.target = target;
        this.hasPath = hasPath;

        strokeWidth = context.getResources().getDimensionPixelSize(R.dimen.stroke_width_default);
        circleRadius = context.getResources().getDimensionPixelSize(R.dimen.radius_default);

        view = LayoutInflater.from(context).inflate(R.layout.card_coachmark, null, true);

        titleText = (TextView) view.findViewById(R.id.coachmark_title);
        contentText = (TextView) view.findViewById(R.id.coachmark_content);

        titleText.setText(title);
        contentText.setText(content);
        view.setLayoutParams(defaultBounds());

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                View segments = getSegments();
                ViewGroup parent = (ViewGroup) view.getParent();
                parent.addView(segments);
                parent.bringChildToFront(segments);
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
    }

    private ViewGroup.LayoutParams defaultBounds() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.coachmark_left_default);
        params.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.coachmark_right_default);
        params.topMargin = context.getResources().getDimensionPixelSize(R.dimen.coachmark_top_default);
        return params;
    }

    public RelativeLayout.LayoutParams getLayoutParams() {
        return (RelativeLayout.LayoutParams) view.getLayoutParams();
    }

    public View getSegments() {
        Point targetCenter = target.getPoint();
        Point targetTopCenter = new Point(targetCenter.x, targetCenter.y - target.getHeight()/2);
        Point targetLeftCenter = new Point(targetCenter.x - target.getWidth()/2, targetCenter.y);
        Point targetRightCenter = new Point(targetCenter.x + target.getWidth()/2, targetCenter.y);
        Point targetBottomCenter = new Point(targetCenter.x, targetCenter.y + target.getHeight()/2);

        Point cardCenter = new Point((int)view.getX()+view.getWidth()/2, (int)view.getY()+view.getHeight()/2);
        Point cardTopCenter = new Point(cardCenter.x, cardCenter.y - view.getHeight()/2);
        Point cardLeftCenter = new Point(cardCenter.x - view.getWidth()/2, cardCenter.y);
        Point cardRightCenter = new Point(cardCenter.x + view.getWidth()/2, cardCenter.y);
        Point cardBottomCenter = new Point(cardCenter.x, cardCenter.y + view.getHeight()/2);


        List<Point> points = Arrays.asList(targetRightCenter, cardTopCenter);

        return new SegmentView(context, points, Color.WHITE);
    }

    private class SegmentView extends View {
        Paint paint;
        List<Point> points;

        public SegmentView(Context context, List<Point> points, int color) {
            super(context);
            this.points = points;

            paint = new Paint();
            paint.setColor(color);
            paint.setStrokeWidth(strokeWidth);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        }

        @Override
        protected void dispatchDraw(Canvas canvas) {
            Path segmentPath = new Path();
            Point startingPoint = points.get(0);
            Point endingPoint = points.get(points.size()-1);
            segmentPath.moveTo(startingPoint.x, startingPoint.y);

            for(Point point : points.subList(1, points.size())) {
                segmentPath.lineTo(point.x, point.y);
            }
            segmentPath.addCircle(endingPoint.x, endingPoint.y, circleRadius, Path.Direction.CW);
            canvas.drawPath(segmentPath, paint);
            super.dispatchDraw(canvas);
        }
    }

}
