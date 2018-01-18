package stpaulsmarthom.com.stpaulsmarthom;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Config;
import android.util.Log;

/**
 * Created by Paras-Android on 29-12-2017.
 */

public class NetworkConnectivityChange extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(isConnected(context)) {

        }
        else
        {
            // Data you need to pass to activity

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
