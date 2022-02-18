package app.shi.com.sagy;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;

import java.io.IOException;

import app.shi.com.sagy.model.Feedback.Feedback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class FeedBackListActivity extends AppCompatActivity {

    Feedback feedback;
    SharedPreferences sagy;
    ListView listView;
    String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_list);
        Toolbar toolbar = findViewById(R.id.toolbarFL);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Citizen's Feedback");

        Log.e("on create","in");
        listView = findViewById(R.id.listview);
        sagy = getSharedPreferences("sagy",0);
        user_id = sagy.getString("mp_id","");
        Log.e("mp id",user_id);
        new getdata().execute();
    }

    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        okhttp3.Response response = client.newCall(request).execute();
        String text = response.body().string();
        Log.e("reponse feedback",text);
        return text;
    }
    private class getdata extends AsyncTask<Void,Void,Void> {
        ProgressDialog dialog;
        String resp="";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(FeedBackListActivity.this);
            dialog.setMessage("Loading ...");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                resp = run(getString(R.string.url)+"?action=get_feedback&user_id="+user_id);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            try
            {
                feedback = new Gson().fromJson(resp,Feedback.class);
                customeradapter adapter = new customeradapter(feedback);
                listView.setAdapter(adapter);
            }
            catch (Exception ex)
            {
                    ex.printStackTrace();
            }

        }
    }

    private class customeradapter extends BaseAdapter {
        Feedback feedback;
        public customeradapter(Feedback feedback) {
            this.feedback = feedback;
        }

        @Override
        public int getCount() {
            return feedback.getData().size();
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

           View view1 = getLayoutInflater().inflate(R.layout.feedbackitem,null);
            TextView title = view1.findViewById(R.id.scheme);
            title.setText(feedback.getData().get(i).getSchemeId());
            TextView desc = view1.findViewById(R.id.desc);
            desc.setText(feedback.getData().get(i).getFeedDesc());
            RatingBar ratingBar = view1.findViewById(R.id.rating);
            ratingBar.setIsIndicator(true);
            try {
                ratingBar.setRating(Float.parseFloat(feedback.getData().get(i).getPoint()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            return view1;
        }
    }
}
