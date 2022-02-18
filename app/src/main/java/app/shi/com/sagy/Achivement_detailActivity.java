package app.shi.com.sagy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.lespinside.simplepanorama.view.SphericalView;
import com.panoramagl.utils.PLUtils;

public class Achivement_detailActivity extends AppCompatActivity {
    SphericalView sphericalView;
    TextView descTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achivement_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        descTextView = (TextView) findViewById(R.id.desc);
        Intent intent = getIntent();
        if(intent!=null){
            descTextView.setText(intent.getStringExtra("desc"));
        }

        sphericalView = (SphericalView) findViewById(R.id.spherical_view);
        sphericalView.setPanorama(PLUtils.getBitmap(Achivement_detailActivity.this, R.raw.ridge), false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sphericalView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        sphericalView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sphericalView.onResume();
    }
}
