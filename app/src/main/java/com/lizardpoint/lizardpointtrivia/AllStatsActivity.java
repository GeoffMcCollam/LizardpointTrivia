package com.lizardpoint.lizardpointtrivia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class AllStatsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_results);
        setTitle("All Results");

        //local variables that store the users high and previous scores
        String[] top = new String[6];
        String[] prev = new String[6];

        for (int x = 0; x < 6; x++) {
            top[x] = getIntent().getExtras().getString("top" + x);
            prev[x] = getIntent().getExtras().getString("prev" + x);
        }

        //6 textview objects are created so that the text can be properly displayed to the user
        final TextView r03 = (TextView) findViewById(R.id.results201503);
        r03.setText("Quiz ID 201503\nHigh Score: " + top[0] + "\nPrevious Score: " + prev[0]);

        final TextView r11 = (TextView) findViewById(R.id.results201511);
        r11.setText("Quiz ID 201511\nHigh Score: " + top[1] + "\nPrevious Score: " + prev[1]);

        final TextView r12 = (TextView) findViewById(R.id.results201512);
        r12.setText("Quiz ID 201512\nHigh Score: " + top[2] + "\nPrevious Score: " + prev[2]);

        final TextView r14 = (TextView) findViewById(R.id.results201514);
        r14.setText("Quiz ID 201514\nHigh Score: " + top[3] + "\nPrevious Score: " + prev[3]);

        final TextView r31 = (TextView) findViewById(R.id.results201531);
        r31.setText("Quiz ID 201531\nHigh Score: " + top[4] + "\nPrevious Score: " + prev[4]);

        final TextView r34 = (TextView) findViewById(R.id.results201534);
        r34.setText("Quiz ID 201534\nHigh Score: " + top[5] + "\nPrevious Score: " + prev[5]);
    }

    public void backClick (View v) {
        finish();
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.anim01, R.anim.anim02);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim01, R.anim.anim02);
    }
}