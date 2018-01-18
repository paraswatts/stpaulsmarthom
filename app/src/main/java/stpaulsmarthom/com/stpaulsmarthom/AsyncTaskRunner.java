package stpaulsmarthom.com.stpaulsmarthom;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Paras-Android on 26-12-2017.
 */

public class AsyncTaskRunner extends AsyncTask<String, Void, Void> {

    private String resp;
    ProgressDialog progressDialog;

    @Override
    protected Void doInBackground(String... params) {
        try {
            File file = new File(params[1]);
            getBitmapFromURL(params[0], file);

        }
        catch (Exception e) {
            e.printStackTrace();
            resp = e.getMessage();
        }
        return null;
    }

    public void getBitmapFromURL(String strURL, File path) {
        try {
           // Log.e("Str Url",strURL);

            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            storeImage(myBitmap, path);


        } catch (IOException e) {
            e.printStackTrace();
            //Log.e("Exception","dasd"+e.getMessage());
        }
    }

    private void storeImage(Bitmap image, File path) {
        //Log.e("Saving Image", "");

        File pictureFile = path;
       // Log.e("FilePath", pictureFile.getAbsolutePath());


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
            Log.d("", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("", "Error accessing file: " + e.getMessage());
        }
    }



}