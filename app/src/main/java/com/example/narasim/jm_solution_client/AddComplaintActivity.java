package com.example.narasim.jm_solution_client;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
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


public class AddComplaintActivity extends AppCompatActivity {

    static final int DATE_DIALOG_ID = 0;
    private int mYear, mMonth, mDay;

    DatePickerDialog picker;

    TextView textViewId, textViewUsername, tvw;

    EditText editTextId, editTextUsername, editTextProduct, editTextModel, editTextQuantity, editTextDeldate, editTextConname, editTextMobile, editTextRemark;

    String GetId, GetUsername, GetProduct, GetModel, GetQuantity, GetDeldate, GetConname, Getmobile, GetStatus, GetRemark;

    Spinner spinner, spinnerProduct, spinnerModel;

    Button buttonSubmit ;


    String DataParseUrl = "http://arunraaza.com/android/product/newproduct.php";

    @SuppressWarnings("deprecation")
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_complaint);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        textViewId = (TextView) findViewById(R.id.textViewId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);

        editTextId = (EditText)findViewById(R.id.textViewId);
        editTextUsername = (EditText)findViewById(R.id.textViewUsername);
        spinnerProduct = (Spinner)findViewById(R.id.spinnerProduct);

        spinnerModel = (Spinner)findViewById(R.id.spinnerModel);
        editTextQuantity = (EditText)findViewById(R.id.editText3);
//date
        editTextDeldate = (EditText)findViewById(R.id.editText4);
        editTextDeldate.setInputType(InputType.TYPE_NULL);
        final Calendar cldr = Calendar.getInstance();
//        int day = cldr.get(Calendar.DAY_OF_MONTH);
//        final int month = cldr.get(Calendar.MONTH);
//        int year = cldr.get(Calendar.YEAR);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        editTextDeldate.setText(sdf.format(cldr.getTime()));

        editTextDeldate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                final int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);


                //date picker dialog
                picker = new DatePickerDialog(AddComplaintActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        editTextDeldate.setText(year +  "/" + (monthOfYear + 1) + "/" + dayOfMonth);
                    }
                }, year, month, day);
                picker.getDatePicker().setMinDate(cldr.getTimeInMillis());
                picker.show();
            }
        });
//date

        editTextConname = (EditText)findViewById(R.id.editText5);
        editTextMobile = (EditText)findViewById(R.id.editText6);
        editTextRemark = (EditText)findViewById(R.id.remark);

        spinner = (Spinner)findViewById(R.id.spinner);

        User user = SharedPrefManager.getInstance(this).getUser();

        textViewId.setText(String.valueOf(user.getId()));
        textViewUsername.setText(user.getUsername());


        buttonSubmit = (Button)findViewById(R.id.button1);

        buttonSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                // TODO Auto-generated method stub

                GetDataFromEditText();

                SendDataToServer(GetId, GetUsername, GetProduct, GetModel, GetQuantity, GetDeldate, GetConname, Getmobile, GetStatus, GetRemark);
//                spinnerProduct.getText().clear();
//                spinnerModel.getText().clear();


            }
        });

    }


    public void GetDataFromEditText(){

        GetId = (editTextId).getText().toString();
        GetUsername = editTextUsername.getText().toString();
        GetProduct = spinnerProduct.getSelectedItem().toString();
        GetModel = spinnerModel.getSelectedItem().toString();
        GetQuantity = editTextQuantity.getText().toString();
        GetDeldate = editTextDeldate.getText().toString();
        GetConname = editTextConname.getText().toString();
        Getmobile = editTextMobile.getText().toString();
        GetStatus = spinner.getSelectedItem().toString();
        GetRemark = editTextRemark.getText().toString();

    }


    public void SendDataToServer(final String id, final String username, final String product, final String model, final String quantity, final String deldate, final String conname, final String mobile, final String status, final String remark){

        if (TextUtils.isEmpty(quantity)) {
            editTextQuantity.setError("Please enter 8 char username");
            editTextQuantity.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(deldate)) {
            editTextDeldate.setError("Please Select Valid Date");
            editTextDeldate.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(conname) || conname.length() < 3) {
            editTextProduct.setError("Please enter minimum 3 char Contact Name");
            editTextProduct.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mobile) || mobile.length() < 10) {
            editTextModel.setError("Please enter Valid Mobile Number");
            editTextModel.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(remark)){
            editTextRemark.setError("Please fill Remarks");
            editTextRemark.requestFocus();
            return;
        }


        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {

                String QuickId = id;
                String QuickUsername = username;
                String QuickProduct = product ;
                String QuickModel = model ;
                String QuickQuantity = quantity;
                String QuickDeldate = deldate;
                String QuickConname = conname;
                String QuickMobile = mobile;
                String QuickStatus = status;
                String QuickRemark = remark;




                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();




                nameValuePairs.add(new BasicNameValuePair("id", QuickId));
                nameValuePairs.add(new BasicNameValuePair("username", QuickUsername));
                nameValuePairs.add(new BasicNameValuePair("product", QuickProduct));
                nameValuePairs.add(new BasicNameValuePair("model", QuickModel));
                nameValuePairs.add(new BasicNameValuePair("quantity", QuickQuantity));
                nameValuePairs.add(new BasicNameValuePair("deldate", QuickDeldate));
                nameValuePairs.add(new BasicNameValuePair("conname", QuickConname));
                nameValuePairs.add(new BasicNameValuePair("mobile", QuickMobile));
                nameValuePairs.add(new BasicNameValuePair("status", QuickStatus));
                nameValuePairs.add(new BasicNameValuePair("remark",QuickRemark));

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

                Toast.makeText(AddComplaintActivity.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();
                editTextQuantity.getText().clear();
                editTextDeldate.getText().clear();
                editTextConname.getText().clear();
                editTextMobile.getText().clear();
                editTextRemark.getText().clear();
            }
        }
        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
        sendPostReqAsyncTask.execute(id, username, product, model, quantity, deldate, conname, mobile, status, remark);
    }

    private void disabledEditText(EditText editText)
    {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setOnKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }

}