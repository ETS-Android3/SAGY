package app.shi.com.sagy;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.shi.com.sagy.model.Mps.Datum;

/**
 * Created by Rujuu on 3/30/2018.
 */

public class MpsDetailActivity extends AppCompatActivity {
    TextView name,villagename,consti,gender,age;
    app.shi.com.sagy.model.Mps.Datum obj;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mpdetail);
        name = findViewById(R.id.name);
        villagename = findViewById(R.id.Village_tv);
        consti = findViewById(R.id.Constituency_tv);
        gender = findViewById(R.id.Gender_tv);
        age = findViewById(R.id.constituency);
        obj = (Datum) getIntent().getSerializableExtra("data");
        name.setText("Name : "+obj.getName());
        villagename.setText("Village Name : "+obj.getVillageId());
        consti.setText("Constituency : "+obj.getConstituency());
        gender.setText("Gender : "+obj.getGender());
        age.setText("Age : "+obj.getAge());


    }

    public void Click(View view) {

        EditText editText = new EditText(MpsDetailActivity.this);
        editText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        editText.setHint("Enter Message");
        new AlertDialog.Builder(MpsDetailActivity.this)
                .setView(editText)
                .setTitle("Send Feedback")

                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }

                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create()
                .show();

    }
}
