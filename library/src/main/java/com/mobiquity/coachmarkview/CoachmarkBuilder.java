package com.mobiquity.coachmarkview;

import com.mobiquity.coachmarkview.target.Target;

/**
 * Created by jwashington on 12/12/14.
 */
public interface CoachmarkBuilder<T> {
    CoachmarkBuilder addTarget(Target target);
    CoachmarkBuilder setTitle(String title);
    T build();
}
