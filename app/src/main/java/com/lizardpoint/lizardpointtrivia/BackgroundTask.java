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
    @Override
    protected String doInBackground(String... params) {
        String getQuiz_url = "https://lizardpoint.com/shared/php/getQuiz.php";
        String method = params[0];
        if (method.equals("getQuiz")) {
            String date = params[1];
            try {
                //Connect to lizardpoint
                URL url = new URL(getQuiz_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                //send quiz id / date as POST variable to lizardpoint
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("quizdate", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
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
    @Override
    protected void onPostExecute(String result) {
        // 0 indicates no errors
        if(result.charAt(0) == '0')
        {
            //parse data from response
            String[] questions = result.split(";");
            Quiz quiz = new Quiz("test title");

            for (int i=1; i<11; i++){
                Question q = new Question(questions[i]);
                quiz.LoadQuestion(q);
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
