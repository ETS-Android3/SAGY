package app.shi.com.sagy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//import org.json.JSONArray;

//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;

public class AchivementActivity extends AppCompatActivity {
    private AchivementAdaptor mAdapter;
    private RecyclerView recyclerView;
    List<Achivement_fields> AchiveList;
    private String Jurl = "http://phpstack-165541-479108.cloudwaysapps.com/api/webservice.php?action=get_achivement";
    private JSONObject jobj;
    RequestQueue requestQueue;
    FloatingActionButton mFloatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achivement);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.Ach_add);
        recyclerView = (RecyclerView) findViewById(R.id.com_list);
        AchiveList = new ArrayList<>();
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AchivementActivity.this,AchievementUpload.class);
                startActivity(intent);
            }
        });
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        requestQueue = Volley.newRequestQueue(this);

        show();


    }

    public void show() {
        StringRequest obreq = new StringRequest(Request.Method.POST, Jurl,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter

                new Response.Listener<String>() {
                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(String response) {
                        Log.e("URL", Jurl);

                        Log.e("RES", response);

                        try {

                            AchiveList.clear();
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);
                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray Array = obj.getJSONArray("data");
                            //now looping through all the elements of the json array
                            for (int i = 0; i < Array.length(); i++) {
                                Achivement_fields machivement_fields = new Achivement_fields();

                                //getting the json object of the particular index inside the array
                                JSONObject Object = Array.getJSONObject(i);
                                //creating a hero object and giving them the values from json object

                                machivement_fields.setAc_img(Object.getString("imagename"));
                                machivement_fields.setAc_desc(Object.getString("desc"));
                                //adding the hero to herolist
                                AchiveList.add(machivement_fields);

//                                Log.e("bbbb",info.getTitleAchievement());
                            }

                            setadaptor();


//                            Log.e("123", String.valueOf(recieveList.size()));
                            //creating custom adapter object

                            //adding the adapter to listview


                            mAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", String.valueOf(error));
                    }
                }

        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(obreq);
    }

    private void setadaptor() {

        mAdapter = new AchivementAdaptor(AchiveList, AchivementActivity.this, new onAchivementItemClick() {
            @Override
            public void onAchiveItemClick(Achivement_fields achivement_fields) {

                Intent intent = new Intent(AchivementActivity.this, Achivement_detailActivity.class);
                intent.putExtra("image",achivement_fields.ac_img);
                intent.putExtra("desc",achivement_fields.getAc_desc());
                startActivity(intent);
            }

        });
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this,"OnRestart",Toast.LENGTH_SHORT).show();
        show();
//        setadaptor();
    }
}
