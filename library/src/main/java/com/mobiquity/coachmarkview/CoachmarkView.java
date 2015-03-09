package com.mobiquity.coachmarkview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobiquity.coachmarkview.target.Target;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by jwashington on 12/12/14.
 */
public class CoachmarkView extends RelativeLayout {

    List<Coachmark> coachmarks;

    //A bitmap that overlays the window for the coachmarks to draw on
    Bitmap bitmapBuffer;
    CoachmarkOverlay coahcmarkOverlay;

    View titleView;
    TextView titleTextView;
    int id;

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
        coachmarks = new ArrayList<>();
        int backgroundColor = getContext().getResources().getColor(R.color.black_trans);
        coahcmarkOverlay = new CoachmarkOverlay(backgroundColor);

        getViewTreeObserver().addOnGlobalLayoutListener(new UpdateOnGlobalLayout());
    }

    public void setTitleView(View titleView, int textViewId) {
        removeAllViews();
        this.titleView = titleView;
        titleTextView = ButterKnife.findById(titleView, textViewId);
        addView(this.titleView);
        invalidate();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        // Draw darkened overlay
        coahcmarkOverlay.erase(bitmapBuffer);

        for (Coachmark coachmark : coachmarks) {
            Target target = coachmark.getTarget();
            Point center = target.getPoint();
            Point leftCorner = new Point(center.x - target.getWidth()/2, center.y - target.getHeight()/2);

            // Punch hole through overlay to show target
            if(target != null) {
                switch (target.getTargetStyle()) {
                    case CIRCLE:
                        float radius = (float) Math.sqrt(Math.pow(target.getWidth(), 2) + Math.pow(target.getHeight(), 2));
                        coahcmarkOverlay.drawCircleCoachmark(bitmapBuffer, center.x, center.y, radius);
                        break;
                    case RECT:
                        coahcmarkOverlay.drawRectCoachmark(bitmapBuffer, leftCorner.x, leftCorner.y, target.getWidth(), target.getHeight());
                        break;
                }
            }

            coahcmarkOverlay.drawToCanvas(canvas, bitmapBuffer);

            //Add the coachmark if the target has one and it has not already been added
            if(coachmark != null && coachmark.getView().getParent() == null) {
                addView(coachmark.getView());
            }
        }
        super.dispatchDraw(canvas);
    }

    private class UpdateOnGlobalLayout implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            updateBitmap();
        }
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

    public boolean isVisible() {
        return getVisibility() == VISIBLE;
    }

    public void hide() {
        setVisibility(GONE);
    }

    public void show() {
        setVisibility(VISIBLE);
    }

    public static class Builder {
        final CoachmarkView coachmarkView;
        private final Activity activity;

        public Builder(Activity activity) {
            this.activity = activity;
            coachmarkView = new CoachmarkView(activity);
        }

        public Builder addCoachmark(Coachmark coachmark) {
            coachmarkView.coachmarks.add(coachmark);
            return this;
        }

        public Builder setTitleView(View titleView, int titleTextId) {
            coachmarkView.setTitleView(titleView, titleTextId);
            return this;
        }

        public CoachmarkView build() {
            insertCoachmarkView();
            return coachmarkView;
        }

        private void insertCoachmarkView() {
            ((ViewGroup) activity.getWindow().getDecorView()).addView(coachmarkView);
        }
    }

}
