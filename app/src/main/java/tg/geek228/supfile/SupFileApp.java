package tg.geek228.supfile;

import android.app.Application;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


/**
 * Created by farouk on 29/04/2018.
 */

public class SupFileApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppEventsLogger.activateApp(this);
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
