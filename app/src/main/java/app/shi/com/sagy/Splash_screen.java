package app.shi.com.sagy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash_screen extends AppCompatActivity {
    private ImageView mImageViewCloud;
    private ImageView mImageViewLogo;
    private ImageView mImageViewBottom;
    private Animation AnimBottom;
    private Animation AnimTop;
    private final int DURATION = 3500;
    private Thread mSplashThread;
    SharedPreferences sagy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        MultiDex.install(this);
        setContentView(R.layout.activity_splash_screen);
        sagy = getSharedPreferences("sagy",0);
//        Animation
        DoAnimation();

    }

    private void DoAnimation() {

        mImageViewCloud = (ImageView) findViewById(R.id.clouds);
        mImageViewLogo = (ImageView) findViewById(R.id.logo);
        AnimBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);

        mImageViewBottom = (ImageView) findViewById(R.id.bottom_bc);
        AnimTop = AnimationUtils.loadAnimation(this, R.anim.fromtop);


        mImageViewBottom.setAnimation(AnimBottom);
        mImageViewCloud.setAnimation(AnimTop);
        mImageViewLogo.setAnimation(AnimTop);


        mSplashThread = new Thread() {

            @Override
            public void run() {
                synchronized (this) {
                    try {
                        wait(DURATION);
                    } catch (InterruptedException e) {
                    } finally {
                        finish();
                        if(!sagy.getBoolean("verified",false) || !sagy.getBoolean("verifiedMP",false))
                        {
                            Intent mIntent = new Intent(Splash_screen.this, login.class);
                            startActivity(mIntent);
                        }else if(sagy.getBoolean("verified",false)){
                            Intent i = new Intent(Splash_screen.this,DashboardActivity.class);
                            i.putExtra("user","verified");
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }else if(sagy.getBoolean("verifiedMP",false))
                        {
                            Intent i = new Intent(Splash_screen.this,mplogin.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                        }
                    }
                }
            }

        };
        mSplashThread.start();
    }

}
