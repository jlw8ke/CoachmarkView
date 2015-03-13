package com.mobiquity.coachmarkview.sample;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.mobiquity.coachmarkview.CoachmarkView;

import butterknife.ButterKnife;

/**
 * TODO: Add a class header comment
 */
public class TitleBarCoachmarkView extends CoachmarkView {

    public TitleBarCoachmarkView(Context context) {
        super(context);
        View titleBar = View.inflate(context, R.layout.coachmark_title, null);
        ImageButton closeButton = ButterKnife.findById(titleBar, R.id.coachmark_view_close);

        closeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                TitleBarCoachmarkView.this.hide();
            }
        });
        setTitleView(titleBar, R.id.coachmark_view_title);
    }
}
