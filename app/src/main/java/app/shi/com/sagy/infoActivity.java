package app.shi.com.sagy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import app.shi.com.sagy.model.Village.Datum;

public class infoActivity extends AppCompatActivity {
    TextView mp,pincode,population,male,female,area,school_children,school_male,school_female,hospital;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Datum model = (Datum) getIntent().getSerializableExtra("data");

        mp = findViewById(R.id.mp);
        pincode = findViewById(R.id.pincode);
        population = findViewById(R.id.population);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
       /* area = findViewById(R.id.area);*/
        school_children = findViewById(R.id.school_children);
        school_male = findViewById(R.id.school_male);
        school_female = findViewById(R.id.school_female);
        hospital = findViewById(R.id.hospital);

        /*area.setText("Area : "+ model.getArea());*/
        female.setText("Female : "+ model.getFemale());
        male.setText("Male : "+ model.getMale());
        hospital.setText("Hospitals : "+ model.getHospital());
        /*textView5.setText("Km.Road : "+ model.getKmRoad());*/
        pincode.setText("Pincode : "+ model.getPinCode());
        mp.setText("MP : "+ model.getName());
        population.setText("Population : "+ model.getPopulation());
        school_children.setText("School Childeren : "+ model.getSchoolChildren());
        school_male.setText("School Male : "+ model.getSchoolMale());
        school_female.setText("School Female : "+model.getSchoolFemale());
        /*textView10.setText("VDP link : "+ model.getVdpLink());*/

    }
}
