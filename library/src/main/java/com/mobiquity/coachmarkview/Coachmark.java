package com.mobiquity.coachmarkview;

import android.content.Context;
import android.view.View;

import com.mobiquity.coachmarkview.target.Target;

/**
 * Created by jwashington on 12/12/14.
 */

public interface Coachmark {
    public Target getTarget();
    public void setTarget(Target target);
    public View getView();
    public void setPosition(float x, float y);
    public void setMaxWidth(float width);

    public class None implements Coachmark {
        Target target;
        View view;

        public None(Context context, Target target) {
            view = new View(context);
            this.target = target;
        }

        @Override
        public Target getTarget() {
            return target;
        }

        @Override
        public void setTarget(Target target) {
            this.target = target;
        }

        @Override
        public View getView() {
            return view;
        }

        @Override
        public void setPosition(float x, float y) {

        }

        @Override
        public void setMaxWidth(float width) {

        }
    }
}
