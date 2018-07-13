package com.example.narasim.jm_solution_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

        TextView textViewId, textViewUsername, textViewEmail, textViewGender, textViewUsername1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //if the user is not logged in
        //starting the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        //textViewId = (TextView) findViewById(R.id.textViewId);
       // textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewGender = (TextView) findViewById(R.id.textViewGender);
        textViewUsername1 = (TextView) findViewById(R.id.textViewUsername1);

            //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        //setting the values to the textviews
 //       textViewId.setText(String.valueOf(user.getId()));
        //textViewUsername.setText(user.getUsername());
        textViewEmail.setText(user.getEmail());
        textViewGender.setText(user.getGender());
        //textViewEmail.setEnabled(false);
        textViewUsername1.setText(user.getUsername());

        //when the user presses logout button
        //calling the logout method
        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });
    }


//Add Complaint Activity
    public void AddComplaint(View view1) {

    if(view1.getId() == R.id.buttonadd)
    {
        Intent i = new Intent(ProfileActivity.this, AddComplaintActivity.class);
        startActivity(i);
    }

    }

//View Complaint Activity
    public void Service(View view) {

        if(view.getId() == R.id.buttonservice)
        {
            Intent i = new Intent(ProfileActivity.this, ServiceActivity.class);
            startActivity(i);
        }
    }

    //Add Complaint Activity
    public void ViewStatus(View view1) {

        if(view1.getId() == R.id.buttonstatus)
        {
            Intent i = new Intent(ProfileActivity.this, ViewComplaintActivity.class);
            startActivity(i);
        }

    }

    //View Complaint Activity
//    public void FeedBack(View view) {
//
//        if(view.getId() == R.id.buttonfeed)
//        {
//            Intent i = new Intent(ProfileActivity.this, FeedBack.class);
//            startActivity(i);
//        }
//    }


}
