package com.mobiquity.coachmarkview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.DisplayMetrics;
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
    protected Context context;
    protected View view;
    protected TextView titleText;
    protected TextView contentText;

    protected Target target;
    protected PathGenerator.SegmentPath path;

    protected int strokeWidth;
    protected int markerRadius;

    private DisplayMetrics displayMetrics;

    public CardCoachmark(Context context, Target target, PathGenerator.SegmentPath path) {
        this.context = context;
        this.target = target;
        this.path = path;
        displayMetrics = context.getResources().getDisplayMetrics();

        strokeWidth = context.getResources().getDimensionPixelSize(R.dimen.stroke_width_default);
        markerRadius = context.getResources().getDimensionPixelSize(R.dimen.radius_default);

        view = View.inflate(context, getLayout(), null);
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }
        });
    }

    public int getLayout() {
        return R.layout.card_coachmark;
    }

    private ViewGroup.LayoutParams defaultBounds() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return params;
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

    public void centerHorizontal() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        view.setLayoutParams(params);
    }

    public void centerVertical() {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)view.getLayoutParams();
        params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        view.setLayoutParams(params);
    }

    @Override
    public void setPosition(float x, float y) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.leftMargin = CoachmarkUtils.dpToPx(x, displayMetrics.xdpi, displayMetrics);
        params.topMargin = CoachmarkUtils.dpToPx(y, displayMetrics.ydpi, displayMetrics);
        view.setLayoutParams(params);
    }

    @Override
    public void setMaxWidth(float width) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
        params.width = CoachmarkUtils.dpToPx(width, displayMetrics.xdpi, displayMetrics);
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
