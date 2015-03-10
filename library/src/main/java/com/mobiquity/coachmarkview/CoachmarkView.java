package com.mobiquity.coachmarkview;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.KeyEvent;
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
public class CoachmarkView extends RelativeLayout implements View.OnKeyListener{

    List<Coachmark> coachmarks;
    private final CoachmarkStore coachmarkStore;

    //A bitmap that overlays the window for the coachmarks to draw on
    Bitmap bitmapBuffer;
    CoachmarkOverlay coachmarkOverlay;

    View titleView;
    TextView titleTextView;
    int coachmarkId = CoachmarkStore.ALWAYS_SHOW;
    List<String> appVersions;

    public CoachmarkView(Context context) {
        this(context, null);
    }

    public CoachmarkView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoachmarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        coachmarkStore = new CoachmarkStore(getContext());
        init();
    }

    private void init() {
        coachmarks = new ArrayList<>();
        appVersions = new ArrayList<>();
        int defaultColor = getContext().getResources().getColor(R.color.black_trans);
        coachmarkOverlay = new CoachmarkOverlay(defaultColor);
        setOnKeyListener(this);

        //Disable items behind the coachmark overlay
        setClickable(true);

        getViewTreeObserver().addOnGlobalLayoutListener(new UpdateOnGlobalLayout());
        setFocusableInTouchMode(true);
        requestFocus();
    }

    public void setTitleView(View titleView, int textViewId) {
        removeAllViews();
        this.titleView = titleView;
        titleTextView = ButterKnife.findById(titleView, textViewId);
        addView(this.titleView);
        invalidate();
    }

    public void setAppVersions(List<String> appVersions) {
        this.appVersions = appVersions;
    }

    public List<String> getAppVersions() {
        return appVersions;
    }

    public void setBackgroundColor(int color) {
        coachmarkOverlay.setBackgroundColor(color);
    }

    public void getBackgroundColor() {
        coachmarkOverlay.getBackgroundColor();
    }

    public void setCoachmarkId(int id) {
        this.coachmarkId = id;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        // Draw darkened overlay
        coachmarkOverlay.erase(bitmapBuffer);

        for (Coachmark coachmark : coachmarks) {
            Target target = coachmark.getTarget();
            Point center = target.getPoint();
            Point leftCorner = new Point(center.x - target.getWidth()/2, center.y - target.getHeight()/2);

            // Punch hole through overlay to show target
            if(target != null) {
                switch (target.getTargetStyle()) {
                    case CIRCLE:
                        float radius = (float) Math.sqrt(Math.pow(target.getWidth(), 2) + Math.pow(target.getHeight(), 2));
                        coachmarkOverlay.drawCircleCoachmark(bitmapBuffer, center.x, center.y, radius);
                        break;
                    case RECT:
                        coachmarkOverlay.drawRectCoachmark(bitmapBuffer, leftCorner.x, leftCorner.y, target.getWidth(), target.getHeight());
                        break;
                }
            }

            coachmarkOverlay.drawToCanvas(canvas, bitmapBuffer);

            //Add the coachmark if the target has one and it has not already been added
            if(coachmark != null && coachmark.getView().getParent() == null) {
                addView(coachmark.getView());
            }
        }
        super.dispatchDraw(canvas);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            hide();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
        coachmarkStore.storeCoachmarkState(coachmarkId, false);
    }

    public void show() {
        setVisibility(VISIBLE);
        updateBitmap();
    }

    public boolean checkVersion() {
        String currentVersion = "";
        try {
            currentVersion = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        boolean contains = false;
        for(String version : appVersions) {
            if(currentVersion.matches(version)) {
                contains = true;
                break;
            }
        }

        boolean validVersion = appVersions.isEmpty() || contains;
        if(!validVersion) {
            coachmarkStore.storeCoachmarkState(coachmarkId, false);
        }
        return validVersion;
    }

    public boolean shouldShow() {
        return !coachmarks.isEmpty() &&
                coachmarkStore.getCoachmarkState(coachmarkId) &&
                checkVersion();
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

        public Builder setCoachmarkId(int id) {
            coachmarkView.setCoachmarkId(id);
            return this;
        }

        public Builder setValidAppVersions(List<String> appVersions) {
            coachmarkView.setAppVersions(appVersions);
            return this;
        }

        public CoachmarkView build() {
            if(coachmarkView.shouldShow()) {
                try {
                    insertCoachmarkView();
                } catch (MultipleCoachmarkViewException e) {
                    e.printStackTrace();
                }            }
            return coachmarkView;
        }

        private void insertCoachmarkView() throws MultipleCoachmarkViewException {
            ViewGroup rootView = (ViewGroup) activity.getWindow().getDecorView();
            for (int i = 0; i < rootView.getChildCount(); i++) {
                if(rootView.getChildAt(i) instanceof CoachmarkView && ((CoachmarkView) rootView.getChildAt(i)).isVisible()) {
                    throw new MultipleCoachmarkViewException();
                }
            }
            rootView.addView(coachmarkView);
            coachmarkView.bringToFront();
        }

        private static class MultipleCoachmarkViewException extends Exception {
            public MultipleCoachmarkViewException() {
                super("Only one CoachmarkView may be visible at once through the builder");
            }

        }
    }

}
