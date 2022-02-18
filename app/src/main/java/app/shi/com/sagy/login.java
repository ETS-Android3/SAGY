package app.shi.com.sagy;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import app.shi.com.sagy.model.Mps.MpsModel;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class login extends AppCompatActivity {


    private static final String KEY_VERIFY_IN_PROGRESS = "key_verify_in_progress";
    boolean valid=true;
    private static final int STATE_INITIALIZED = 1;
    AlertDialog popup;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;
    private FrameLayout skip_lay;
    private FirebaseAuth mAuth;
    EditText username,password;
    Spinner type;
    // [END declare_auth]
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    String userID;
    public static String ano="";
ProgressDialog pd;

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    public static String contactNo;

    EditText aadhar_no;
    Button verify_btn;
   // String JSON_URL = "http://192.168.29.175:8888/sagy/varification.php";
    String JSON_URL = "http://phpstack-165541-479108.cloudwaysapps.com/api/webservice.php";
    Button login_btn;
    RequestQueue requestQueue;
    public static String pin_code;
    Boolean isLoginClick=false;
    public static MpsModel mps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.e("activity",JSON_URL);
        aadhar_no = (EditText)findViewById(R.id.aadhar_no);
        login_btn = (Button)findViewById(R.id.login_btn);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        type = (Spinner) findViewById(R.id.type);
        SharedPreferences sagy = getSharedPreferences("sagy",0);
        if(sagy.getBoolean("verified",false))
        {
            Intent intent = new Intent(login.this,DashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        /*else if(sagy.getBoolean("verifiedMP",false)){
            Intent intent = new Intent(login.this,mplogin.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }*/

        TextView reg = findViewById(R.id.regTxt);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this,Registration.class));
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("on click ","in on click");
                validMP();
            }
        });
        verify_btn = (Button)findViewById(R.id.verify_btn);
        verify_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validAno();
            }
        });

        pd = new ProgressDialog(this);
        requestQueue = Volley.newRequestQueue(this);

        skip_lay = (FrameLayout) findViewById(R.id.skip_layout);

        skip_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ano = aadhar_no.getText().toString()+"";
                Intent mintent = new Intent(login.this, DashboardActivity.class);
                mintent.putExtra("user","guest");
                startActivity(mintent);
            }
        });

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        // Initialize phone auth callbacks
        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verificaiton without
                //     user action.
                Log.d("login", "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                updateUI(STATE_VERIFY_SUCCESS, credential);
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w("login", "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
//                    mPhoneNumberField.setError("Invalid phone number.");
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    //  Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                    //    Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                updateUI(STATE_VERIFY_FAILED);
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d("login", "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // [START_EXCLUDE]
                // Update UI
                updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };
        // [END phone_auth_callbacks]


    }

    public void validMP()
    {
        Log.e("in valid","validation");
        valid = true;
        if(TextUtils.isEmpty(username.getText().toString()))
        {
            valid=false;
            username.setError("Enter username");
        }
        if(TextUtils.isEmpty(password.getText().toString()))
        {
            valid= false;
            password.setError("Enter password");
        }
        if(valid)
        {
            new getData().execute();
        }
    }

    private void validAno()
    {
        valid = true;
        if(TextUtils.isEmpty(aadhar_no.getText().toString()))
        {
            aadhar_no.setError("Enter aadhar number");
            valid=false;
        }else
        {
            if(aadhar_no.getText().toString().length()<10)
            {
                aadhar_no.setError("Mobile number should be of 10 digit");
                valid= false;
            }
        }
        if(valid)
        {
            contactNo = aadhar_no.getText().toString();
            insert();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mVerificationInProgress && validatePhoneNumber()) {
            startPhoneNumberVerification(contactNo);
        }
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+91" + phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("login", "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();

                            Toast.makeText(login.this, "Number verified.", Toast.LENGTH_SHORT).show();
                            //check if user exist or not in Database

                            userID = user.getUid();
                            pd.dismiss();
                            Toast.makeText(login.this, "verification success", Toast.LENGTH_LONG).show();
                            ano = aadhar_no.getText().toString()+"";
                            SharedPreferences.Editor editor = getSharedPreferences("sagy",MODE_PRIVATE).edit();
                            editor.putString("pin_code",pin_code);
                            editor.putBoolean("verified",true);
                            editor.putString("ano",contactNo);
                            editor.putBoolean("officer",false);
                            editor.apply();
                            Intent intent = new Intent(login.this,DashboardActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            isLoginClick=false;

                            popup.dismiss();
                            myRef.child("users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot snapshot) {
                                    if (snapshot.getValue() != null) {

                                        /*Intent y = new Intent(login.this, MainActivity.class);
                                        y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(y);
                                        SharedPreferences mPreferences;

                                        mPreferences = getSharedPreferences("User", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = mPreferences.edit();
                                        editor.putString("saveuserid", userID);
                                        editor.commit();*/

                                        //user exists, do something
                                    } else {

                                        /*SharedPreferences mPreferences;

                                        mPreferences = getSharedPreferences("User", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = mPreferences.edit();
                                        editor.putString("saveuserid", userID);
                                        editor.commit();
                                        //user does not exist, do something else
                                        myRef.child("users").child(userID).setValue("true");
                                        //    myRef.child("users").child(userID).child("Name").setValue("true");
                                        myRef.child("users").child(userID).child("contact").setValue(contactNo);


                                        Intent y = new Intent(PhoneAuthActivity.this, MainActivity.class);
                                        y.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        y.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(y);*/

                                    }
                                }

                                public void onCancelled(DatabaseError arg0) {
                                }
                            });


                            //  myRef.child(userID).child("title").setValue("true");
                            //    myRef.child(userID).child("description").setValue("true");
                            // myRef.child(userID).child("imageurl").setValue("true");
                            //  myRef.child(userID).child("url").setValue("true");
                            // [START_EXCLUDE]
                            updateUI(STATE_SIGNIN_SUCCESS, user);
                            // [END_EXCLUDE]
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w("login", "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                // [START_EXCLUDE silent]
                                //mVerificationField.setError("Invalid code.");
                                // [END_EXCLUDE]
                            }
                            // [START_EXCLUDE silent]
                            // Update UI
                            updateUI(STATE_SIGNIN_FAILED);
                            // [END_EXCLUDE]
                        }
                    }
                });
    }
    // [END sign_in_with_phone]


    private void updateUI(int uiState) {
        updateUI(uiState, mAuth.getCurrentUser(), null);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            updateUI(STATE_SIGNIN_SUCCESS, user);
        } else {
            updateUI(STATE_INITIALIZED);
        }
    }

    private void updateUI(int uiState, FirebaseUser user) {
        updateUI(uiState, user, null);
    }

    private void updateUI(int uiState, PhoneAuthCredential cred) {
        updateUI(uiState, null, cred);
    }

    private void updateUI(int uiState, FirebaseUser user, PhoneAuthCredential cred) {
        switch (uiState) {
            case STATE_INITIALIZED:
                // Initialized state, show only the phone number field and start button
                break;
            case STATE_CODE_SENT:
                pd.dismiss();
                // Code sent state, show the verification field, the
                //  mDetailText.setText(R.string.status_code_sent);
                //TODO: add popup


                Toast.makeText(login.this, "code sent", Toast.LENGTH_LONG).show();
                break;
            case STATE_VERIFY_FAILED:
                //  mDetailText.setText(R.string.status_verification_failed);
                pd.dismiss();

                Toast.makeText(login.this, "verification failed", Toast.LENGTH_LONG).show();
                break;
            case STATE_VERIFY_SUCCESS:
                pd.dismiss();
                Toast.makeText(login.this, "verification success", Toast.LENGTH_LONG).show();
                ano = aadhar_no.getText().toString()+"";
                SharedPreferences.Editor editor = getSharedPreferences("sagy",MODE_PRIVATE).edit();
                editor.putString("pin_code",pin_code);
                editor.putBoolean("verified",true);
                editor.putString("ano",ano);
                editor.putBoolean("officer",false);
                editor.apply();
                    Intent intent = new Intent(login.this,DashboardActivity.class);
                    intent.putExtra("user","verified");
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                isLoginClick=false;

                popup.dismiss();

                // Set the verification text based on the credential
                if (cred != null) {
                    if (cred.getSmsCode() != null) {
                        Toast.makeText(login.this, "in update ui cred!=null", Toast.LENGTH_SHORT).show();
                        //mVerificationField.setText(cred.getSmsCode());
                    } else {
                        //         mVerificationField.setText(R.string.instant_validation);
                    }
                }

                break;
            case STATE_SIGNIN_FAILED:
                // No-op, handled by sign-in check
                //  mDetailText.setText(R.string.status_sign_in_failed);
                break;
            case STATE_SIGNIN_SUCCESS:
                // Np-op, handled by sign-in check
                break;
        }

        if (user == null) {
            // Signed out
            //  mPhoneNumberViews.setVisibility(View.VISIBLE);
            //    mSignedInViews.setVisibility(View.GONE);
            //  mStatusText.setText(R.string.signed_out);
        } else {
            // Signed in


            //  mStatusText.setText(R.string.signed_in);
            //  mDetailText.setText(getString(R.string.firebase_status_fmt, user.getUid()));
        }
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = contactNo;
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        }

        return true;
    }




    //firebase

    public void insert() {

        StringRequest obreq = new StringRequest(com.android.volley.Request.Method.POST, JSON_URL,
//                // The third parameter Listener overrides the method onResponse() and passes
//                //JSONObject as a parameter
               new Response.Listener<String>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(String response) {
                        Log.e("RES", response);
                        try {

                            JSONObject ob = new JSONObject(response);
                            JSONArray arr = ob.getJSONArray("data");
                            if(arr.length()==0)
                            {
                                Toast.makeText(login.this, "Please register.", Toast.LENGTH_SHORT).show();
                            }else {
                                JSONObject obj = arr.getJSONObject(0);

                                pin_code = obj.getString("pin_code");
                                Log.e("contact_no", contactNo);
                                Log.e("pin code", pin_code);
                                startPhoneNumberVerification(contactNo);

                                final AlertDialog.Builder builder = new AlertDialog.Builder(login.this);

                                View pop = getLayoutInflater().inflate(R.layout.dialogxml,null);
                                Button ok = pop.findViewById(R.id.ok);
                                final EditText otp = pop.findViewById(R.id.otp);

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (!validatePhoneNumber()) {
                                            return;
                                        }
                                        String code = otp.getText().toString();
                                        verifyPhoneNumberWithCode(mVerificationId,code);
                                        pd.setMessage("Verifying...");
                                        pd.show();
                                    }
                                });
                                builder.setView(pop);
                                popup = builder.create();
                                popup.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
//                // The final parameter overrides the method onErrorResponse() and passes VolleyError
//                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", String.valueOf(error));
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String aadhar = aadhar_no.getText().toString();
                Map<String, String> params = new HashMap<>();
                params.put("phone_no", aadhar);
                Log.e("PARAMS", String.valueOf(params));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(obreq);
    }

    private class getData extends AsyncTask<Void,Void,String> {

        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(login.this);
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            String resp="";
            try {
                 resp =  run(getString(R.string.url)+"?action=login&uid="+username.getText().toString()+"&password="+password.getText().toString()+"&type="+type.getSelectedItem().toString());
            } catch (IOException e) {
                e.printStackTrace();
                resp = "error";
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(s);

            if(((JSONArray)jsonObject.get("data")).length()>0)
            {
                JSONArray jarray = jsonObject.getJSONArray("data");
                JSONObject obj = jarray.getJSONObject(0);
                SharedPreferences sagy = getSharedPreferences("sagy",0);
                SharedPreferences.Editor se = sagy.edit();
                se.putBoolean("officer",true);
                se.putBoolean("verifiedMP",true);
                se.putString("mp_id",obj.getString("mp_id"));
                se.putString("officer_id",username.getText().toString());
                se.apply();
                mps =  new Gson().fromJson(jsonObject.toString(),MpsModel.class);
                Intent intent = new Intent(login.this,mplogin.class);
                intent.putExtra("type",type.getSelectedItem().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(login.this,"invalid id or password",Toast.LENGTH_SHORT).show();
            }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        okhttp3.Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
