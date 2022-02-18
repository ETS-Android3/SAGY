package app.shi.com.sagy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

public class Help_Activity extends AppCompatActivity {
FrameLayout Fram_lyout_comp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_);
        Fram_lyout_comp = (FrameLayout) findViewById(R.id.link_comp);
        Fram_lyout_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mintent = new Intent(Help_Activity.this,helpcomplaint.class);
                startActivity(mintent);
            }
        });

        Fram_lyout_comp = (FrameLayout) findViewById(R.id.link_FAQ);
        Fram_lyout_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent mintent = new Intent(Help_Activity.this,FAQ.class);
                startActivity(mintent);
            }
        });

        Fram_lyout_comp = (FrameLayout) findViewById(R.id.link_Achive);
        Fram_lyout_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mintent = new Intent(Help_Activity.this,help_achivement.class);
                startActivity(mintent);
            }
        });

        Fram_lyout_comp = (FrameLayout) findViewById(R.id.link_ContUs);
        Fram_lyout_comp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mintent = new Intent(Help_Activity.this,Help_contact_us.class);
                startActivity(mintent);
            }
        });
    }
}
