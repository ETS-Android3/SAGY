package app.shi.com.sagy;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hsalf.smilerating.SmileRating;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class rating extends AppCompatActivity {
    private int mSmileRating = 0;
    String mSmiltetext ="";
    String id;
    private EditText text;
    SmileRating smileRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        id = getIntent().getStringExtra("id");
         smileRating = (SmileRating) findViewById(R.id.smile_rating);
        text = findViewById(R.id.comments_et);

        SharedPreferences sagy = getSharedPreferences("sagy",0);
        login.ano = sagy.getString("ano","");
        TextView name =(TextView)findViewById(R.id.schemename_TV);
        name.setText("Scheme: "+getIntent().getStringExtra("scheme"));
        smileRating.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
//            @Override
            public void onRatingSelected(int level, boolean reselected) {
                mSmileRating = level;
                mSmiltetext = smileRating.getSmileName(level);
                // level is from 1 to 5 (0 when none selected)
                // reselected is false when user selects different smiley that previously selected one
                // true when the same smiley is selected.
                // Except if it first time, then the value will be false.
            }
        });
    }


    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public void feedbackclick(View view) {

        new submitreview().execute();
    }

    private class submitreview extends AsyncTask<Void,Void,Void> {
        ProgressDialog dialog ;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(rating.this);
            dialog.setMessage("Loading..");
            try {
                dialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                String resp =   run(getString(R.string.url)+"?action=insert_feedback&user_id="+login.ano+"&scheme_id="+id+"&feed_desc="+text.getText()+"&point="+mSmileRating+"&point_string="+mSmiltetext);
             //   run(resp);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            try {
                dialog.dismiss();
                finish();
            } catch (Exception e) {
                e.printStackTrace();
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

    }

}

