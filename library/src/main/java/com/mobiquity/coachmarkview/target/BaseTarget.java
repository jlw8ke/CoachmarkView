package com.mobiquity.coachmarkview.target;

import com.mobiquity.coachmarkview.coachmark.Coachmark;

/**
 * Created by jwashington on 12/12/14.
 */
public abstract class BaseTarget implements Target{
    private Coachmark coachmark;
    private boolean isCropped;

    public Coachmark getCoachmark() {
        return coachmark;
    }

    public void setCoachmark(Coachmark coachmark) {
        this.coachmark = coachmark;
    }

    @Override
    public boolean isRect() {
        return isCropped;
    }

    public void setCropped(boolean isCropped) {
        this.isCropped = isCropped;
    }
}
