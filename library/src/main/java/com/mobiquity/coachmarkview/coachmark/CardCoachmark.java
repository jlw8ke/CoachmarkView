package com.mobiquity.coachmarkview.coachmark;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobiquity.coachmarkview.R;
import com.mobiquity.coachmarkview.target.Target;

/**
 * Created by jwashington on 12/12/14.
 */
public class CardCoachmark extends Coachmark{

    private String content;
    private Target target;

    private TextView titleText;
    private TextView contentText;

    public CardCoachmark(Context context, String title, String content, Target target) {
        super(context, title);
        this.content = content;
        this.target = target;

        view = LayoutInflater.from(context).inflate(R.layout.card_coachmark, null, true);

        titleText = (TextView) view.findViewById(R.id.coachmark_title);
        contentText = (TextView) view.findViewById(R.id.coachmark_content);

        titleText.setText(title);
        contentText.setText(content);
        view.setLayoutParams(defaultBounds());
    }

    private ViewGroup.LayoutParams defaultBounds() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.coachmark_left_default);
        params.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.coachmark_right_default);
        params.topMargin = context.getResources().getDimensionPixelSize(R.dimen.coachmark_top_default);
        return params;
    }

    @Override
    public void setCoachmarkLocation(int leftMargin, int rightMargin, int topMargin) {
        ((RelativeLayout.LayoutParams) view.getLayoutParams()).leftMargin = leftMargin;
        ((RelativeLayout.LayoutParams) view.getLayoutParams()).rightMargin = rightMargin;
        ((RelativeLayout.LayoutParams) view.getLayoutParams()).topMargin = topMargin;
    }


}
