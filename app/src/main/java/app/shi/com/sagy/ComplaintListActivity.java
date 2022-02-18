package app.shi.com.sagy;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ComplaintListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    String userid="";
    private complainAdptor mAdapter;
    private String Jurl = "http://phpstack-165541-479108.cloudwaysapps.com/api/webservice.php";
    private JSONObject jobj;
    List<Complaint> complaintList = new ArrayList<>();
    FloatingActionButton mFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_list);

        recyclerView = (RecyclerView) findViewById(R.id.com_list);

        complaintList = new ArrayList<>();
        mFloatingActionButton= (FloatingActionButton) findViewById(R.id.conmpAdd);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ComplaintListActivity.this,complain.class);
                startActivity(intent);
            }
        });
        SharedPreferences sagy = getSharedPreferences("sagy",0);
        if(!sagy.getBoolean("officer",true)) {
            Log.e("in user","user");
            login.ano = sagy.getString("ano", "");
            userid = login.ano;
            Log.e("userid",userid);
            setadapter();
            show();

           // new getdata().execute();
        }else
        {
            mFloatingActionButton.setVisibility(View.GONE);
            userid = sagy.getString("mp_id","");
            Log.e("mp_id",userid);
            setadapter();
            showMP();
            //new getdatamp().execute();
        }
//        Complaint complaint= new Complaint();
//        complaint.setTitle("Complaint1");
//        complaint.setImage(R.drawable.ic_idea);
//        Complaint complaint1= new Complaint();
//        complaint1.setTitle("Complaint1");
//        complaint1.setImage(R.drawable.ic_idea);
//        complaintList.add(complaint);
//        Complaint complaint2= new Complaint();
//        complaint2.setTitle("Complaint1");
//        complaint2.setImage(R.drawable.ic_idea);
//        complaintList.add(complaint);
//        Complaint complaint3= new Complaint();
//        complaint3.setTitle("Complaint1");
//        complaint3.setImage(R.drawable.ic_idea);
//        complaintList.add(complaint);



         }
