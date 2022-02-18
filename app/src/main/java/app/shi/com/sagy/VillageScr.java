package app.shi.com.sagy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;

import app.shi.com.sagy.model.Village.VillageModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VillageScr extends AppCompatActivity {
    VillageModel model;

    //Button my,all;
    String userid="";
    ListView listView;
    boolean My = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_scr);

        Toolbar tb = findViewById(R.id.toolbar3);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("Villages");
        listView = (ListView) findViewById(R.id.my_recycler_view_village);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        /*all = findViewById(R.id.all);
        my = findViewById(R.id.my);

        all.setBackgroundResource(R.drawable.brownroundebtn);
        my.setBackgroundResource(R.drawable.greenroundebtn);*/
        SharedPreferences sagy = getSharedPreferences("sagy",0);
        login.ano=sagy.getString("ano","");
        /*if(login.ano.equals("")){
            my.setVisibility(View.INVISIBLE);
            all.setVisibility(View.INVISIBLE);
        }*/
        new getData().execute();
        FloatingActionButton fab = findViewById(R.id.myvillage);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                my_village_click(v);
                My=true;
            }
        });

        if(!sagy.getBoolean("verified",false))
        {
            fab.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if(My)
        {
            all_village_click();
            My=false;
        }else{
            super.onBackPressed();
        }
    }

    public void my_village_click(View view) {
        /*my.setBackgroundResource(R.drawable.brownroundebtn);
        all.setBackgroundResource(R.drawable.greenroundebtn);*/
        userid=login.ano;
        new getData().execute();
    }

    public void all_village_click() {
        /*all.setBackgroundResource(R.drawable.brownroundebtn);
        my.setBackgroundResource(R.drawable.greenroundebtn);*/
        userid="";
        new getData().execute();
    }
    private class getData  extends AsyncTask<Void,Void,String> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(VillageScr.this);
            dialog.setMessage("Loading..");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {

            String res="";
            try {
                if(userid.equals(""))
                {
                    res = run(getString(R.string.url)+"?action=get_village");
                }
                else
                {
                    res = run(getString(R.string.url)+"?action=get_village&uid="+userid);
                }
            } catch (IOException e) {
                e.printStackTrace();
                res = "error";
            }
            return res;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if(s.equals("error"))
            {
                Toast.makeText(VillageScr.this,"connection error",Toast.LENGTH_LONG).show();
            }
            else
            {
                model =  new Gson().fromJson(s,VillageModel.class);
                listView.setAdapter(new customadapter(model));

            }
        }
    }

    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    private class customadapter extends BaseAdapter {
        VillageModel model ;
        public customadapter(VillageModel mps) {
            model = mps;
        }

        @Override
        public int getCount() {
            return model.getData().size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View view1 = getLayoutInflater().inflate(R.layout.singlevillage,null);
            TextView title = view1.findViewById(R.id.tv_vil);


            title.setText(Html.fromHtml(Html.fromHtml(model.getData().get(i). getVname()).toString()));

            view1.setTag(i);
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int i = (int) view.getTag();
                    Intent intent = new Intent(VillageScr.this,infoActivity.class);
                    intent.putExtra("data", (Serializable) model.getData().get(i));
                    startActivity(intent);

                }
            });
            return view1;
        }
    }
}
