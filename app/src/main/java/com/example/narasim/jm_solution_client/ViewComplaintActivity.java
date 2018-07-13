package com.example.narasim.jm_solution_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ViewComplaintActivity extends AppCompatActivity {



    TextView textViewId, textViewUsername;

    EditText editTextId, editTextUsername, editTextHint, spinnerTextFeed;

    String GetId;

    Button buttonSubmit ;

    String DataParseUrl = "http://arunraaza.com/android/product/serviceview.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaint);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
//            finish();
//            startActivity(new Intent(this, LoginActivity.class));
//        }
//
//        textViewId = (TextView) findViewById(R.id.textViewId);
//        editTextId = (EditText)findViewById(R.id.textViewId);
//
//        User user = SharedPrefManager.getInstance(this).getUser();
//
//        textViewId.setText(String.valueOf(user.getId()));
////
//        buttonSubmit = (Button)findViewById(R.id.progress);
////
//        findViewById(R.id.progress).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                GetDataFromEditText();
//                SendDataToServer(GetId);
//            }
//        });
////
//        //if user presses on not registered
//        findViewById(R.id.progress).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //open register screen
//                finish();
//                startActivity(new Intent(getApplicationContext(), ProgressComplaintActivity.class));
//            }
//        });
////
//    }
////
//    public void GetDataFromEditText(){
////
//        GetId = (editTextId).getText().toString();
////
//    }
//
//
//    //sync============================================
////
//    public void SendDataToServer(final String id){
//
//
//        class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
//            @Override
//            protected String doInBackground(String... params) {
//
//                String QuickId = id;
//
//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//
//                nameValuePairs.add(new BasicNameValuePair("id", QuickId));
//
//                try {
//                    HttpClient httpClient = new DefaultHttpClient();
//
//                    HttpPost httpPost = new HttpPost(DataParseUrl);
//
//                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//
//                    HttpResponse response = httpClient.execute(httpPost);
//
//                    HttpEntity entity = response.getEntity();
//
//
//                } catch (ClientProtocolException e) {
//
//                } catch (IOException e) {
//
//                }
//                return "Data Submit Successfully";
//            }
//
//            @Override
//            protected void onPostExecute(String result) {
//                super.onPostExecute(result);
//
//
//            }
//        }
//        SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
//        sendPostReqAsyncTask.execute(id);
    }
//===============================================================================

//    completed complaints
    public void ViewCompleted(View view1) {

        if(view1.getId() == R.id.completed)
        {
            Intent i = new Intent(ViewComplaintActivity.this, ViewAllProduct.class);
            startActivity(i);
        }

    }


////progressing Complaints

    public void ViewProgress(View view2) {

        if(view2.getId() == R.id.progress)
        {
            Intent i = new Intent(ViewComplaintActivity.this, ViewAllService.class);
            startActivity(i);
        }

    }


//Cancelled Complaints
//    public void ViewCancelled(View view3) {
//
//        if(view3.getId() == R.id.cancelled)
//        {
//            Intent i = new Intent(ViewComplaintActivity.this, CancelledComplaintActivity.class);
//            startActivity(i);
//        }
//
//    }

//    public void ViewDetail(View view) {
//        Intent i = new Intent(ViewComplaintActivity.this, FeedBack.class);
//        startActivity(i);
//    }


}