public void showMP(){
        StringRequest obreq = new StringRequest(com.android.volley.Request.Method.POST, Jurl,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter

                new com.android.volley.Response.Listener<String>() {
                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(String response) {
                        Log.e("URL",Jurl);

                        Log.e("RES","test"+response);

                        try {
                            complaintList.clear();
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray Array = obj.getJSONArray("data");
                            if(Array.length()<=0)
                            {
                                Toast.makeText(ComplaintListActivity.this, "Hurray!!! no complains", Toast.LENGTH_SHORT).show();
                            }else {

                                //now looping through all the elements of the json array
                                for (int i = 0; i < Array.length(); i++) {
                                    Complaint info = new Complaint();
                                    //getting the json object of the particular index inside the array
                                    JSONObject Object = Array.getJSONObject(i);
                                    //creating a hero object and giving them the values from json object

                                    info.setDescription(Object.getString("complain_desc"));
                                    info.setStatus(Object.getString("complain_status"));
                                    info.setCid(Object.getInt("complain_id"));
                                    info.setTime(Object.getString("complain_time"));
                                    info.setPriority(Object.getInt("priority"));
                                    info.setImage(Object.getString("complain_image"));
                                    info.setUid(Object.getInt("user_id"));
                                    info.setVillageID(Object.getInt("village_id"));

                                    //adding the hero to herolist
                                    complaintList.add(info);

                                    Log.e("bbbb", info.getDescription());
                                }
                                Log.e("123", String.valueOf(complaintList.size()));
                                //creating custom adapter object

                                //adding the adapter to listview


                                mAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", String.valueOf(error));
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("action", "get_complain");
                params.put("mp_id", userid);
                Log.e("PARAMS", String.valueOf(params));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(obreq);
    }

    public void show() {

        StringRequest obreq = new StringRequest(com.android.volley.Request.Method.POST, Jurl,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter

                new com.android.volley.Response.Listener<String>() {
                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(String response) {
                        Log.e("URL",Jurl);

                        Log.e("RES","test"+response);

                        try {
                            complaintList.clear();
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray Array = obj.getJSONArray("data");

                            if(Array.length()<=0)
                            {
                                Toast.makeText(ComplaintListActivity.this, "Hurray!!! no complains", Toast.LENGTH_SHORT).show();
                            }else {

                                //now looping through all the elements of the json array
                                for (int i = 0; i < Array.length(); i++) {
                                    Complaint info = new Complaint();
                                    //getting the json object of the particular index inside the array
                                    JSONObject Object = Array.getJSONObject(i);
                                    //creating a hero object and giving them the values from json object

                                    info.setDescription(Object.getString("complain_desc"));
                                    info.setStatus(Object.getString("complain_status"));
                                    info.setCid(Object.getInt("complain_id"));
                                    info.setTime(Object.getString("complain_time"));
                                    info.setPriority(Object.getInt("priority"));
                                    info.setImage(Object.getString("complain_image"));
                                    info.setUid(Object.getInt("user_id"));
                                    info.setVillageID(Object.getInt("village_id"));

                                    //adding the hero to herolist
                                    complaintList.add(info);

                                    Log.e("bbbb", info.getDescription());
                                }
                                Log.e("123", String.valueOf(complaintList.size()));
                                //creating custom adapter object

                                //adding the adapter to listview


                                mAdapter.notifyDataSetChanged();
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", String.valueOf(error));
                    }
                }

        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
              params.put("user", "user");
                params.put("action", "get_complain");
                params.put("uid", userid);
                Log.e("PARAMS", String.valueOf(params));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(obreq);
    }
    public class getdata extends AsyncTask<Void,Void,Void> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ComplaintListActivity.this);
            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
            Log.e("in getdata ","in getdata");
            RequestBody req = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("user","user")
                    .addFormDataPart("action","get_complain")
                    .addFormDataPart("uid", userid).build();
                Request request = new Request.Builder()
                        .url(Jurl)
//                    .post(req)
                        .build();

                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();

//            Log.d("response", "uploadImage:" + response.body().string());
                String text = response.body().string();
                Log.d("RESPONSE",text);
                jobj = new JSONObject(text);

                JSONArray jsonArray = jobj.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject ljobj = jsonArray.getJSONObject(i);
                    Complaint complaint = new Complaint();
                    complaint.setImage(ljobj.getString("complain_image"));
                    complaint.setStatus(ljobj.getString("comlain_status"));
                    complaint.setUid(ljobj.getInt("user_id"));
                    complaint.setVillageID(ljobj.getInt("village_id"));
                    complaint.setCid(ljobj.getInt("complain_id"));
                    complaint.setTime(ljobj.getString("complain_time"));
                    complaint.setDescription(ljobj.getString("complain_desc"));
                    complaint.setPriority(ljobj.getInt("priority"));
                    complaintList.add(complaint);

                }

            } catch (Exception e) {
//            Log.e("Json Error", e.getMessage());
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();



            mAdapter = new complainAdptor(complaintList,ComplaintListActivity.this);
            LinearLayoutManager llm = new LinearLayoutManager(ComplaintListActivity.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

        }
    }

    private class getdatamp extends AsyncTask<Void,Void,Void> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(ComplaintListActivity.this);
            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                RequestBody req = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("mp_id", userid)
                        .build();
                Request request = new Request.Builder()
                        .url(Jurl)
//                    .post(req)
                        .build();

                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();

//            Log.d("response", "uploadImage:" + response.body().string());

                jobj = new JSONObject(response.body().string());
                JSONArray jsonArray = jobj.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject ljobj = jsonArray.getJSONObject(i);
                    Complaint complaint = new Complaint();
                    complaint.setImage(ljobj.getString("complain_image"));

                    complaint.setUid(ljobj.getInt("user_id"));
                    complaint.setVillageID(ljobj.getInt("village_id"));
                    complaint.setCid(ljobj.getInt("complain_id"));
                    complaint.setTime(ljobj.getString("complain_time"));
                    complaint.setDescription(ljobj.getString("complain_desc"));
                    complaint.setPriority(ljobj.getInt("priority"));
                    complaintList.add(complaint);

                }

            } catch (Exception e) {
//            Log.e("Json Error", e.getMessage());
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();



            mAdapter = new complainAdptor(complaintList,ComplaintListActivity.this);
            LinearLayoutManager llm = new LinearLayoutManager(ComplaintListActivity.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);

        }
    }
    public void setadapter()
    {
        mAdapter = new complainAdptor(complaintList, ComplaintListActivity.this);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPreferences sagy = getSharedPreferences("sagy",0);
        if(!sagy.getBoolean("officer",true)) {
            Log.e("in user","user");
            login.ano = sagy.getString("ano", "");
            userid = login.ano;
            Log.e("userid",userid);
            setadapter();
            show();

            // new getdata().execute();
        }else
        {
            userid = sagy.getString("mp_id","");
            setadapter();
            showMP();
            mFloatingActionButton.setVisibility(View.GONE);
            //new getdatamp().execute();
        }
    }
}
