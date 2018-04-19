package com.lizardpoint.lizardpointtrivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import android.util.Log;

public class QuizActivity extends AppCompatActivity {
    //local string arrays to store quiz content
    public String[] qQuestion = new String[11];
    public String[] qAnswerA = new String[11];
    public String[] qAnswerB = new String[11];
    public String[] qAnswerC = new String[11];
    public String[] qAnswerD = new String[11];
    public String[] qCorrect = new String[11];
    public String[] qRespType = new String[11];
    //stores the users selected answer in a string array
    public String[] uAnswer = new String[11];
    //local int values to check if 10 questions have gone by
    //and if 10 answers have been successfully pressed
    public int uAnswerNum = 1;
    public int qNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        //sets the title to the quiz ID so the user can reference it later
        String qTitle = getIntent().getExtras().getString("title");
        setTitle("Quiz ID: " + qTitle);
        //stores intent values to previously mentioned local variables
        for (int i = 1; i < 11; i++) {
            qQuestion[i] = getIntent().getExtras().getString("question" + i);
            qAnswerA[i] = getIntent().getExtras().getString("answer" + i + "a");
            qAnswerB[i] = getIntent().getExtras().getString("answer" + i + "b");
            qAnswerC[i] = getIntent().getExtras().getString("answer" + i + "c");
            qAnswerD[i] = getIntent().getExtras().getString("answer" + i + "d");
            qCorrect[i] = getIntent().getExtras().getString("correct" + i);
            qRespType[i] = getIntent().getExtras().getString("respType" + i);
        }
        //function that uses the question number to determine when to change
        //to next questions and answers
        quiz_cycle(qNumber);
    }

    public void quiz_cycle(int x) {

        //Get button instances
        final Button btA = (Button) findViewById(R.id.qAnswer1);
        final Button btB = (Button) findViewById(R.id.qAnswer2);
        final Button btC = (Button) findViewById(R.id.qAnswer3);
        final Button btD = (Button) findViewById(R.id.qAnswer4);
        //Get image button instances
        final ImageButton imgButtonA = (ImageButton) findViewById(R.id.qAnswer1IMG);
        final ImageButton imgButtonB = (ImageButton) findViewById(R.id.qAnswer2IMG);
        final ImageButton imgButtonC = (ImageButton) findViewById(R.id.qAnswer3IMG);
        final ImageButton imgButtonD = (ImageButton) findViewById(R.id.qAnswer4IMG);
        //Stores and sets text for the question to the view
        final TextView tv = (TextView) findViewById(R.id.qQuestion);
        tv.setText("Question " + x + ":\n" + qQuestion[x]);
        //If answer is an image instead of a string
        if (qRespType[x].equals("image")) {
            //Make image buttons visible if they were off
            imgButtonA.setVisibility(View.VISIBLE);
            imgButtonB.setVisibility(View.VISIBLE);
            imgButtonC.setVisibility(View.VISIBLE);
            imgButtonD.setVisibility(View.VISIBLE);
            //Load the images into our image button
            Picasso.get().load(getIMGURL(qAnswerA[x])).into(imgButtonA);
            Picasso.get().load(getIMGURL(qAnswerB[x])).into(imgButtonB);
            Picasso.get().load(getIMGURL(qAnswerC[x])).into(imgButtonC);
            Picasso.get().load(getIMGURL(qAnswerD[x])).into(imgButtonD);
            //Hide string buttons
            btA.setVisibility(View.GONE);
            btB.setVisibility(View.GONE);
            btC.setVisibility(View.GONE);
            btD.setVisibility(View.GONE);
        }

        else /*if string buttons*/ {
            //Show buttons if they were hidden
            btA.setVisibility(View.VISIBLE);
            btB.setVisibility(View.VISIBLE);
            btC.setVisibility(View.VISIBLE);
            btD.setVisibility(View.VISIBLE);
            //Hide any non hidden image buttons
            imgButtonA.setVisibility(View.GONE);
            imgButtonB.setVisibility(View.GONE);
            imgButtonC.setVisibility(View.GONE);
            imgButtonD.setVisibility(View.GONE);
        }

        //Set buttons with appropriate answers for correct answer calculations
        btA.setText("" + qAnswerA[x]);
        btB.setText("" + qAnswerB[x]);
        btC.setText("" + qAnswerC[x]);
        btD.setText("" + qAnswerD[x]);
    }

    //onClick function for both string and image button number 01
    public void buttonOnClick01(View v) {
        //gets and sets button text that will be passed to calculate correct answers
        final Button btA = (Button) findViewById(R.id.qAnswer1);
        uAnswer[uAnswerNum] = "" + btA.getText();
        //if 10 questions have been attempted, show results activity
        if (qNumber == 10 || uAnswerNum == 10) {
            finalResults();
        }
        //else keep cycling through questions until we hit 10
        else if (qNumber < 10 || uAnswerNum < 10) {
            uAnswerNum++;
            qNumber++;
            quiz_cycle(qNumber);
        }
    }

    //same as above but for string and image button number 02
    public void buttonOnClick02(View v) {
        final Button btB = (Button) findViewById(R.id.qAnswer2);
        uAnswer[uAnswerNum] = "" + btB.getText();

        if (qNumber == 10 || uAnswerNum == 10) {
            finalResults();
        } else if (qNumber < 10 || uAnswerNum < 10) {
            uAnswerNum++;
            qNumber++;
            quiz_cycle(qNumber);
        }
    }

    //same as above but for string and image button number 03
    public void buttonOnClick03(View v) {
        final Button btC = (Button) findViewById(R.id.qAnswer3);
        uAnswer[uAnswerNum] = "" + btC.getText();

        if (qNumber == 10 || uAnswerNum == 10) {
            finalResults();
        } else if (qNumber < 10 || uAnswerNum < 10) {
            uAnswerNum++;
            qNumber++;
            quiz_cycle(qNumber);
        }
    }

    //same as above but for string and image button number 04
    public void buttonOnClick04(View v) {
        final Button btD = (Button) findViewById(R.id.qAnswer4);
        uAnswer[uAnswerNum] = "" + btD.getText();

        if (qNumber == 10 || uAnswerNum == 10) {
            finalResults();
        } else if (qNumber < 10 || uAnswerNum < 10) {
            uAnswerNum++;
            qNumber++;
            quiz_cycle(qNumber);
        }
    }

    //public int that returns the calculated results that will be shown to the user
    //plus stored in the DB
    public int results() {

        int x = 0;

        for (int i = 1; i < 11; i++) {
            //Log.v("RESULTS: ", uAnswer[i].toString() + " == " + qCorrect[i].toString());
            if (uAnswer[i].toString().equals(qCorrect[i].toString())) {
                x++;
            }
        }

        return x;
    }

    public void finalResults() {

        //stores the return value of previously mentioned "results" function
        int x = results();
        //local variable that pulls quid ID for later DB and results use
        String getQuizDate = getIntent().getExtras().getString("title");

        Bundle myBundle = new Bundle();

        myBundle.putInt("results", x);
        myBundle.putString("title", getQuizDate);

        //cycles through for loop to store all answers (to display back to the user)
        for (int i = 1; i < 11; i++) {
            myBundle.putString("qQuestion" + i, qQuestion[i]);
            myBundle.putString("userAnswer" + i, uAnswer[i]);
            myBundle.putString("correctAnswer" + i, qCorrect[i]);
            myBundle.putString("respType" + i, qRespType[i]);
        }

        //new intent to pass to stats activity
        Intent myIntent = new Intent(this, StatsActivity.class);
        myIntent.putExtras(myBundle);

        finish();
        this.startActivity(myIntent);
        overridePendingTransition(R.anim.anim01, R.anim.anim02);
    }

    public String getIMGURL(String imgPath) {
        //which url to pull images from
        String imgURL = "https://lizardpoint.com";
        //Regex to get file path
        final Pattern imageDirectory = Pattern.compile("/.*\\.gif");
        //This is in-charge of finding a match
        Matcher foundMatches = imageDirectory.matcher(imgPath);
        if (foundMatches.find()) {
            imgURL += foundMatches.group(0);
        }
        return imgURL;
    }

    //anim plays
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.anim01, R.anim.anim02);
    }
}
