package com.mobiquity.coachmarkview.coachmark;

import android.content.Context;
import android.view.View;

/**
 * Created by jwashington on 12/12/14.
 */
public abstract class Coachmark {
    protected Context context;
    protected String title;
    protected View view;

    public Coachmark(Context context, String title) {
        this.context = context;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public View getView() {
        return view;
    }
}
