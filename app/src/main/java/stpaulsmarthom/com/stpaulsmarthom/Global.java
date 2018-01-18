package stpaulsmarthom.com.stpaulsmarthom;

import android.app.Application;

import com.squareup.picasso.Picasso;

/**
 * Created by Paras-Android on 05-01-2018.
 */
import com.jakewharton.picasso.OkHttp3Downloader;

public class Global extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

    }
}