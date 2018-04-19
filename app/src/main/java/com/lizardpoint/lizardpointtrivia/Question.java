package com.lizardpoint.lizardpointtrivia;
import android.util.Log;

import java.util.concurrent.ThreadLocalRandom;
import java.util.*;
/**
 * Created by Geoff on 2018-03-22.
 */

public class Question {
    private String question; //question being asked
    private String[] option; //options to guess from
    private String correctAnswer;
    private String userAnswer;
    private String qImage; //question image, set to no if there is no image
    private String respType; //answers are usually text but can also be images
    private String support; //extra message after user guesses

    private void shuffleAnswers(){
        Random rnd = ThreadLocalRandom.current();
        for (int i = option.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            String a = option[index];
            option[index] = option[i];
            option[i] = a;
        }
    }

    public Question(String obj){
        String[] q = obj.split(":split:");
        question = q[0];
        correctAnswer = q[1];
        option = new String[]{q[1],q[2],q[3],q[4]};
        shuffleAnswers();
        qImage = q[5];
        respType = q[6];
    }

    public boolean AnswerQuestion(String response){
        userAnswer = response;
        return userAnswer == correctAnswer;
    }

    public String getQuestion(){
        return question;
    }

    public String[] getAnswerList(){
        return option;
    }

    public String getCorrectAnswer() { return correctAnswer; }

    public String getQFormat() { return respType; }
}
