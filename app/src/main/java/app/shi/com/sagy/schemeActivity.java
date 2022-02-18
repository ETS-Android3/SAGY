package app.shi.com.sagy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

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

import app.shi.com.sagy.model.RecieveScheme;

import static java.lang.String.valueOf;

public class schemeActivity extends AppCompatActivity {

    RecyclerView RvUser;
    SchemeAdapterClass adapter;
    List<RecieveScheme> recieveList= new ArrayList<>();
    RequestQueue requestQueue ;
    /*Button myvillage;
    Button all;*/
    RecyclerView.LayoutManager mLayoutManager;
    public final String MY_PREFS_NAME = "sagy";
    /*private Button btnVilage;
    private Button btnAll;*/
    String uid = "";
    String type="all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme1);
        Toolbar tb = findViewById(R.id.toolbar2);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Scheme");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sagy = getSharedPreferences("sagy",0);
        login.ano=sagy.getString("ano","");
        /*myvillage = (Button)findViewById(R.id.myvillage);
        btnAll = (Button)findViewById(R.id.all);
        if(login.ano.equals("")){
            myvillage.setVisibility(View.INVISIBLE);
            btnAll.setVisibility(View.INVISIBLE);
        }*/
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        requestQueue = Volley.newRequestQueue(this);
        RvUser = (RecyclerView) findViewById(R.id.RvUser);
        final TextView sname= (TextView) findViewById(R.id.scheme_name);

        /*insert();*/

        /*btnVilage = findViewById(R.id.myvillage);
        btnVilage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type="my";
                btnVilage.setBackgroundResource(R.drawable.brownroundebtn);
                btnAll.setBackgroundResource(R.drawable.greenroundebtn);
                uid = login.ano;
                show();
            }
        });

        btnAll = findViewById(R.id.all);
        btnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = "all";
                btnAll.setBackgroundResource(R.drawable.brownroundebtn);
                btnVilage.setBackgroundResource(R.drawable.greenroundebtn);
                uid = "";
                show();
            }
        });

        btnAll.setBackgroundResource(R.drawable.brownroundebtn);
        btnVilage.setBackgroundResource(R.drawable.greenroundebtn);
*/
        show();

    }
    public void show() {
        String url;
        final ProgressDialog dialog;
        dialog = new ProgressDialog(schemeActivity.this);
        dialog.setMessage("Loading ...");
        dialog.show();
        if(uid.equals("")){
            url= getString(R.string.url)+"?action=scheme_village";
        }else {
            url= getString(R.string.url)+"?action=scheme_village&uid="+uid;
        }
        StringRequest obreq = new StringRequest(Request.Method.GET, url,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter

                new Response.Listener<String>() {
                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(String response) {


                        Log.e("RESPONSE", response);

                        try {
                            recieveList.clear();
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray Array = obj.getJSONArray("data");

                            recieveList.clear();
                            //now looping through all the elements of the json array
                            for (int i = 0; i < Array.length(); i++) {
                                final RecieveScheme info = new RecieveScheme();
                                //getting the json object of the particular index inside the array
                                JSONObject Object = Array.getJSONObject(i);
                                //creating a hero object and giving them the values from json object

                                info.setId(Object.getString("scheme_id"));
                                info.setName(Object.getString("name"));
                                info.setBudget(Object.getString("scheme_budget"));
                                info.setDescription(Object.getString("description"));
                              //  info.setDocument(Object.getString("document"));

                                //adding the hero to herolist
                                recieveList.add(info);



                                Log.e("bbbb", info.getName());
                              /*  RvUser.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
                                    @Override
                                    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                                        Toast.makeText(getApplicationContext(),info.getName(),Toast.LENGTH_SHORT).show();
                                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE).edit();
                                        editor.putString("scheme_name",info.getName());
                                        editor.apply();
                                        Intent intent = new Intent(schemeActivity.this,schemeDetailActivity.class);
                                        startActivity(intent);
                                        return false;
                                    }
                                    @Override
                                    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                                        Toast.makeText(getApplicationContext(),"2nd click",Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
                                        Toast.makeText(getApplicationContext(),"3rd click",Toast.LENGTH_SHORT).show();
                                    }
                                });*/
                            }
                            Log.e("123456", valueOf(recieveList.size()));

                            setadapter();

                            dialog.dismiss();
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
                        Log.e("Volley", valueOf(error));
                    }
                }

        );

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(obreq);
    }

    public void setadapter()
    {

        adapter = new SchemeAdapterClass(recieveList, schemeActivity.this, new OnSchemeItemClick() {
            @Override
            public void OnSchemeItemClick(RecieveScheme scheme) {


                SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE).edit();
                editor.putString("scheme_name",scheme.getName());
                editor.apply();

                Log.e("SCHEME",scheme.getName());
                Intent intent = new Intent(schemeActivity.this,schemeDetailActivity.class);
                intent.putExtra("name",scheme.getName());
                intent.putExtra("id",scheme.getId());
                intent.putExtra("description",scheme.getDescription());
               // intent.putExtra("document",scheme.getDocument());
                intent.putExtra("budget",scheme.getBudget());
                intent.putExtra("type",type);
                startActivity(intent);
            }
        });
        RvUser.setAdapter(adapter);
        RvUser.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }
}
