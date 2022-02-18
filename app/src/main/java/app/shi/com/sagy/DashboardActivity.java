package app.shi.com.sagy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CardView VilageCard;
    CardView MpCard;
    CardView instituteCard;
    CardView SchemeCard;
    CardView AchievementCard;
    CardView WallOfFameCard;
    CardView problem;
    private MenuItem log;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        VilageCard = (CardView) findViewById(R.id.village);
        MpCard = (CardView) findViewById(R.id.mps);
        instituteCard = (CardView) findViewById(R.id.institute);
        SchemeCard = (CardView) findViewById(R.id.scheme);
        AchievementCard = (CardView) findViewById(R.id.achievement);
        WallOfFameCard = (CardView) findViewById(R.id.WallOfFame);
        problem = (CardView) findViewById(R.id.problem);
        SharedPreferences sagy = getSharedPreferences("sagy",0);

        problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,ShowDataOfProblemStatement.class);
                startActivity(intent);
            }
        });
        VilageCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,VillageScr.class);
                startActivity(intent);
            }
        });


        MpCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,MpsActivity.class);
                startActivity(intent);
            }
        });

        instituteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,DataAnalysis.class);
                startActivity(intent);
            }
        });

        SchemeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,schemeActivity.class);
                startActivity(intent);
            }
        });

        AchievementCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,AchivementActivity.class);
                startActivity(intent);
            }
        });

        WallOfFameCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,wall_of_fame.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Intent intent =getIntent();
        if(!sagy.getBoolean("verified",false)){
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.nav_log_out).setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_complaint) {
            Intent intent = new Intent(DashboardActivity.this, ComplaintListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_help) {
            Intent intent = new Intent(DashboardActivity.this, Help_Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_aboutUs) {
            Intent intent = new Intent(DashboardActivity.this, Aboutus.class);
            startActivity(intent);


//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
        }else if(id == R.id.nav_log_out)
        {
            SharedPreferences sagy = getSharedPreferences("sagy",0);
            SharedPreferences.Editor e = sagy.edit();
            e.clear();
            e.apply();
            Intent i = new Intent(DashboardActivity.this,login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
