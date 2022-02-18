package app.shi.com.sagy;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefs {

    public static final String MyPREFERENCES = "YummyProject" ;
    public static String apikey="Apikey";
    public static String email="Email";
    public static String firebasetoken="firebasetoken";
    public static String customer_id="c_id";

    public static String devid="DevID";
    public static String logintype="logintype";
    public static String lastname = "lastname";
    public static String firstname = "firstname";

    public static String getEmail(Context c1) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ans =sharedpreferences.getString(email,"no");
        return  ans;
    }
    public static void setEmail(Context c1,String value) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(email, value);
        editor.apply();
    }

    public static String getfirebasetoken(Context c1) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ans =sharedpreferences.getString(firebasetoken,"no");
        return  ans;
    }
    public static void setfirebasetoken(Context c1,String value) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(firebasetoken, value);
        editor.apply();
    }

    public static String getcustomer_id(Context c1) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ans =sharedpreferences.getString(customer_id,"no");
        return  ans;
    }
    public static void setcustomer_id(Context c1,String value) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(customer_id, value);
        editor.apply();
    }

    public static String getApikey(Context c1) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ans =sharedpreferences.getString(apikey,"no");
        return  ans;
    }
    public static void setApikey(Context c1,String value) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(apikey, value);
        editor.apply();
    }

    public static String getfirstname(Context c1) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ans =sharedpreferences.getString(firstname,"no");
        return  ans;
    }
    public static void setfirstname(Context c1,String value) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(firstname, value);
        editor.apply();
    }



    public static void clearAll(Context c1)
    {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.apply();
    }
    public static void clearValue(Context c1,String name) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(name);
        editor.apply();
    }

    public static String getDevid(Context c1) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ans =sharedpreferences.getString(devid,"no");
        return  ans;
    }
    public static void setDevid(Context c1,String value) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(devid, value);
        editor.apply();
    }
    public static String getlogintype(Context c1) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ans =sharedpreferences.getString(logintype,"no");
        return  ans;
    }
    public static void setlogintype(Context c1,String value) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(logintype, value);
        editor.apply();
    }

    public static String getlastname(Context c1) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String ans =sharedpreferences.getString(lastname,"no");
        return  ans;
    }
    public static void setlastname(Context c1,String value) {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(lastname, value);
        editor.apply();
    }

    public static void clearall(Context c1)
    {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();

    }

    public static void clear(String name, Context c1)
    {
        SharedPreferences sharedpreferences = c1.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.remove(name);
        editor.commit();

    }

}
