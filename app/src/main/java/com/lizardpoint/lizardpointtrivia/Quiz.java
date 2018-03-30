package com.lizardpoint.lizardpointtrivia;
import java.util.*;
/**
 * Created by Geoff on 2018-03-22.
 */

public class Quiz {
    private Question[] questionList;
    private int qNum;
    private String title;

    public Quiz(String id){
        title = id;
        questionList = new Question[10];
        qNum = 0;
    }

    public void LoadQuestion(Question q){
        questionList[qNum++] = q;
    }

    public Question getQuestion(int n){
        return questionList[n-1];
    }

    public String getTitle(){
        return title;
    }

}
