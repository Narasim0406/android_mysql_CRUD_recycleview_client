    package com.example.narasim.jm_solution_client;

    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.support.v7.app.AppCompatActivity;
    import android.util.Log;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.EditText;
    import android.widget.ListAdapter;
    import android.widget.ListView;
    import android.widget.SimpleAdapter;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.util.ArrayList;
    import java.util.HashMap;

    public class ViewAllService extends AppCompatActivity implements ListView.OnItemClickListener {

        private ListView listView;

        private String JSON_STRING;

        EditText textUsername;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_all_service);
            listView = (ListView) findViewById(R.id.listView);
            listView.setOnItemClickListener(this);
            getJSON();

    //        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
    //            Log.d("test","responses");
    //            finish();
    //            startActivity(new Intent(this, LoginActivity.class));
    //        }
    //
    //        textUsername = (EditText) findViewById(R.id.textUsername);
    //
    //        User user = SharedPrefManager.getInstance(this).getUser();
    //
    //        textUsername.setText(user.getUsername());
        }


        private void showEmployee(){
            JSONObject jsonObject = null;
            ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
            try {
                jsonObject = new JSONObject(JSON_STRING);
                JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

                for(int i = 0; i<result.length(); i++){
                    JSONObject jo = result.getJSONObject(i);
                    String id = jo.getString(Config.TAG_ID);
                    String token = jo.getString(Config.TAG_TOKEN);
                    String service = jo.getString(Config.TAG_SERVICE);
                    String status = jo.getString(Config.TAG_STATUS);

                    HashMap<String,String> employees = new HashMap<>();
                    employees.put(Config.TAG_ID,id);
                    employees.put(Config.TAG_TOKEN,token);
                    employees.put(Config.TAG_SERVICE,service);
                    employees.put(Config.TAG_STATUS,status);
                    list.add(employees);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            ListAdapter adapter = new SimpleAdapter(
                    ViewAllService.this, list, R.layout.list_item_service,
                    new String[]{Config.TAG_ID, Config.TAG_TOKEN, Config.TAG_SERVICE, Config.TAG_STATUS},
                    new int[]{R.id.id, R.id.token, R.id.service, R.id.status});

            listView.setAdapter(adapter);
        }

        private void getJSON(){

    //        textUsername.setText(user.getUsername);
            if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
                Log.d("test","responses");
                finish();
                startActivity(new Intent(this, LoginActivity.class));
            }

            textUsername = (EditText) findViewById(R.id.textUsername);

            User user = SharedPrefManager.getInstance(this).getUser();

            textUsername.setText(user.getUsername());

            String username = textUsername.getText().toString();
            final String URL_SERVICE = "http://arunraaza.com/android/android/getAllService.php?username=" + username;

            class GetJSON extends AsyncTask<Void,Void,String> {


                ProgressDialog loading;
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(ViewAllService.this,"Fetching Data","Wait...",false,false);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    JSON_STRING = s;
                    showEmployee();
                }

                @Override

                protected String doInBackground(Void... params) {
    //                String username = textUsername.getText().toString();
                    RequestHandler rh = new RequestHandler();
                    String s = rh.sendGetRequest(URL_SERVICE);
                    return s;
                }
            }
            GetJSON gj = new GetJSON();
            gj.execute();
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(this, ViewService.class);
            HashMap<String,String> map =(HashMap)parent.getItemAtPosition(position);
            String empId = map.get(Config.TAG_ID).toString();
            intent.putExtra(Config.EMP_ID,empId);
            startActivity(intent);
        }
    }
