package com.mobiquity.coachmarkview.coachmark;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
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
    private boolean hasPath;

    private TextView titleText;
    private TextView contentText;

    public CardCoachmark(Context context, String title, String content, Target target, boolean hasPath) {
        super(context, title);
        this.content = content;
        this.target = target;
        this.hasPath = hasPath;

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

    public RelativeLayout.LayoutParams getLayoutParams() {
        return (RelativeLayout.LayoutParams) view.getLayoutParams();
    }

}
