package stpaulsmarthom.com.stpaulsmarthom;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Paras-Android on 04-01-2018.
 */

public class DownloadThread implements Runnable {
    String imagePath,imageUrl;
    int quality;
    public DownloadThread(String imageUrl, String imagePath,int quality) {
        this.imageUrl = imageUrl;
        this.imagePath = imagePath;
        this.quality = quality;
    }
    @Override
    public void run() {
        File file = new File(imagePath);
        getBitmapFromURL(imageUrl, file,quality);
    }

    public void getBitmapFromURL(String strURL, File path,int quality) {
        try {
            Log.e("Str Url",strURL);

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



    private void storeImage(Bitmap image, File path, int quality) {
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
            image.compress(Bitmap.CompressFormat.JPEG, 10, fos);
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
