package com.mobiquity.coachmarkview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobiquity.coachmarkview.target.Target;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by jwashington on 12/12/14.
 */
public class CardCoachmark implements Coachmark{
    private Context context;
    private View view;
    private TextView titleText;
    private TextView contentText;

    private Target target;
    private PathGenerator.SegmentPath path;

    int strokeWidth;
    int markerRadius;

    public CardCoachmark(Context context, Target target, PathGenerator.SegmentPath path) {
        this.context = context;
        this.target = target;
        this.path = path;

        strokeWidth = context.getResources().getDimensionPixelSize(R.dimen.stroke_width_default);
        markerRadius = context.getResources().getDimensionPixelSize(R.dimen.radius_default);

        view = LayoutInflater.from(context).inflate(R.layout.card_coachmark, null, true);
        titleText = ButterKnife.findById(view, R.id.coachmark_title);
        contentText = ButterKnife.findById(view, R.id.coachmark_content);
        view.setLayoutParams(defaultBounds());

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                View segments = getSegments();
                ViewCompat.setElevation(segments, ViewCompat.getElevation(view));
                ViewGroup parent = (ViewGroup) view.getParent();
                parent.addView(segments);
                parent.bringChildToFront(segments);
                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    public String getTitle() {
        return titleText.getText().toString();
    }

    public void setTitle(String title) {
        titleText.setText(title);
    }

    public String getContent() {
        return contentText.getText().toString();
    }

    public void setContent(String content) {
        contentText.setText(content);
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public void setMarkerRadius(int markerRadius) {
        this.markerRadius = markerRadius;
    }

    @Override
    public void setPosition(int x, int y, boolean centered) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.leftMargin = x;
        if(centered) {
            params.rightMargin = x;
        }
        params.topMargin = y;
        view.setLayoutParams(params);
    }

    @Override
    public Target getTarget() {
        return target;
    }

    @Override
    public void setTarget(Target target) {
        this.target = target;
    }

    @Override
    public View getView() {
        return view;
    }

    private ViewGroup.LayoutParams defaultBounds() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.coachmark_left_default);
        params.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.coachmark_right_default);
        params.topMargin = context.getResources().getDimensionPixelSize(R.dimen.coachmark_top_default);
        return params;
    }

    public View getSegments() {
        List<Point> points = PathGenerator.generatePath(target, view, path);
        return new SegmentView(context, points, Color.WHITE);
    }

    private class SegmentView extends View {
        Paint paint;
        Paint circlePaint;
        List<Point> points;

        public SegmentView(Context context, List<Point> points, int color) {
            super(context);
            this.points = points;

            paint = new Paint();
            paint.setColor(color);
            paint.setStrokeWidth(strokeWidth);
            paint.setStyle(Paint.Style.STROKE);
            paint.setFlags(Paint.ANTI_ALIAS_FLAG);

            circlePaint = new Paint(paint);
            circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);
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
            canvas.drawPath(segmentPath, paint);
            canvas.drawCircle(endingPoint.x, endingPoint.y, markerRadius, circlePaint);
            super.dispatchDraw(canvas);
        }
    }

}
