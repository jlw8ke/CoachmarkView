package com.mobiquity.coachmarkview.sample;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.mobiquity.coachmarkview.CoachmarkView;
import com.mobiquity.coachmarkview.CardCoachmark;
import com.mobiquity.coachmarkview.PathGenerator;
import com.mobiquity.coachmarkview.target.ViewTarget;

import java.util.ArrayList;
import java.util.Arrays;

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
        CardCoachmark coachmark1 = new CardCoachmark(this, viewTarget1, PathGenerator.SegmentPath.LEFT);
        coachmark1.setTitle("Test");
        coachmark1.setContent("This is a test card coachmark");
        coachmark1.setPosition(0, 200);
        coachmark1.centerHorizontal();

        View titleBar = LayoutInflater.from(this).inflate(R.layout.coachmark_title, null, false);
        ImageButton closeButton = ButterKnife.findById(titleBar, R.id.coachmark_view_close);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coachmarkView.hide();
            }
        });

        coachmarkView = new CoachmarkView.Builder(this)
                .addCoachmark(coachmark1)
                .setTitleView(titleBar, R.id.coachmark_view_title)
                .setValidAppVersions(Arrays.asList("1.*"))
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

}
