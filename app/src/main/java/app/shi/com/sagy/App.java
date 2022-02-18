package app.shi.com.sagy;

import android.app.Application;
import android.os.StrictMode;

/**
 * Created by Rujuu on 3/30/2018.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }
}
