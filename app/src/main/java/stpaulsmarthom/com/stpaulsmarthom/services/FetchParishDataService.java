package stpaulsmarthom.com.stpaulsmarthom.services;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;

import stpaulsmarthom.com.stpaulsmarthom.FetchParishThread;
import stpaulsmarthom.com.stpaulsmarthom.FetchThread;

/**
 * Created by moody on 31/12/17.
 */

public class FetchParishDataService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FetchParishDataService(String name) {
        super(name);
    }

    public FetchParishDataService() {
        super("");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(isConnected(this)) {
            new FetchParishThread(FetchParishDataService.this,intent.getStringExtra("user"),intent.getStringExtra("pwd")).run();
            //thread.start();

        }
    }



    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }


}
