package app.shi.com.sagy;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class wall_of_fame extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String Jurl = "http://phpstack-165541-479108.cloudwaysapps.com/api/webservice.php?action=get_walloffame";
    private JSONObject jobj;
    private boolean switchAPI = false;
    List<wall_of_fame_getset> FameList;
    Button mp, village;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_of_fame);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_village);
        FameList = new ArrayList<>();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        mp = findViewById(R.id.walloffameMP);
        mp.setBackgroundResource(R.drawable.brownroundebtn);
        village = findViewById(R.id.walloffamevillage);
        village.setBackgroundResource(R.drawable.greenroundebtn);

        mp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.setBackgroundResource(R.drawable.brownroundebtn);
                village.setBackgroundResource(R.drawable.greenroundebtn);
                switchAPI = false;
                Jurl = "http://phpstack-165541-479108.cloudwaysapps.com/api/webservice.php?action=get_walloffame";
                new getdata_wall().execute();
            }
        });


        village.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.setBackgroundResource(R.drawable.greenroundebtn);
                village.setBackgroundResource(R.drawable.brownroundebtn);
                switchAPI = true;
                Jurl = "http://phpstack-165541-479108.cloudwaysapps.com/api/webservice.php?action=ranking";
                new getdata_wall().execute();
            }
        });

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                //do something
                                                swipeRefreshLayout.setRefreshing(false);
                                                swipeRefreshLayout.setEnabled(false);
                                            }
                                        }, 1000);
                                    }
                                }
        );
        new getdata_wall().execute();
    }


    public class getdata_wall extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(wall_of_fame.this);
            dialog.setMessage("Loading...");
            dialog.show();
        }


        @Override
        protected Void doInBackground(Void... voids) {
            try {

                Request request = new Request.Builder()
                        .url(Jurl)
                        .build();

                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();

                jobj = new JSONObject(response.body().string());
                JSONArray jsonArray = jobj.getJSONArray("data");
                FameList = new ArrayList<wall_of_fame_getset>();

                if (switchAPI==false) {
                    for (int i = 0; i < 3; i++) {
                        wall_of_fame_getset mWall_of_fame_getset = new wall_of_fame_getset();
                        JSONObject ljobj = jsonArray.getJSONObject(i);
                        Fame fame = new Fame();
                        mWall_of_fame_getset.setGender(ljobj.getString("gender"));
                        mWall_of_fame_getset.setMp_img(ljobj.getString("mp_img"));
                        mWall_of_fame_getset.setVname(ljobj.getString("vname"));
                        mWall_of_fame_getset.setName(ljobj.getString("name"));
                        mWall_of_fame_getset.setScore(ljobj.getInt("score"));

                        FameList.add(mWall_of_fame_getset);
                    }
                } else {

                    for (int i = 0; i < 3; i++) {
                        wall_of_fame_getset mWall_of_fame_getset = new wall_of_fame_getset();
                        JSONObject ljobj = jsonArray.getJSONObject(i);
                        Fame fame = new Fame();
                        mWall_of_fame_getset.setGender("village");
                        mWall_of_fame_getset.setMp_img("village");
                        mWall_of_fame_getset.setVname(ljobj.getString("vname"));
                        mWall_of_fame_getset.setName("village");
                        mWall_of_fame_getset.setScore(ljobj.getInt("score"));

                        FameList.add(mWall_of_fame_getset);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();

            mAdapter = new AdapterWallOfFame(FameList, wall_of_fame.this);
            LinearLayoutManager mlinearLayoutManager = new LinearLayoutManager(wall_of_fame.this);
            mlinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mlinearLayoutManager);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.setAdapter(mAdapter);

        }

    }

}
