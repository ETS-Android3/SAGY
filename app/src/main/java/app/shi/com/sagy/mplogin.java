package app.shi.com.sagy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;

import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class mplogin extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private ImageButton mp_complain;
    private ImageButton mp_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mplogin);
       /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(mplogin.this);
*/
        mp_feedback = findViewById(R.id.ib2);
        mp_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mplogin.this,FeedBackListActivity.class));
            }
        });
        mp_complain= findViewById(R.id.mp_complaint);
        mp_complain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mplogin.this,ComplaintListActivity.class);
                intent.putExtra("type",getIntent().getStringExtra("type"));
                startActivity(new Intent(mplogin.this,ComplaintListActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {

        SharedPreferences sagy = getSharedPreferences("sagy",0);
        SharedPreferences.Editor se = sagy.edit();
        se.clear();
        se.apply();
        super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        return false;
    }
}
