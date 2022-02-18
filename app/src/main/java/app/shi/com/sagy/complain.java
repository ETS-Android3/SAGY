package app.shi.com.sagy;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.test.mock.MockPackageManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

public class complain extends AppCompatActivity implements OnItemSelectedListener {
    TextView tv;
    Spinner vSpinner;
    ArrayList<String> varrayList;
    Spinner spinner1;
    Button varify_btn;
    ImageView cameraimg;
    Button submit;
    Button click_image;
    JSONObject resp;
    Intent intent = getIntent();
    public static final int RequestPermissionCode = 1;
    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    GPSTracker gps;
    String item;
    ArrayAdapter<CharSequence> dataAdapter;
    int flag;
    String file_path;
    Bitmap bitmap;
    EditText desc;
    ImageView microphoneid;
    String villageId="";
    String JSON_URL = "http://phpstack-165541-479108.cloudwaysapps.com/api/webservice.php";
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    Map<String,String> village_id = new HashMap<>();
    String priority="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain);
        desc = findViewById(R.id.desc);
        varify_btn = (Button) findViewById(R.id.submit_btn);
        Toolbar toolbar = findViewById(R.id.toolbarC);
        setSupportActionBar(toolbar);
        insert();

        microphoneid = (ImageView) findViewById(R.id.microphoneid);
        microphoneid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startVoiceInput();
            }
        });
        cameraimg = (ImageView) findViewById(R.id.cameraimg);
        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission)
                    != MockPackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);

                // If any permission above not allowed by user, this condition will
                // execute every time, else your else part will work
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        varify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.equals(null) || item.equals("select_catagory")) {
                    Toast.makeText(getApplicationContext(), "please select a catagory to procced", Toast.LENGTH_LONG).show();
                } else {
                    if (flag == 1) {
                        Log.d("posting complain","complain");
                        if(item.equals("Crime"))
                        {
                            priority="1";
                        }
                        else{
                            priority="0";
                        }
                        new postCompl().execute();
                    }
                }
            }
        });

        click_image = findViewById(R.id.click_image);
        // show location button click event
        click_image.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // create class object
                if(ContextCompat.checkSelfPermission( complain.this, Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED ) {
                    EnableRuntimePermission();
                }else {
                    intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent, 7);
                }
            }

        });

        spinner1 = (Spinner) findViewById(R.id.btn);
        spinner1.setOnItemSelectedListener((OnItemSelectedListener) this);

        // Creating adapter for spinner
        dataAdapter = ArrayAdapter.createFromResource(this, R.array.select_catagory, android.R.layout.simple_spinner_item);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(dataAdapter);
        vSpinner=(Spinner) findViewById(R.id.village);
        vSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                villageId = village_id.get(adapterView.getItemAtPosition(i).toString());
                Log.e("village: ",villageId);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void insert() {

        StringRequest obreq = new StringRequest(Request.Method.POST, JSON_URL,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter

                new com.android.volley.Response.Listener<String>() {
                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(String response) {
                        Log.e("URL", JSON_URL);

                        Log.e("RES", response);

//                        for(int i=0; i < JSON_URL.length() ; i++) {
//                            json_data = jArray.getJSONObject(i);
//                            int id=json_data.getInt("id");
//                            String name=json_data.getString("name");
//                            items.add(name);
//                            Log.d(name,"Output");
//

                        try {

                            //getting the whole json object from the response
                            JSONObject obj = new JSONObject(response);

                            //we have the array named hero inside the object
                            //so here we are getting that json array
                            JSONArray Array = obj.getJSONArray("data");

                            //now looping through all the elements of the json array
//                            vname= new ArrayList<String>();
                            varrayList =new ArrayList<String>();
                            for (int i = 0; i < Array.length(); i++) {
//                                recieveComplain info = new recieveComplain();
                                //getting the json object of the particular index inside the array
                                JSONObject Object = Array.getJSONObject(i);
                                //creating a hero object and giving them the values from json object
                                varrayList.add(Object.getString("vname"));
                                village_id.put(Object.getString("vname"),Object.getString("village_id"));
                                Log.e("vname", Object.getString("vname"));
                                //adding the hero to herolist
                            }

                            vSpinner.setAdapter(new ArrayAdapter<String>(complain.this,android.R.layout.simple_spinner_dropdown_item,varrayList));

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }
                },
//                // The final parameter overrides the method onErrorResponse() and passes VolleyError
//                //as a parameter
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("action", "get_village");
                params.put("action1", "get_village_name");
                Log.e("PARAMS", String.valueOf(params));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(obreq);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    desc.setText(result.get(0));
                }
                break;
            }
        }
        if (requestCode == 7 && resultCode == RESULT_OK) {

            gps = new GPSTracker(complain.this);

            if (resultCode == RESULT_OK) {
                previewCapturedImage();
                if (gps.canGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();

                    // \n is for new line

                    /*longitudeid.setText((int) gps.getLongitude());
                    latitudeid.setText((int) gps.getLatitude());*/

                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();

                } else {
                    // Can't get location.
                    // GPS or network is not enabled.
                    // Ask user to enable GPS/network in settings.
                    gps.showSettingsAlert();
                }

            } else if (resultCode == RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getApplicationContext(),
                        "Cancelled", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getApplicationContext(),
                        "Error!", Toast.LENGTH_SHORT)
                        .show();
            }
            flag = 1;
            bitmap = (Bitmap) data.getExtras().get("data");

            cameraimg.setImageBitmap(bitmap);
        }
    }

    private void previewCapturedImage() {
    }

    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(complain.this,
                Manifest.permission.CAMERA)) {

            Toast.makeText(complain.this, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(complain.this, new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        item = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(complain.this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();
                    intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent, 7);

                } else {

                    Toast.makeText(complain.this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    private String getSetImagePath(Bitmap image) {
        String imagename = String.valueOf(System.currentTimeMillis()) + ".png";
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory, imagename);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            image.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d("image", "+++++++++++++++++++++++++++encode complete");
        return directory.getAbsolutePath() + "/" + imagename;
    }

    public class postCompl extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(complain.this);
            pd.setMessage("Posting...");
            pd.show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Log.d("encoding","dd");
                file_path = getSetImagePath(bitmap);
                Log.d("sending","send");
                File image = new File(file_path);

                String imagename = String.valueOf(System.currentTimeMillis()) + ".png";
                URL url = new URL("http://phpstack-165541-479108.cloudwaysapps.com/api/webservice.php");
                final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
                Log.d("okhttp", "building request");
                Log.d("data", "local "+desc.getText().toString()+" "+login.ano+" "+imagename+" "+villageId);
                RequestBody req = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("action", "insert_complain")
                        .addFormDataPart("complain_desc", desc.getText().toString())
                        .addFormDataPart("complain_time", String.valueOf(System.currentTimeMillis()))
                        .addFormDataPart("complain_status", "pending")
                        .addFormDataPart("user_id", login.ano)
                        .addFormDataPart("complain_image", imagename)
                        .addFormDataPart("village",villageId)
                        .addFormDataPart("priority",priority)
                        .addFormDataPart("image", imagename, RequestBody.create(MEDIA_TYPE_PNG, image)).build();

//                        Log.d("Data : ",desc.getText().toString()+" "+String.valueOf(System.currentTimeMillis())+" "+login.ano+" "+imagename);
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(url)
                        .post(req)
                        .build();

//                Log.d("okhttp", "sending request");
                OkHttpClient client = new OkHttpClient.Builder().readTimeout(30000, TimeUnit.MILLISECONDS).writeTimeout(60000, TimeUnit.MILLISECONDS).build();
                Response response = client.newCall(request).execute();
                String test = response.body().string();
                Log.d("okhttp", "request sent to complain"+test);
                resp = new JSONObject(test);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pd.dismiss();
            try {
                JSONArray jsonArray = resp.getJSONArray("data");
                JSONObject obj = jsonArray.getJSONObject(0);
                if(obj.getString("value").equals("valid"))
                {
                    Toast.makeText(complain.this, "complain submitted", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(complain.this, "Please try again", Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

}
