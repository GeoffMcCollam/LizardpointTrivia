package com.lizardpoint.lizardpointtrivia;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StatsActivity extends AppCompatActivity {

    Database myDB;
    String shareScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        //int x: stores the results number that was passed through the intent
        int x = getIntent().getExtras().getInt("results");
        //string y: stores the quiz ID that was passed through the intent
        String y = getIntent().getExtras().getString("title");
        setTitle("Results for Quiz ID: " + y);
        //local strings created to store the users answers with the correct answers
        //from the intent
        String[] getUser = new String[11];
        String[] getCorrect = new String[11];
        String[] questResType = new String[11];
        String[] questionAsked = new String[11];

        //stores previously mentioned answers
        for (int i = 1; i < 11; i++) {
            getUser[i] = getIntent().getExtras().getString("userAnswer" + i);
            getCorrect[i] = getIntent().getExtras().getString("correctAnswer" + i);
            questResType[i] = getIntent().getExtras().getString("respType" + i);
            questionAsked[i] = getIntent().getExtras().getString("qQuestion" + i);
        }

        //initializes the DB
        myDB = new Database(this);
        //runs SQL command to update the high score if applicable (with WHERE clause)
        myDB.updateTop(y, x);

        //stores the select statement of the high score
        Cursor resultT = myDB.getTop(y);
        //stores select statement of previous score
        Cursor resultP = myDB.getPrev(y);

        //string buffers for displaying high and previous scores
        StringBuffer sbT = new StringBuffer();
        StringBuffer sbP = new StringBuffer();

        //appends the high score with a leading string value
        resultT.moveToFirst();
        sbT.append("HIGH SCORE: " + resultT.getString(0));

        //appends the previous score with a leading string value
        resultP.moveToFirst();
        sbP.append("PREVIOUS SCORE: " + resultP.getString(0));

        //displays text from string buffers to show the user their results
        final TextView tv = (TextView) findViewById(R.id.titleText);
        tv.setText("CURRENT SCORE: " + x + "\n" + sbT.toString() + "\n" + sbP.toString());
        shareScore = String.valueOf(x*10);
        //passes the answer information to the text view setting function
        setTextViews(getUser, getCorrect, questionAsked, questResType);

        //updates users previous attempt
        //done last so the previous score displays properly
        myDB.updatePrev(y, x);
    }

    public void setTextViews(String[] gu, String[] gc, String[] questionAsked, String[] respType) {
        //sets all textviews nested within the scrollview to the textview array
        final TextView[] textViews = new TextView[10];
        textViews[0] = findViewById(R.id.q1);
        textViews[1] = findViewById(R.id.q2);
        textViews[2] = findViewById(R.id.q3);
        textViews[3] = findViewById(R.id.q4);
        textViews[4] = findViewById(R.id.q5);
        textViews[5] = findViewById(R.id.q6);
        textViews[6] = findViewById(R.id.q7);
        textViews[7] = findViewById(R.id.q8);
        textViews[8] = findViewById(R.id.q9);
        textViews[9] = findViewById(R.id.q10);

//      These will hold the user's selected image choice
        final ImageView[] imageViewsChoice = new ImageView[10];
        imageViewsChoice[0] = findViewById(R.id.q1Choice);
        imageViewsChoice[1] = findViewById(R.id.q2Choice);
        imageViewsChoice[2] = findViewById(R.id.q3Choice);
        imageViewsChoice[3] = findViewById(R.id.q4Choice);
        imageViewsChoice[4] = findViewById(R.id.q5Choice);
        imageViewsChoice[5] = findViewById(R.id.q6Choice);
        imageViewsChoice[6] = findViewById(R.id.q7Choice);
        imageViewsChoice[7] = findViewById(R.id.q8Choice);
        imageViewsChoice[8] = findViewById(R.id.q9Choice);
        imageViewsChoice[9] = findViewById(R.id.q10Choice);

//      These will hold the correct image for a given question with images
        final ImageView[] imageViewsAnswers = new ImageView[10];
        imageViewsAnswers[0] = findViewById(R.id.q1Answer);
        imageViewsAnswers[1] = findViewById(R.id.q2Answer);
        imageViewsAnswers[2] = findViewById(R.id.q3Answer);
        imageViewsAnswers[3] = findViewById(R.id.q4Answer);
        imageViewsAnswers[4] = findViewById(R.id.q5Answer);
        imageViewsAnswers[5] = findViewById(R.id.q6Answer);
        imageViewsAnswers[6] = findViewById(R.id.q7Answer);
        imageViewsAnswers[7] = findViewById(R.id.q8Answer);
        imageViewsAnswers[8] = findViewById(R.id.q9Answer);
        imageViewsAnswers[9] = findViewById(R.id.q10Answer);

//      These hold the textviews for the "Your choice" texts
        final TextView[] textViewChoiceText = new TextView[10];
        textViewChoiceText[0] = findViewById(R.id.q1ChoiceText);
        textViewChoiceText[1] = findViewById(R.id.q2ChoiceText);
        textViewChoiceText[2] = findViewById(R.id.q3ChoiceText);
        textViewChoiceText[3] = findViewById(R.id.q4ChoiceText);
        textViewChoiceText[4] = findViewById(R.id.q5ChoiceText);
        textViewChoiceText[5] = findViewById(R.id.q6ChoiceText);
        textViewChoiceText[6] = findViewById(R.id.q7ChoiceText);
        textViewChoiceText[7] = findViewById(R.id.q8ChoiceText);
        textViewChoiceText[8] = findViewById(R.id.q9ChoiceText);
        textViewChoiceText[9] = findViewById(R.id.q10ChoiceText);

//      These hold the textviews for the "Correct Answer" texts
        final TextView[] textViewAnswerText = new TextView[10];
        textViewAnswerText[0] = findViewById(R.id.q1AnswerText);
        textViewAnswerText[1] = findViewById(R.id.q2AnswerText);
        textViewAnswerText[2] = findViewById(R.id.q3AnswerText);
        textViewAnswerText[3] = findViewById(R.id.q4AnswerText);
        textViewAnswerText[4] = findViewById(R.id.q5AnswerText);
        textViewAnswerText[5] = findViewById(R.id.q6AnswerText);
        textViewAnswerText[6] = findViewById(R.id.q7AnswerText);
        textViewAnswerText[7] = findViewById(R.id.q8AnswerText);
        textViewAnswerText[8] = findViewById(R.id.q9AnswerText);
        textViewAnswerText[9] = findViewById(R.id.q10AnswerText);


        //will display the users answers compared to the correct answer.
        //changes the corresponding textview colour to green (for correct) and red (for incorrect)
        for (int x = 0; x < 10; x++) {
//          Our question's response has images
            if (respType[x + 1].equals("image")) {
//              We set the hidden views to visible so we can work with them
                imageViewsChoice[x].setVisibility(View.VISIBLE);
                imageViewsAnswers[x].setVisibility(View.VISIBLE);
                textViewChoiceText[x].setVisibility(View.VISIBLE);
                textViewAnswerText[x].setVisibility(View.VISIBLE);

                textViews[x].setText("Question " + (x + 1) + ": " + questionAsked[x + 1]);
//              Load the pictures into our imageviews
                Picasso.get().load(getIMGURL(gu[x + 1])).into(imageViewsChoice[x]);
                Picasso.get().load(getIMGURL(gc[x + 1])).into(imageViewsAnswers[x]);
            } else {
                textViews[x].setText("Question " + (x + 1) + ": " + questionAsked[x + 1] + ".\nYour Choice: " + gu[x + 1] + "\nCorrect Answer: " + gc[x + 1]);
            }
//          Set colors to green if the user's answer is correct
            if (gu[x + 1].equals(gc[x + 1])) {
                if (respType[x + 1].equals("image")) {
                    textViewChoiceText[x].setTextColor(this.getResources().getColor(R.color.correct));
                    textViewAnswerText[x].setTextColor(this.getResources().getColor(R.color.correct));
                }
                textViews[x].setTextColor(this.getResources().getColor(R.color.correct));
            } else {
                if (respType[x + 1].equals("image")) {
                    textViewAnswerText[x].setTextColor(this.getResources().getColor(R.color.wrong));
                    textViewChoiceText[x].setTextColor(this.getResources().getColor(R.color.wrong));
                }

                textViews[x].setTextColor(this.getResources().getColor(R.color.wrong));
            }
        }
    }

    //on click for "main menu" button
    public void homeOnClick(View v) {
        finish();
        setContentView(R.layout.activity_main);
        overridePendingTransition(R.anim.anim01, R.anim.anim02);
    }


    //button to share results
    Button bt;

    //on Click for our SHARE Button
    public void shareButton(View v) {
        bt = (Button) findViewById(R.id.shareButton);
        bt.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = ("I just got a score of " + shareScore +"% on the app, Lizardpoint Trivia!" +
                        " Test your knowledge and compare :)\n\nPlay here: http://lizardpoint.com/");
                String shareSubject = "This is QUIZ Subject";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(myIntent, "Share Quiz Score Using"));
            }
        }));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

}
