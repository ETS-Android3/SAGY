package app.shi.com.sagy;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.volley.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


import app.shi.com.sagy.model.no_adapter;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.Request;

public class nodaloff_list extends AppCompatActivity {

    no_adapter no_adapter;
    ListView listView;
    ArrayList<info> obj = new ArrayList<>();
    CustomAdapter adapter;
    public Dialog popup;
    public String name;
    public TextView nameTextView;
    public TextView villageTextView;
    public TextView contactTextView;


    public class info {
        String name;
        String villagename;

        public info(String name, String villagename) {
            this.name = name;
            this.villagename = villagename;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nodaloff_list);
        listView = findViewById(R.id.listview_no);
        adapter = new CustomAdapter(nodaloff_list.this, obj);
        new call_service().execute();
        popup = new Dialog(nodaloff_list.this);
        popup.setContentView(R.layout.details_nodaloff);
        ImageView close_img = (ImageView) popup.findViewById(R.id.close_btn);
        close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popup.dismiss();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                name = obj.get(i).name;


                nameTextView = (TextView) popup.findViewById(R.id.no_name_tv);
                villageTextView = (TextView) popup.findViewById(R.id.no_va_tv);
                contactTextView = (TextView) popup.findViewById(R.id.no_contact_tv);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(popup.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.gravity = Gravity.CENTER;

                popup.getWindow().setAttributes(lp);

                popup.show();

                new getNodalDetails().execute();
            }
        });


//        class getNodalDetaills extends AsyncTask<Void, Void, String> {
//
//                    String nname;
//                    String adopted_village;
//                    String contect;
//
//                    @Override
//                    protected String doInBackground(Void... voids) {
//                        try {
//                            String details = run("http://phpstack-165541-479108.cloudwaysapps.com/api/webservice.php?action=get_nodel_popup&n_name=" + name);
//
//                            JSONObject mobjj=new JSONObject(details);
//                            JSONArray myarray= mobjj.getJSONArray("data");
//
//                                JSONObject jobj=myarray.getJSONObject(0);
//
//                                  nname=jobj.getString("Name_of_Nodal_Officer");
//                                 adopted_village=jobj.getString("Email_of_Nodal_Officer");
//                                 contect=jobj.getString("Gram_Panchayats");
//
//                                return  "sucess";
//
//                            } catch (JSONException e1) {
//                            e1.printStackTrace();
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        }
//
//
//
//                        return "error";
//                    }
//
//                    @Override
//                    protected void onPostExecute(String s) {
//                        super.onPostExecute(s);
//
//                        TextView nameTextView = (TextView) popup.findViewById(R.id.no_name_TV);
//                        TextView villageTextView = (TextView) popup.findViewById(R.id.no_va_tv);
//                        TextView contactTextView = (TextView) popup.findViewById(R.id.no_contact_tv);
//
//                        if(s.equals("sucesss")){
//                            nameTextView.setText(nname);
//                            villageTextView.setText(adopted_village);
//                            contactTextView.setText(contect);
//                            popup.setContentView(R.layout.details_nodaloff);
//                        }
//                    }
//
//                }

    }


    private class CustomAdapter extends BaseAdapter {
        Context context;
        ArrayList<nodaloff_list.info> obj;

        public CustomAdapter(Context context, ArrayList<nodaloff_list.info> obj) {
            this.context = context;
            this.obj = obj;
        }

        @Override
        public int getCount() {
            return obj.size();
        }

        @Override
        public Object getItem(int i) {
            return obj.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.single_nodaloff, null);
            TextView noname = view.findViewById(R.id.no_name_TV);
            TextView vname = view.findViewById(R.id.no_village_name_TV);

            noname.setText(obj.get(i).name);
            vname.setText(obj.get(i).villagename);

            return view;
        }
    }

    public class call_service extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(nodaloff_list.this);
            dialog.setTitle("fetching...");
            dialog.setMessage("please wait...");
            dialog.show();
        }

        @Override
        protected String doInBackground(Void... voids) {


            try {
                String op = run("http://phpstack-165541-479108.cloudwaysapps.com/api/webservice.php?action=get_nodel_officer");

                obj.clear();

                JSONObject mainoobj = new JSONObject(op);
                JSONArray array = mainoobj.getJSONArray("data");
                Log.d("message", "inside dfor doin background");

                for (int i = 0; i <= array.length(); i++) {
                    JSONObject jobj = array.getJSONObject(i);
                    String noname = jobj.getString("Name_of_Nodal_Officer");
                    String vname = jobj.getString("Gram_Panchayats");

                    info myinfo = new info(noname, vname);
                    obj.add(myinfo);

                    Log.d("message", "inside dfor doin background");

                }
                return "sucess";
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("message1", "inside exception");
                return "error";

            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            listView.setAdapter(adapter);
            if (s.equals("error")) {
                Toast.makeText(nodaloff_list.this, "Sorry unable to fetch", Toast.LENGTH_LONG).show();

            }

        }
    }

    private class getNodalDetails extends AsyncTask<Void, Void, String>{

        String nname;
        String adopted_village;
        String contect;

        @Override
        protected String doInBackground(Void... voids) {
            try {
                String details = run("http://phpstack-165541-479108.cloudwaysapps.com/api/webservice.php?action=get_nodel_popup&n_name=" + name);

                JSONObject mobjj=new JSONObject(details);
                JSONArray myarray= mobjj.getJSONArray("data");

                JSONObject jobj=myarray.getJSONObject(0);

                nname=jobj.getString("Name_of_Nodal_Officer");
                adopted_village=jobj.getString("Email_of_Nodal_Officer");
                contect=jobj.getString("Gram_Panchayats");

                Log.d("message",contect);
                return  "sucess";
            } catch (JSONException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            return "error";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //if(s.equals("sucesss")){

            Log.d("tag","onpostexecute sucess");
                nameTextView.setText(nname);
                villageTextView.setText(adopted_village);
                contactTextView.setText(contect);

//            }
        }


    }
}