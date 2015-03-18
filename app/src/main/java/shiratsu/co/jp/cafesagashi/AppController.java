package shiratsu.co.jp.cafesagashi;

import android.app.Application;


import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

/**
 * Created by shiratsu on 15/03/16.
 */
public class AppController extends Application {


    private static AppController mInstance;

    @Override
    public void onCreate(){
        super.onCreate();
        Configuration.Builder builder = new Configuration.Builder(getBaseContext());
        builder.setCacheSize(1024*1024*4);
        builder.setDatabaseName("cafesagashi.db");
        builder.setDatabaseVersion(1);
        ActiveAndroid.initialize(builder.create(), true);

        mInstance = this;
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
