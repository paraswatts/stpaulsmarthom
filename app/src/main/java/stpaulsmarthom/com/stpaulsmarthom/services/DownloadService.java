package stpaulsmarthom.com.stpaulsmarthom.services;

import android.app.DownloadManager;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import stpaulsmarthom.com.stpaulsmarthom.DownloadThread;
import stpaulsmarthom.com.stpaulsmarthom.FetchThread;

/**
 * Created by moody on 30/12/17.
 */

public class DownloadService extends IntentService {
    DownloadManager downloadManager;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadService(String name) {
        super(name);
    }
    public DownloadService() {
        super("");
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        File file = new File(intent.getStringExtra("imagePath"));
        getBitmapFromURL(intent.getStringExtra("imageUrl"), file,intent.getIntExtra("quality",0));

        //new DownloadThread(intent.getStringExtra("imageUrl"),file.getAbsolutePath(),10).run();

    }


    public void getBitmapFromURL(String strURL, File path,int quality) {
        try {
            //Log.e("Str Url",strURL);

            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            storeImage(myBitmap, path,quality);
//            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(strURL));
//            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
//            request.setAllowedOverRoaming(true);
//            request.setVisibleInDownloadsUi(false);
//            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN);
//
//            //request.setDestinationInExternalFilesDir(getApplicationContext(),Environment.getExternalStorageDirectory(),path.getAbsolutePath());
//            request.setDestinationUri(Uri.fromFile(path));
//            request.allowScanningByMediaScanner();
            //request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            //downloadManager.enqueue(request);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }



    private void storeImage(Bitmap image, File path,int quality) {
        //Log.e("Saving Image", "");

        File pictureFile = path;
        //Log.e("FilePath", pictureFile.getAbsolutePath());


        if (pictureFile == null) {
            Log.d("",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG,quality , fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            //Log.d("", "File not found: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            //Log.d("", "Error accessing file: " + e.getMessage());
        }
    }
}

