package app.shi.com.sagy;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.HttpUrl;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Registration extends AppCompatActivity {

    private FrameLayout login_redirect;
    private Button reg_btn;
    private EditText reg_e,reg_pin,reg_add,reg_ph,reg_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        login_redirect = (FrameLayout) findViewById(R.id.Login_redirect);
        reg_btn = (Button) findViewById(R.id.Reg_btn);
        reg_e = (EditText) findViewById(R.id.reg_email);
        reg_add = (EditText) findViewById(R.id.reg_add);
        reg_ph = (EditText) findViewById(R.id.reg_phone);
        reg_name = (EditText) findViewById(R.id.reg_name);
        reg_pin = (EditText) findViewById(R.id.reg_pin);

        login_redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Registration.this,login.class);
                startActivity(intent);
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean v=true;
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if((!reg_name.getText().toString().equals("")) && (!reg_e.getText().toString().equals("")) && (!reg_add.getText().toString().equals("")) && (!reg_ph.getText().toString().equals("")) && (!reg_pin.getText().toString().equals(""))) {
                    if(reg_ph.getText().toString().length()<10)
                    {
                        v=false;
                        Snackbar.make(view, "Mobile number should be of length 10", Snackbar.LENGTH_LONG)
                                .show();
                    }
                    if(reg_pin.getText().toString().length()<6){
                        v=false;
                        Snackbar.make(view, "Pin code should be of length 6.", Snackbar.LENGTH_LONG)
                                .show();
                    }
                    if (!reg_e.getText().toString().matches(emailPattern))
                    {
                        Snackbar.make(view, "Enter valid email address.", Snackbar.LENGTH_LONG)
                                .show();
                    }
                    if(v)
                    {
                        new register().execute();
                    }
                }else {
                    Snackbar.make(view, "Enter the Details!", Snackbar.LENGTH_LONG)
                            .show();
                }
            }
        });
    }

    private class register extends AsyncTask<Void,Void,Void> {

        private boolean isInserted;
        private Dialog dialog;
        @Override
        protected void onPreExecute() {

            dialog = new ProgressDialog(Registration.this);
            dialog.setTitle("Registering...");
            dialog.show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(isInserted){
                dialog.dismiss();
                Intent intent =new Intent(Registration.this,login.class);
                startActivity(intent);
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                HttpUrl.Builder builder = HttpUrl.parse(getResources().getString(R.string.url))
                        .newBuilder();
                builder.addQueryParameter("action", "register_user");
                builder.addQueryParameter("name", reg_name.getText().toString());
                builder.addQueryParameter("phone_no", reg_ph.getText().toString());
                builder.addQueryParameter("address", reg_add.getText().toString());
                builder.addQueryParameter("pin_code", reg_pin.getText().toString());
                builder.addQueryParameter("email", reg_e.getText().toString());
                Request request = new Request.Builder()
                        .url(builder.build())
                        .build();
                OkHttpClient client = new OkHttpClient();
                Response response = client.newCall(request).execute();

                JSONObject jobj = new JSONObject(response.body().string());
                JSONArray jsonArray = jobj.getJSONArray("data");
                Log.d("json", jobj.getString("data"));
                String data = jsonArray.getJSONObject(0).getString("value");
                if(data.equals("valid")){
                    isInserted = true;
                }else {
                    isInserted = false;
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
}
