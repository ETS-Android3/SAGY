package app.shi.com.sagy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class schemeDetailActivity extends AppCompatActivity {
    RequestQueue requestQueue ;
    TextView scheme_description;
    TextView scheme_name;
    TextView scheme_document;
    TextView scheme_budget;
    String name;
    String document;
    String description;
    String budget;
    String type="";
    Button feedback;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scheme_detail);
        scheme_name = (TextView)findViewById(R.id.scheme_name);
        scheme_description = (TextView)findViewById(R.id.scheme_description);
        scheme_document = (TextView)findViewById(R.id.scheme_document);
        scheme_budget= (TextView)findViewById(R.id.scheme_budget);
        feedback= (Button) findViewById(R.id.feedback);

        type = getIntent().getStringExtra("type");
        id = getIntent().getStringExtra("id");
        Intent intent = getIntent();
        if(intent!=null)
        {
            name= intent.getStringExtra("name");
            document = intent.getStringExtra("document");
            description = intent.getStringExtra("description");
            budget = intent.getStringExtra("budget");

            scheme_name.setText(name);
            scheme_budget.setText(budget);
            scheme_document.setText(document);
            scheme_description.setText(description);
        }
        if(type.equals("all"))
        {
            feedback.setVisibility(View.GONE);
        }
        else
        {
            feedback.setVisibility(View.VISIBLE);
            feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(schemeDetailActivity.this,rating.class);
                    intent.putExtra("id",id);
                    intent.putExtra("scheme",name);
                    startActivity(intent);
                }
            });
        }
        SharedPreferences prefs = getSharedPreferences("sagy", MODE_PRIVATE);
        String restoredText = prefs.getString("scheme_name", null);
        if (restoredText != null) {
            String name = prefs.getString("scheme_name", "No name defined");//"No name defined" is the default value.
            Log.e("PREFVAL : ",name);
        }



        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        requestQueue = Volley.newRequestQueue(this);

    }

}
