package com.mobiquity.coachmarkview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by jwashington on 12/12/14.
 */
public class CoachmarkView extends RelativeLayout {


    public CoachmarkView(Context context) {
        this(context, null);
    }

    public CoachmarkView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoachmarkView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        getAttributes();
        init();
    }

    private void getAttributes() {

    }

    private void init() {

    }

}
