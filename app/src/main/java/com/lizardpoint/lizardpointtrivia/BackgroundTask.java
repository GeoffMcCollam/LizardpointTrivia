package com.lizardpoint.lizardpointtrivia;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import android.util.Log;

/**
 * Created by Geoff on 2018-03-29.
 */

public class BackgroundTask extends AsyncTask<String,Void,String> {
    AlertDialog alertDialog;
    Context ctx;

    //where to send the result back to, set in parent activity
    public AsyncResponse delegate = null;

    BackgroundTask(Context ctx)
    {
        this.ctx =ctx;
    }
    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Searching for quiz...");
    }

    //get result logic
    //take parameters, method and quiz id
    //method is used to devide what this function will do, currently only getQuiz
    //quiz id or date is which quiz we're trying to find from the server
    @Override
    protected String doInBackground(String... params) {
        //getQuiz.php when receiving a quiz id through POST sends back all of the questions and answers for that quiz
        String getQuiz_url = "https://lizardpoint.com/shared/php/getQuiz.php";
        String method = params[0];
        //we're getting a quiz
        if (method.equals("getQuiz")) {
            String date = params[1]; //probably should be called quiz id, but it's a date as well so whatever
            try {
                //Connect to lizardpoint
                URL url = new URL(getQuiz_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                //send quiz id / date as POST variable to lizardpoint
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                //Original: String data = URLEncoder.encode("quizdate","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
                String data = URLEncoder.encode("quizdate","UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                //get back database response from lizardpoint
                InputStream IS = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(IS,"iso-8859-1"));
                String response = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    response += line;
                    Log.v("RESPONSE: ", response);
                }
                bufferedReader.close();
                IS.close();
                httpURLConnection.disconnect();
                return response;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    //when doInBackground finishes, this function is called and given the return result
    @Override
    protected void onPostExecute(String result) {
        if (result == null){
            alertDialog.setMessage("There may be problems with your internet.");
            alertDialog.show();
        }
        // 0 indicates no errors
        else if(result.charAt(0) == '0')
        {
            //parse data from response
            String[] questions = result.split(";");
            Quiz quiz = new Quiz("test title");

            for (int i=1; i<11; i++){
                Question q = new Question(questions[i]);
                quiz.LoadQuestion(q);
                //Log.v("BT", "Current question: " + quiz.getQuestion(i).getQuestion());
                //Log.v("BT", "Current answer: " + quiz.getQuestion(i).getAnswerList()[0]);
            }
            //send quiz object back to activity
            delegate.processFinish(quiz);
        }
        else
        {
            alertDialog.setMessage(result);
            alertDialog.show();
        }
    }
}
