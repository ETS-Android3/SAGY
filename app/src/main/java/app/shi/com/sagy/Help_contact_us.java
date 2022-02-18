package app.shi.com.sagy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class Help_contact_us extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_contact_us);
        Toolbar toolbar = findViewById(R.id.toolbarCus);
        setSupportActionBar(toolbar);
    }
}
