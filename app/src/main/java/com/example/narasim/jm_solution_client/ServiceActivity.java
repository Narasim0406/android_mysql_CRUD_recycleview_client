package com.example.narasim.jm_solution_client;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ServiceActivity extends AppCompatActivity {

    DatePickerDialog picker;

    TextView textViewId, textViewUsername;

    Spinner spinner;

    EditText editTextId, editTextUsername, editTextService, editTextBook_date, editTextCon_name, editTextMobile;

    String GetId, GetUsername, GetService, GetBook_date, GetCon_name, GetMobile;

    Button buttonSubmit ;

//    String DataParseUrl = "http://arunraaza.com/android/product/service.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, ServiceActivity.class));
        }


        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);

        editTextId = (EditText)findViewById(R.id.textViewId);
        editTextUsername = (EditText)findViewById(R.id.textViewUsername);

        spinner = (Spinner) findViewById(R.id.editText1);

//date
        editTextBook_date = (EditText)findViewById(R.id.editText2);
        editTextBook_date.setInputType(InputType.TYPE_NULL);
        final Calendar cldr = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        editTextBook_date.setText(sdf.format(cldr.getTime()));

        editTextBook_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                final int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                //date picker dialog
                picker = new DatePickerDialog(ServiceActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                editTextBook_date.setText(year +  "/" + "0" + (monthOfYear + 1) + "/" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(cldr.getTimeInMillis());
                picker.show();
            }
        });
 //date section closed

        editTextCon_name = (EditText)findViewById(R.id.editText3);
        editTextMobile = (EditText)findViewById(R.id.editText4);

        User user = SharedPrefManager.getInstance(this).getUser();

        textViewId.setText(String.valueOf(user.getId()));
        textViewUsername.setText(user.getUsername());

        buttonSubmit = (Button)findViewById(R.id.button1);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                GetDataFromEditText();

                SendDataToServer(GetId, GetUsername, GetService, GetBook_date, GetCon_name, GetMobile);


            }
        });

    }


    public void GetDataFromEditText(){

        GetId = (editTextId).getText().toString();
        GetUsername = editTextUsername.getText().toString();
        GetService = spinner.getSelectedItem().toString();
        GetBook_date = editTextBook_date.getText().toString();
        GetCon_name = editTextCon_name.getText().toString();
        GetMobile = editTextMobile.getText().toString();

    }


    public void SendDataToServer(final String id, final String username, final String service, final String book_date, final String con_name, final String mobile){

        if (TextUtils.isEmpty(service)) {
            editTextService.setError("Please enter a Service type");
            editTextService.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(book_date)) {
            editTextBook_date.setError("Please select Booking date ");
            editTextBook_date.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(con_name) || con_name.length() < 8) {
            editTextCon_name.setError("Please enter Contact name");
            editTextCon_name.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mobile) || mobile.length() < 10) {
            editTextMobile.setError("Please enter Mobile");
            editTextMobile.requestFocus();
            return;
        }

//        String usernames = textViewUsername.getText().toString();
        final String DataParseUrl ="http://arunraaza.com/android/product/service.php";

        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String QuickId = id;
                String QuickUsername = username;
                String QuickService = service ;
                String QuickBook_date = book_date ;
                String QuickName = con_name;
                String QuickMobile = mobile;

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                nameValuePairs.add(new BasicNameValuePair("id", QuickId));
                nameValuePairs.add(new BasicNameValuePair("username", QuickUsername));
                nameValuePairs.add(new BasicNameValuePair("service", QuickService));
                nameValuePairs.add(new BasicNameValuePair("book_date", QuickBook_date));
                nameValuePairs.add(new BasicNameValuePair("con_name", QuickName));
                nameValuePairs.add(new BasicNameValuePair("mobile", QuickMobile));

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

                Toast.makeText(ServiceActivity.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();
                editTextBook_date.getText().clear();
                editTextCon_name.getText().clear();
                editTextMobile.getText().clear();

            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(id, username, service, book_date, con_name, mobile);
    }
}
