package com.lizardpoint.lizardpointtrivia;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Random;

//import android.os.Parcelable;
//import android.util.Log;

public class MainActivity extends AppCompatActivity implements AsyncResponse {

    //Create database object (used to pull and push information from and to the existing db)
    Database myDB;
    //New quiz object (so we can use the variables stored and pass them through activities via bundles)
    Quiz quiz;

    //The 6 quiz IDs we will be pulling from
    public String[] titleArray = {"201503", "201511", "201512", "201514", "201531", "201534"};
    //Stores the quiz ID that will be used (from a random value)
    public String usedQuiz = "";
    //Function call that goes back to the DB to pull quiz information
    public String method = "getQuiz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initialize the DB for first time application use
        myDB = new Database(this);

        //uses the "insertData" function of the DB to insert 6 rows of info based on "titleArray"
        for (int x = 0; x < 6; x++) {
            myDB.insertData(titleArray[x], 0, 0);
        }
        //set app title
        setTitle("Lizardpoint Trivia");
    }

    public void findQuiz(View view) {

        //math.random parameters
        int low = 0;
        int high = 6;
        Random rando = new Random();
        //get number from 0 - 5 (1 - 6)
        int value = Math.round(rando.nextInt(high - low) + low);
        //Log.v("onClick", "" + value);

        //Go to BackgroundTask.java to fetch data from lizardpoint
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.delegate = this;
        //GET TAG FOR CURRENT VIEW PRESSING THE BUTTON
        usedQuiz = titleArray[value];

        //PASS TAG THROUGH FUNCTION
        backgroundTask.execute(method, usedQuiz);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.delegate = this;
        switch (id){
            case R.id.q201503:
                usedQuiz = "201503";
                backgroundTask.execute(method, "201503");
                break;
            case R.id.q201511:
                usedQuiz = "201511";
                backgroundTask.execute(method, "201511");
                break;
            case R.id.q201512:
                usedQuiz = "201512";
                backgroundTask.execute(method, "201512");
                break;
            case R.id.q201514:
                usedQuiz = "201514";
                backgroundTask.execute(method, "201514");
                break;
            case R.id.q201531:
                usedQuiz = "201531";
                backgroundTask.execute(method, "201531");
                break;
            case R.id.q201534:
                usedQuiz = "201534";
                backgroundTask.execute(method, "201534");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(Quiz q) {

        //fill previously created quiz object with information returning from the lizardpoint DB
        quiz = q;
        //create new intent to pass to next activity
        Intent myIntent = new Intent(this, QuizActivity.class);
        //myIntent.putExtra("quiz", (Parcelable) quiz);

        //create new bundle to pass to next activity
        Bundle myBundle = new Bundle();

        //passes the used quiz ID
        myBundle.putString("title", usedQuiz);

        //passes all possible questions, answers, correct answers, and response type (string or img)
        //pulled from the quiz object
        for (int i = 1; i < 11; i++) {
            myBundle.putString("question" + i, "" + q.getQuestion(i).getQuestion());
            myBundle.putString("answer" + i + "a", "" + q.getQuestion(i).getAnswerList()[0]);
            myBundle.putString("answer" + i + "b", "" + q.getQuestion(i).getAnswerList()[1]);
            myBundle.putString("answer" + i + "c", "" + q.getQuestion(i).getAnswerList()[2]);
            myBundle.putString("answer" + i + "d", "" + q.getQuestion(i).getAnswerList()[3]);
            myBundle.putString("correct" + i, "" + q.getQuestion(i).getCorrectAnswer());
            myBundle.putString("respType" + i, "" + q.getQuestion(i).getQFormat());
        }
        //pass all previously set variables to the next activity
        myIntent.putExtras(myBundle);
        this.startActivity(myIntent);
        //Log.v("MA", "end of processFinish");

        //play fade transition on activity end
        overridePendingTransition(R.anim.anim01, R.anim.anim02);
    }

    public void displayResults(View view) {

        Intent myIntent = new Intent(this, AllStatsActivity.class);
        Bundle myBundle = new Bundle();

        //local cursor array to pull information straight from the DB
        Cursor[] topCursor = new Cursor[6];
        Cursor[] prevCursor = new Cursor[6];
        //local string array to store the return values of the SQL select statement functions
        String[] top = new String[6];
        String[] prev = new String[6];

        for (int x = 0; x < 6; x++) {
            //stores all 6 titles for next activity
            myBundle.putString("title" + x, titleArray[x]);
            //stores all 6 quiz IDs for next activity
            myBundle.putInt("quizNumber" + x, x);

            topCursor[x] = myDB.getTop(titleArray[x]);
            prevCursor[x] = myDB.getPrev(titleArray[x]);
            //if select statement returns a valid result, store result in string arrays
            //push arrays to bundle
            if (topCursor[x].moveToFirst() && prevCursor[x].moveToFirst()) {
                top[x] = topCursor[x].getString(0);
                prev[x] = prevCursor[x].getString(0);

                myBundle.putString("top" + x, top[x]);
                myBundle.putString("prev" + x, prev[x]);
            }
        }
        //pass bundle variables to the intent
        myIntent.putExtras(myBundle);
        //start activity with intent variables
        this.startActivity(myIntent);

        //fade anim
        overridePendingTransition(R.anim.anim01, R.anim.anim02);
    }
}
