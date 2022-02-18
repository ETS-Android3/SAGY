package app.shi.com.sagy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Serializable;

import app.shi.com.sagy.model.Mps.MpsModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MpsActivity extends AppCompatActivity {

    MpsModel mps;
    ListView listView;
    /*private Button all;
    private Button my;*/
    String userid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme);
        Toolbar tb = findViewById(R.id.toolbar2);
        setSupportActionBar(tb);
        getSupportActionBar().setTitle("MPs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listView = findViewById(R.id.listview);
        SharedPreferences sagy = getSharedPreferences("sagy",0);
        login.ano=sagy.getString("ano","");
        new getData().execute();
        /*all = findViewById(R.id.all);
        my = findViewById(R.id.my);
        if(login.ano.equals("")){
            my.setVisibility(View.INVISIBLE);
           all.setVisibility(View.INVISIBLE);
        }
        all.setBackgroundResource(R.drawable.brownroundebtn);
        my.setBackgroundResource(R.drawable.greenroundebtn);
        my.setText("My MP");

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                my.setBackgroundResource(R.drawable.brownroundebtn);
                all.setBackgroundResource(R.drawable.greenroundebtn);
                SharedPreferences sagy = getSharedPreferences("sagy",0);
                userid= sagy.getString("ano","");
                new getData().execute();
            }
        });

        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userid="";
                all.setBackgroundResource(R.drawable.brownroundebtn);
                my.setBackgroundResource(R.drawable.greenroundebtn);
                new getData().execute();
            }
        });*/
    }

    private class getData  extends AsyncTask<Void,Void,String>{
        ProgressDialog dialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MpsActivity.this);
            dialog.setMessage("Loading..");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {

            String res="";
            try {
                if(userid.equals(""))
                {
                    res = run(getString(R.string.url)+"?action=get_mps");
                }
                else
                {
                    res = run(getString(R.string.url)+"?action=get_mps&uid="+userid);
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
                Toast.makeText(MpsActivity.this,"connection error",Toast.LENGTH_LONG).show();
            }
            else
            {
                mps =  new Gson().fromJson(s,MpsModel.class);
                listView.setAdapter(new customadapter(mps));
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
        MpsModel model ;
        public customadapter(MpsModel mps) {
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

            View view1 = getLayoutInflater().inflate(R.layout.scheme,null);
            TextView title = view1.findViewById(R.id.mp_name_TV);
            TextView village_name = view1.findViewById(R.id.village_name_TV);
            village_name.setVisibility(View.GONE);

            title.setText(model.getData().get(i).getName());
            //village_name.setText(model.getData().get(i).getVillageId());
            view1.setTag(i);
            view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int i = (int) view.getTag();
                    Intent intent = new Intent(MpsActivity.this,MpsDetailActivity.class);
                    intent.putExtra("data", (Serializable) mps.getData().get(i));
                    startActivity(intent);

                }
            });
            return view1;
        }
    }
}
