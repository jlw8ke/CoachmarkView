package com.mobiquity.coachmarkview;

import com.mobiquity.coachmarkview.target.Target;

/**
 * Created by jwashington on 12/12/14.
 */
public interface CoachmarkApi {
    public void show();
    public void hide();

    public void setTitle(String title);
    public String getTitle();

    public void setContent(String content);
    public String getContent();

    public void addTarget(Target target);

}
