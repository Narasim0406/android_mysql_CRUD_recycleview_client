package com.example.narasim.jm_solution_client;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FeedBack extends AppCompatActivity {

    Spinner spinner;

    TextView textViewId, textViewUsername;

    EditText editTextId, editTextUsername, editTextHint, spinnerTextFeed;

    String GetId, GetUsername, GetHint, GetFeed;

    Button buttonSubmit ;

    String DataParseUrl = "http://arunraaza.com/android/product/feedback.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, FeedBack.class));
        }


        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);


        editTextId = (EditText) findViewById(R.id.textViewId);
        editTextUsername = (EditText)findViewById(R.id.textViewUsername);
        spinner = (Spinner)findViewById(R.id.spinner);
        editTextHint = (EditText)findViewById(R.id.editText1);

        User user = SharedPrefManager.getInstance(this).getUser();

        textViewUsername.setText(user.getUsername());
        textViewId.setText(String.valueOf(user.getUsername()));

        buttonSubmit = (Button)findViewById(R.id.button1);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                // TODO Auto-generated method stub

                GetDataFromEditText();

                SendDataToServer(GetId, GetUsername, GetFeed, GetHint);
                editTextHint.getText().clear();

            }
        });


    }


    public void GetDataFromEditText(){

        GetId = (editTextId).getText().toString();
        GetUsername = (editTextUsername).getText().toString();
        GetFeed = (spinner).getSelectedItem().toString();
        GetHint = (editTextHint).getText().toString();

    }


    public void SendDataToServer(final String id, final String username, final String feed, final String hint){


        if (TextUtils.isEmpty(feed) || feed.length() < 8) {
            spinnerTextFeed.setError("Please Select any Feedback");
            editTextHint.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(hint)) {
            editTextHint.setError("Please enter 50 char thoughts of you...");
            editTextHint.requestFocus();
            return;
        }

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String QuickId = id;
                String QuickUsername = username;
                String QuickFeed =feed;
                String QuickHint = hint;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("id", QuickId));
                nameValuePairs.add(new BasicNameValuePair("username", QuickUsername));
                nameValuePairs.add(new BasicNameValuePair("hint", QuickHint));
                nameValuePairs.add(new BasicNameValuePair("feed", QuickFeed));


                try {
                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(DataParseUrl);

                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    HttpResponse response = httpClient.execute(httpPost);

                    HttpEntity entity = response.getEntity();


                } catch (ClientProtocolException e) {

                } catch (IOException e) {

                }
                return "Data Submit Successfully";
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                Toast.makeText(FeedBack.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(id,feed,hint);
    }

    }
