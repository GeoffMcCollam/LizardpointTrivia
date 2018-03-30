package com.lizardpoint.lizardpointtrivia;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Intent;

public class MainActivity extends AppCompatActivity implements AsyncResponse{

    Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void findQuiz(View view){
        String quizDate = "201514";
        String method = "getQuiz";
        //Go to BackgroundTask.java to fetch data from lizardpoint
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.delegate = this;
        backgroundTask.execute(method,quizDate);
    }

    @Override
    public void processFinish(Quiz q){
        quiz = q;
        Intent myIntent = new Intent( this, QuizActivity.class );
       // myIntent.putExtra("quiz", (Parcelable) quiz);
        this.startActivity(myIntent);
    }

}
