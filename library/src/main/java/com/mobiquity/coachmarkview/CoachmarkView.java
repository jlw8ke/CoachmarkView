package com.mobiquity.coachmarkview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import com.mobiquity.coachmarkview.coachmark.Coachmark;
import com.mobiquity.coachmarkview.target.BaseTarget;
import com.mobiquity.coachmarkview.target.Target;

import java.util.ArrayList;

/**
 * Created by jwashington on 12/12/14.
 */
public class CoachmarkView extends RelativeLayout implements CoachmarkApi {

    ArrayList<Target> targets;

    //A bitmap that overlays the window for the coachmarks to draw on
    private Bitmap bitmapBuffer;

    String title;
    String content;

    CoachmarkContainer coachmarkContainer;

    public CoachmarkView(Context context) {
        this(context, null);
    }

    public CoachmarkView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoachmarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        targets = new ArrayList<Target>();
        coachmarkContainer = new CoachmarkContainer();
        int backgroundColor = getContext().getResources().getColor(R.color.black_trans);

        coachmarkContainer.setBackgroundColor(backgroundColor);
        getViewTreeObserver().addOnGlobalLayoutListener(new UpdateOnGlobalLayout());
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        //Draw background color
        coachmarkContainer.erase(bitmapBuffer);

        // Draw the showcase drawable
        for (Target target : targets) {
            Point center = target.getPoint();
            Point leftCorner = new Point(center.x - target.getWidth()/2, center.y - target.getHeight()/2);

            float radius = (float) Math.sqrt(Math.pow(target.getWidth(),2) + Math.pow(target.getHeight(),2));
            if(target.isRect()) {
                coachmarkContainer.drawCoachmark(bitmapBuffer, leftCorner.x, leftCorner.y, target.getWidth(), target.getHeight());
            }else {
                coachmarkContainer.drawCoachmark(bitmapBuffer, center.x, center.y, radius);
            }
            coachmarkContainer.drawToCanvas(canvas, bitmapBuffer);

            //Add the coachmark if the target has one and it has not already been added
            Coachmark coachmark = ((BaseTarget)target).getCoachmark();
            if(coachmark != null && coachmark.getView().getParent() == null) {
                addView(coachmark.getView());
            }
        }
        super.dispatchDraw(canvas);
    }

    private void updateBitmap() {
        if (bitmapBuffer == null || haveBoundsChanged()) {
            if(bitmapBuffer != null)
                bitmapBuffer.recycle();
            bitmapBuffer = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        }
    }

    private boolean haveBoundsChanged() {
        return getMeasuredWidth() != bitmapBuffer.getWidth() ||
                getMeasuredHeight() != bitmapBuffer.getHeight();
    }

    private class UpdateOnGlobalLayout implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            updateBitmap();
        }
    }

    private static void insertCoachmarkView(CoachmarkView coachmarkView, Activity activity) {
        ((ViewGroup) activity.getWindow().getDecorView()).addView(coachmarkView);
    }

    public static class Builder implements CoachmarkBuilder{
        final CoachmarkView coachmarkView;
        private final Activity activity;

        public Builder(Activity activity) {
            this.activity = activity;
            coachmarkView = new CoachmarkView(activity);
        }

        @Override
        public Builder addTarget(Target target) {
            coachmarkView.targets.add(target);
            return this;
        }

        @Override
        public Builder setTitle(String title) {
            coachmarkView.title = title;
            return this;
        }

        @Override
        public CoachmarkView build() {
            insertCoachmarkView(coachmarkView, activity);
            return coachmarkView;
        }
    }

    //Coachmark API
    @Override
    public void show() {
        setVisibility(VISIBLE);
    }

    @Override
    public void hide() {
        setVisibility(GONE);
    }

    @Override
    public boolean isVisible() {
        return getVisibility() == VISIBLE;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
        invalidate();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setContent(String content) {
        this.content = content;
        invalidate();
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public void addTarget(Target target) {
        targets.add(target);
        invalidate();
    }
}
