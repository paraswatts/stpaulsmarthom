package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.jpardogo.android.googleprogressbar.library.ChromeFloatingCirclesDrawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stpaulsmarthom.com.stpaulsmarthom.APIService;
import stpaulsmarthom.com.stpaulsmarthom.APIUrl;
import stpaulsmarthom.com.stpaulsmarthom.Adapters.ParishBulletinAdapter;
import stpaulsmarthom.com.stpaulsmarthom.R;

public class ResourcesActivity extends AppCompatActivity {
    ProgressBar mProgressBar;
    SharedPreferences preferences ;
    Button bt_bible_english,bt_bible_malayalam,bt_parasya,bt_kristeeya_alpha,bt_kristeeya_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#29b1a3"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#29b1a3"));
            getWindow().setNavigationBarColor(Color.parseColor("#29b1a3"));
        }

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        TextView myActionBarTitle;
        myActionBarTitle = (TextView)findViewById(R.id.myActionBarTitle);
        myActionBarTitle.setText("RESOURCES");


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#29b1a3")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("RESOURCES");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);

        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResourcesActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);                finish();
            }
        });

        setContentView(R.layout.activity_resources);
        mProgressBar = (ProgressBar)findViewById(R.id.google_progress_res);
        mProgressBar.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(this)
                .build());
        bt_bible_english = (Button)findViewById(R.id.bt_bible_english);
        bt_bible_malayalam = (Button)findViewById(R.id.bt_bible_malayalam);
        bt_parasya = (Button)findViewById(R.id.bt_parasya);
        bt_kristeeya_alpha = (Button)findViewById(R.id.bt_kristeeya_alpha);
        bt_kristeeya_number = (Button)findViewById(R.id.bt_kristeeya_number);

         preferences = getSharedPreferences("PdfDownload",Context.MODE_PRIVATE);
        if(isConnected(this)) {
            getData();
        }
        else
        {
            mProgressBar.setVisibility(View.INVISIBLE);

            bt_bible_english.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = getSharedPreferences("PdfDownload",Context.MODE_PRIVATE);
                    SharedPreferences preferences1 = getSharedPreferences("PdfDownload",Context.MODE_PRIVATE);

                    String path = preferences.getString("13","");
                    //Log.e("path",path);
                    if (!TextUtils.isEmpty(path)) {
                        String downloaded = preferences1.getString(path,"");
                        if(downloaded.equals("yes")) {
                            view( path,"English Bible");
                        }
                        else
                        {
                            Toast.makeText(ResourcesActivity.this, "Please download pdf to use offline", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(ResourcesActivity.this, "Please download pdf to use offline", Toast.LENGTH_SHORT).show();

                    }


                }
            });

            bt_bible_malayalam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = getSharedPreferences("PdfDownload",Context.MODE_PRIVATE);
                    SharedPreferences preferences1 = getSharedPreferences("PdfDownload",Context.MODE_PRIVATE);

                    String path = preferences.getString("14","");
                    //Log.e("path",path);

                    if (!TextUtils.isEmpty(path)) {
                        String downloaded = preferences1.getString(path,"");
                        if(downloaded.equals("yes")) {
                            view( path,"Malayalam Bible");
                        }
                        else
                        {
                            Toast.makeText(ResourcesActivity.this, "Please download pdf to use offline", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(ResourcesActivity.this, "Please download pdf to use offline", Toast.LENGTH_SHORT).show();

                    }

                }
            });
            bt_parasya.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = getSharedPreferences("PdfDownload",Context.MODE_PRIVATE);
                    String path = preferences.getString("15","");
                    //Log.e("path",path);
                    SharedPreferences preferences1 = getSharedPreferences("PdfDownload",Context.MODE_PRIVATE);

