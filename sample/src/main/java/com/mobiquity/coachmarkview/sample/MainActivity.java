package com.mobiquity.coachmarkview.sample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.mobiquity.coachmarkview.CoachmarkView;
import com.mobiquity.coachmarkview.coachmark.CardCoachmark;
import com.mobiquity.coachmarkview.target.ViewTarget;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends ActionBarActivity {

    @InjectView(R.id.target1) TextView target1;
    @InjectView(R.id.target2) TextView target2;
    @InjectView(R.id.target3) TextView target3;
    @InjectView(R.id.target4) TextView target4;
    @InjectView(R.id.target5) TextView target5;

    CoachmarkView coachmarkView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        ViewTarget viewTarget1 = new ViewTarget(target1);
        viewTarget1.setCoachmark(new CardCoachmark(this, "Test", "This is a test coachmark", viewTarget1));
        viewTarget1.setCropped(true);
        coachmarkView = new CoachmarkView.Builder(this)
                .addTarget(viewTarget1)
                .addTarget(new ViewTarget(target2))
                .build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(coachmarkView.isVisible()) {
            coachmarkView.hide();
        } else {
            super.onBackPressed();
        }
    }
}
