package stpaulsmarthom.com.stpaulsmarthom.services;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stpaulsmarthom.com.stpaulsmarthom.APIService;
import stpaulsmarthom.com.stpaulsmarthom.APIUrl;
import stpaulsmarthom.com.stpaulsmarthom.Activities.HomeActivity;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbContract;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbHelper;
import stpaulsmarthom.com.stpaulsmarthom.DownloadThread;
import stpaulsmarthom.com.stpaulsmarthom.FetchThread;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.AboutUsModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.BishopModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ChildModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ContactUsModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.KaisthanaSamithiModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.KaisthanaSectionModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgDetailModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgSectionModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ParishBulletinModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ParishMemberModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.PrivacyModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.VicarModel;

/**
 * Created by moody on 31/12/17.
 */

public class FetchDataService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FetchDataService(String name) {
        super(name);
    }

    public FetchDataService() {
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
            Thread thread = new Thread(new FetchThread(FetchDataService.this,intent.getStringExtra("user"),intent.getStringExtra("pwd")));
            thread.start();

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