//                    if(!TextUtils.isEmpty(path))
//                    {
//                        view("Parasyaraadhana Kramam",path);
//
//                    }
//                    else
//                    {
//                        Toast.makeText(ResourcesActivity.this, "Please download pdf to use offline", Toast.LENGTH_SHORT).show();
//                    }

                    if (!TextUtils.isEmpty(path)) {
                        String downloaded = preferences1.getString(path,"");
                        if(downloaded.equals("yes")) {
                            view( path,"Parasyaraadhana Kramam");
                        }
                        else
                        {
                            Toast.makeText(ResourcesActivity.this, "Please download pdf to use offline", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(ResourcesActivity.this, "Please download pdf to use offline", Toast.LENGTH_SHORT).show();

                    }
                }
            });

            bt_kristeeya_alpha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = getSharedPreferences("PdfDownload",Context.MODE_PRIVATE);
                    String path = preferences.getString("16","");
                    //Log.e("path",path);
                    SharedPreferences preferences1 = getSharedPreferences("PdfDownload",Context.MODE_PRIVATE);

//                    if(!TextUtils.isEmpty(path)){
//                        view("Kristeeya Keerthanangal(Alphabetical order)",path);
//
//                    }
//                    else
//                    {
//                        Toast.makeText(ResourcesActivity.this, "Please download pdf to use offline", Toast.LENGTH_SHORT).show();
//
//                    }

                    if (!TextUtils.isEmpty(path)) {
                        String downloaded = preferences1.getString(path,"");
                        if(downloaded.equals("yes")) {
                            view(path,"Kristeeya Keerthanangal (Alphabetical order)");
                        }
                        else
                        {
                            Toast.makeText(ResourcesActivity.this, "Please download pdf to use offline", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(ResourcesActivity.this, "Please download pdf to use offline", Toast.LENGTH_SHORT).show();

                    }

                }
            });

            bt_kristeeya_number.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences = getSharedPreferences("PdfDownload",Context.MODE_PRIVATE);
                    String path = preferences.getString("17","");
                    SharedPreferences preferences1 = getSharedPreferences("PdfDownload",Context.MODE_PRIVATE);


                    if (!TextUtils.isEmpty(path)) {
                        String downloaded = preferences1.getString(path,"");
                        if(downloaded.equals("yes")) {
                            view( path,"Kristeeya Keerthanangal (Number order)");
                        }
                        else
                        {
                            Toast.makeText(ResourcesActivity.this, "Please download pdf to use offline", Toast.LENGTH_SHORT).show();
                        }

                    }
                    else
                    {
                        Toast.makeText(ResourcesActivity.this, "Please download pdf to use offline", Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    private void getData() {


        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getResources();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mProgressBar.setVisibility(View.INVISIBLE);
                //Log.d("URL", "=====" + response.raw().request().url());

                //Log.e("getting about us", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        //Log.e("Success message",jsonObject.getString("success"));

                        //Log.e("Success is" , " "+ jsonObject.get("success"));
                        final JSONArray jsonArray = jsonObject.getJSONArray("Data");

                        bt_bible_english.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (isConnected(getApplicationContext())) {
                                    try {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                        String downloaded = preferences.getString(jsonObject1.getString("file_name"),"");
                                        if(downloaded.equals("yes"))
                                        {
                                            view(jsonObject1.getString("file_name"),jsonObject1.getString("bible"));

                                        }
                                        else {
                                            getOutputMediaFile(jsonObject1.getString("file_name"), jsonObject1.getString("bible"),jsonObject1.getString("Id"));
                                            //SaveShared(jsonObject1.getString("Id"), jsonObject1.getString("file_name"));
                                        }
                                        //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://stpaulsmarthomabahrain.com/membershipform/church_admin/Resources_pdf/" +jsonObject1.getString("file_name"))));
                                    } catch (JSONException e) {

                                    } catch (Exception e) {

                                    }
                                }
                                else
                                {
                                    Toast.makeText(ResourcesActivity.this, "Please connect to internet", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                        bt_bible_malayalam.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(isConnected(getApplicationContext()))
                                {
                                try {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(1);
//                                    getOutputMediaFile(jsonObject1.getString("file_name"),jsonObject1.getString("bible"));
//                                    SaveShared(jsonObject1.getString("Id"),jsonObject1.getString("file_name"));
                                    String downloaded = preferences.getString(jsonObject1.getString("file_name"),"");
                                    if(downloaded.equals("yes"))
                                    {
                                        view(jsonObject1.getString("file_name"),jsonObject1.getString("bible"));

                                    }
                                    else {
                                        getOutputMediaFile(jsonObject1.getString("file_name"), jsonObject1.getString("bible"),jsonObject1.getString("Id"));
                                        //SaveShared(jsonObject1.getString("Id"), jsonObject1.getString("file_name"));
                                    }
                                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://stpaulsmarthomabahrain.com/membershipform/church_admin/Resources_pdf/" +jsonObject1.getString("file_name"))));
                                }
                                catch(JSONException e)
                                {

                                }
                                catch (Exception e)
                                {

                                }
                            }
                                else
                            {
                                Toast.makeText(ResourcesActivity.this, "Please connect to internet", Toast.LENGTH_SHORT).show();
                            }

                            }
                        });
                        bt_parasya.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(isConnected(getApplicationContext()))
                                {
                                try {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(2);
                                    String downloaded = preferences.getString(jsonObject1.getString("file_name"),"");
                                    if(downloaded.equals("yes"))
                                    {
                                        view(jsonObject1.getString("file_name"),jsonObject1.getString("bible"));

                                    }
                                    else {
                                        getOutputMediaFile(jsonObject1.getString("file_name"), jsonObject1.getString("bible"),jsonObject1.getString("Id"));
                                        //SaveShared(jsonObject1.getString("Id"), jsonObject1.getString("file_name"));
                                    }
                                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://stpaulsmarthomabahrain.com/membershipform/church_admin/Resources_pdf/" +jsonObject1.getString("file_name"))));
                                }
                                catch(JSONException e)
                                {

                                }
                                catch (Exception e)
                                {

                                }

                            }
                                else
                            {
                                Toast.makeText(ResourcesActivity.this, "Please connect to internet", Toast.LENGTH_SHORT).show();
                            }

                            }
                        });

                        bt_kristeeya_alpha.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(isConnected(getApplicationContext())){
                                try {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(3);
                                    String downloaded = preferences.getString(jsonObject1.getString("file_name"),"");
                                    if(downloaded.equals("yes"))
                                    {
                                        view(jsonObject1.getString("file_name"),jsonObject1.getString("bible"));

                                    }
                                    else {
                                        getOutputMediaFile(jsonObject1.getString("file_name"), "Kristheeya Keerthanangal",jsonObject1.getString("Id"));
                                        //SaveShared(jsonObject1.getString("Id"), jsonObject1.getString("file_name"));
                                    };
                                }
                                catch(JSONException e)
                                {

                                }
                                catch (Exception e)
                                {

                                }
                            }
                                else
                            {

                            }

                            }
                        });

                        bt_kristeeya_number.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(isConnected(getApplicationContext()))
                                {
                                try {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(4);
                                    String downloaded = preferences.getString(jsonObject1.getString("file_name"),"");
                                    if(downloaded.equals("yes"))
                                    {
                                        view(jsonObject1.getString("file_name"),jsonObject1.getString("bible"));

                                    }
                                    else {
                                        getOutputMediaFile(jsonObject1.getString("file_name"), "Kristheeya Keerthanangal",jsonObject1.getString("Id"));
                                        //SaveShared(jsonObject1.getString("Id"), jsonObject1.getString("file_name"));
                                    }

                                    //startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://stpaulsmarthomabahrain.com/membershipform/church_admin/Resources_pdf/" +jsonObject1.getString("file_name"))));
                                }
                                catch(JSONException e)
                                {
                                    e.printStackTrace();
                                    //Log.e("Download error","Error Jdon"+e.getMessage());
                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                    //Log.e("Download error","Error"+e.getMessage());
                                }
                            }
                                else
                            {
                                Toast.makeText(ResourcesActivity.this, "Please connect to internet", Toast.LENGTH_SHORT).show();
                            }

                            }
                        });

                    } catch (Exception e) {

                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void SaveShared( String key,String value)
    {
        //Log.e("Key Name",key + "value"+value);
        SharedPreferences preferences = getSharedPreferences("PdfDownload",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key,value);
        editor.commit();
    }

    public void view(String pdfname,String filename)
    {
        File pdfFile = new File(Environment.getExternalStorageDirectory() + "/StPaulsBahrain/" + "/Parish_Publication_PDF/"+pdfname);  // -> filename = maven.pdf
        Uri path = Uri.fromFile(pdfFile);
        //Log.e("pdf path",pdfFile.getAbsolutePath());
       //Log.e("path",pdfFile.getAbsolutePath());

        Intent intent = new Intent(this, PdfViewerActivity.class);
        intent.putExtra("pdfUrl", pdfFile.getAbsolutePath());
        intent.putExtra("pdfName",filename);
        //mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://stpaulsmarthomabahrain.com/membershipform/church_admin/publication_pdf/" +parish.getFile_name())));
        startActivity(intent);
    }


    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }


    private class DownloadFile extends AsyncTask<String, String, ArrayList<String>> {
        ProgressDialog progressDialog=null;
        private static final int  MEGABYTE = 1024 * 1024;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(ResourcesActivity.this);
            progressDialog.setMessage("Downloading PDF");
            progressDialog.setIndeterminate(false);
            progressDialog.setMax(100);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setCancelable(true);
            progressDialog.show();
            super.onPreExecute();

        }

        @Override
        protected ArrayList<String> doInBackground(String... strings) {

            String fileUrl = strings[0];   // -> http://maven.apache.org/maven-1.x/maven.pdf
            String fileName = strings[1];  // -> maven.pdf
            //Log.e("File download",fileUrl+fileName);
            File file = new File(strings[1]);

            int totalSize=0;
            try {

                URL url = new URL(fileUrl);
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                //urlConnection.setRequestMethod("GET");
                //urlConnection.setDoOutput(true);
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                totalSize = urlConnection.getContentLength();
                long total = 0;
                byte[] buffer = new byte[MEGABYTE];
                int bufferLength = 0;
                while((bufferLength = inputStream.read(buffer))>0 ){
                    total += bufferLength;
                    publishProgress(""+(int)((total*100)/totalSize));
                    fileOutputStream.write(buffer, 0, bufferLength);
                }
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<String> arrayList =  new ArrayList<>();
            arrayList.add(strings[3]);
            arrayList.add(strings[2]);
            arrayList.add(String.valueOf(totalSize));
            return arrayList;
        }
        protected void onProgressUpdate(String... progress) {
            progressDialog.setProgress(Integer.parseInt(progress[0]));
        }


        @Override
        protected void onPostExecute(ArrayList<String> aVoid) {
            File sampleDir = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain"+"/"+"Parish_Publication_PDF/"+aVoid.get(0));
            int size =  Integer.parseInt(String.valueOf(sampleDir.length()));
            //Log.e("Size of file",size +"ds" + aVoid.get(2));
            progressDialog.dismiss();
            //Log.e("Files download",""+ aVoid.get(0)+aVoid.get(1));
            if(String.valueOf(size).equals(aVoid.get(2))) {
                view(aVoid.get(0),aVoid.get(1));
                SaveShared(aVoid.get(0), "yes");
            }else{
                SaveShared(aVoid.get(0), "no");
            }
            super.onPostExecute(aVoid);

        }
    }

    private void getOutputMediaFile(String pdfFile,String fileName,String Id) {

        File sampleDir = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain"+"/"+"Parish_Publication_PDF");
        if (!sampleDir.exists()) {
            if (!sampleDir.mkdirs()) {
                return;
            }
        }
        File mediaFile;
        String mPdfName = pdfFile;
        mediaFile = new File(sampleDir.getPath() + File.separator + mPdfName);
//        if(mediaFile.exists())
//        {
//            view(pdfFile,fileName);
//
//        }
//        else
//        {
           SaveShared(Id, pdfFile);

            new DownloadFile().execute("http://stpaulsmarthomabahrain.com/membershipform/church_admin/Resources_pdf/"+pdfFile, mediaFile.getAbsolutePath(),fileName,pdfFile);

       // }
    }

}
