package app.shi.com.sagy;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

/**
 * Created by Abhinity on 4/13/18.
 */

public class AchievementUpload extends AppCompatActivity {

    TextView Atitle, Adesc;
    ImageView Aimg;
    Button Acamera, Abrowse, Asubmit;
    public static final int RequestPermissionCode = 1;
    private static int RESULT_LOAD_IMAGE = 1;
    RequestQueue requestQueue;
    int flag = 0;
    ProgressDialog progressDialog;
    Intent intent = getIntent();
    Bitmap bitmap=null;
    String file_path;
    JSONObject resp;
    String imagename;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.achievement_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarAchievement);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Post Achievement");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        requestQueue = Volley.newRequestQueue(this);

        Atitle = findViewById(R.id.Atitle);
        Adesc = findViewById(R.id.Adesc);
        Aimg = findViewById(R.id.Aimg);
        Acamera = (Button) findViewById(R.id.Acamera);
        Abrowse = (Button) findViewById(R.id.Abrowse);
        Asubmit = (Button) findViewById(R.id.Asubmit);
        Asubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valid();
            }
        });
        Acamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(AchievementUpload.this, Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED) {
                    EnableRuntimePermission();
                }else {
                    flag = 0;
                    intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent, 7);
                }
            }
        });
        Abrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 1;

                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
            }
        });
    }

    private void valid()
    {
        boolean valid = true;
        if(TextUtils.isEmpty(Atitle.getText().toString()))
        {
            valid=false;
            Atitle.setError("Enter title");
        }
        if(TextUtils.isEmpty(Adesc.getText().toString()))
        {
            valid=false;
            Adesc.setError("Enter description");
        }
        if(bitmap==null)
        {
            valid=false;
            Toast.makeText(this, "Image is mandatory", Toast.LENGTH_SHORT).show();
        }
        if(valid)
        {
            new upload().execute();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (flag == 0) {
            if (requestCode == 7 && resultCode == RESULT_OK) {

                bitmap = (Bitmap) data.getExtras().get("data");

                Aimg.setImageBitmap(bitmap);
            }
        } else if (flag == 1) {
            super.onActivityResult(requestCode, resultCode, data);


            if (resultCode == RESULT_OK) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    bitmap = BitmapFactory.decodeStream(imageStream);
                    Aimg.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                }

            }
        }
    }

    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(AchievementUpload.this,
                android.Manifest.permission.CAMERA)) {

            Toast.makeText(AchievementUpload.this, "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(AchievementUpload.this, new String[]{
                    android.Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(AchievementUpload.this, "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();
                    flag = 0;
                    intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                    startActivityForResult(intent, 7);

                } else {

                    Toast.makeText(AchievementUpload.this, "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    private String getSetImagePath(Bitmap image) {
        imagename = String.valueOf(System.currentTimeMillis()) + ".png";
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

    private class upload extends AsyncTask<Void,Void,Void>{

        ProgressDialog pd;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(AchievementUpload.this);
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


                URL url = new URL("http://phpstack-165541-479108.cloudwaysapps.com/api/webservice.php");
                final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
                Log.d("okhttp", "building request");
                Log.d("data", "local "+Adesc.getText().toString());
                RequestBody req = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("action", "insert_achivement")
                        .addFormDataPart("desc", Adesc.getText().toString())
                        .addFormDataPart("imagename", imagename)
                        .addFormDataPart("title",Atitle.getText().toString())
                        .addFormDataPart("like_count","0")
                        .addFormDataPart("image", imagename, RequestBody.create(MEDIA_TYPE_PNG, image)).build();

//                        Log.d("Data : ",desc.getText().toString()+" "+String.valueOf(System.currentTimeMillis())+" "+login.ano+" "+imagename);
                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(url)
                        .post(req)
                        .build();

//                Log.d("okhttp", "sending request");
                OkHttpClient client = new OkHttpClient.Builder().readTimeout(30000, TimeUnit.MILLISECONDS).writeTimeout(60000, TimeUnit.MILLISECONDS).build();
                okhttp3.Response response = client.newCall(request).execute();
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
                    Toast.makeText(AchievementUpload.this, "Achievemnent submitted", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(AchievementUpload.this, "Please try again", Toast.LENGTH_SHORT).show();
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}