package app.shi.com.sagy;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

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

import app.shi.com.sagy.model.ProblemStatementAdapterClass;
import app.shi.com.sagy.model.recieveProblemStatement;

public class ShowDataOfProblemStatement extends AppCompatActivity {

    TextView problem_statement_TV;
    TextView p_village_TV;
    TextView Field_TV;
    TextView contact_details_TV;
    RecyclerView sol_recycler_view;
    ProblemStatementAdapterClass adapter;
    String JSON_URL = "http://phpstack-165541-479108.cloudwaysapps.com/api/webservice.php?action=get_problem";
    List<recieveProblemStatement> recievelist = new ArrayList<>();
    RequestQueue requestQueue;
    RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data_of_problem_statement);
        problem_statement_TV = (TextView) findViewById(R.id.problem_statement_TV);
        p_village_TV = (TextView) findViewById(R.id.p_village_TV);
        Field_TV = (TextView) findViewById(R.id.Field_TV);
        contact_details_TV = (TextView) findViewById(R.id.contact_details_TV);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        requestQueue = Volley.newRequestQueue(this);
        sol_recycler_view = (RecyclerView) findViewById(R.id.sol_recycler_view);
        /*insert();*/

        setadapter();
        show();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public void show() {
        StringRequest obreq = new StringRequest(Request.Method.POST, JSON_URL,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter

                new Response.Listener<String>() {
                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(String response) {
                        Log.e("URL", JSON_URL);

                        Log.e("RES", response);

                        try {
                            recievelist.clear();
                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray Array = obj.getJSONArray("data");

                            //now looping through all the elements of the json array
                            for (int i = 0; i < Array.length(); i++) {
                                recieveProblemStatement info = new recieveProblemStatement();
                                //getting the json object of the particular index inside the array
                                JSONObject Object = Array.getJSONObject(i);
                                //creating a hero object and giving them the values from json object
                                info.setProblem_statement_TV(Object.getString("statement"));
                                info.setP_village_TV(Object.getString("village_name"));
                                info.setField_TV(Object.getString("catagory"));
                                info.setContact_details_TV(Object.getString("contact_us"));

                                //adding the hero to herolist
                                recievelist.add(info);

                                Log.e("bbbb", info.getP_village_TV());
                            }
                            Log.e("123", String.valueOf(recievelist.size()));
                            //creating custom adapter object

                            //adding the adapter to listview


                            adapter.notifyDataSetChanged();

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

    public void setadapter()
    {

        adapter = new ProblemStatementAdapterClass(recievelist, ShowDataOfProblemStatement.this);
        sol_recycler_view.setAdapter(adapter);
       sol_recycler_view.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
    }
}
