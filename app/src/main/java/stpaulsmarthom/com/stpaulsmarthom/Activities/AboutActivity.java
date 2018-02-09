package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jpardogo.android.googleprogressbar.library.ChromeFloatingCirclesDrawable;
import com.jpardogo.android.googleprogressbar.library.FoldingCirclesDrawable;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import stpaulsmarthom.com.stpaulsmarthom.APIService;
import stpaulsmarthom.com.stpaulsmarthom.APIUrl;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbContract;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbHelper;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.AboutUsModel;
import stpaulsmarthom.com.stpaulsmarthom.NetworkConnectivityChange;
import stpaulsmarthom.com.stpaulsmarthom.R;


public class AboutActivity extends AppCompatActivity {

    TextView myActionBarTitle;
    TextView tv_english,tv_malayalam;
    WebView tv_about_us;
    AboutUsModel aboutUsModel;
    DbHelper dbHelper;
    ProgressBar mProgressBar;
    ImageView iv_about_us;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#367eae"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#367eae"));
            getWindow().setNavigationBarColor(Color.parseColor("#367eae"));

        }
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);
        mProgressBar = (ProgressBar)findViewById(R.id.google_progress);
        mProgressBar.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(this)
                .build());
        myActionBarTitle = (TextView)findViewById(R.id.myActionBarTitle);
        myActionBarTitle.setText("ABOUT US");
        iv_about_us = findViewById(R.id.iv_about_us);
        tv_english = findViewById(R.id.tv_english);
        tv_malayalam = findViewById(R.id.tv_malayalam);


        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AboutActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();
            }
        });
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#367eae")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("ABOUT US");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
//        registerReceiver(new NetworkConnectivityChange(),new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        tv_about_us = findViewById(R.id.tv_about_us);


        dbHelper = new DbHelper(this);
        aboutUsModel = new AboutUsModel();
        mSeekBar =  findViewById(R.id.seek_bar);
        mSeekBar.setProgress(tv_about_us.getSettings().getTextZoom()/25);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_about_us.getSettings().setTextZoom(progress*25);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        tv_about_us.getSettings().setJavaScriptEnabled(true);
        tv_about_us.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        tv_about_us.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        tv_about_us.getSettings().setAppCacheEnabled(false);
        tv_about_us.getSettings().setBlockNetworkImage(true);
        tv_about_us.getSettings().setLoadsImagesAutomatically(true);
        tv_about_us.getSettings().setNeedInitialFocus(true);
        tv_about_us.getSettings().setGeolocationEnabled(false);
        tv_about_us.getSettings().setSaveFormData(false);
        if (isConnected(getApplicationContext())) {
            getData();
        }
        else
        {
            try {
                mProgressBar.setVisibility(View.INVISIBLE);

                DbHelper userDbHelper = new DbHelper(this);
                SQLiteDatabase sqLiteDatabase = userDbHelper.getWritableDatabase();

                Cursor query = sqLiteDatabase.query(DbContract.DbEntry.TABLE_ABOUT_US, //Table to query
                        null, //columns to return
                        null, //columns for the WHERE clause
                        null, //filter by row groups
                        null, null, null);
                int count = query.getCount();
                //Log.e ("Db Count",""+count);
                while (query.moveToNext()) {
                    String aboutUsContent = query.getString(1);
                    File imgFile = new File(query.getString(2));
                    if(imgFile.getAbsolutePath().toLowerCase().endsWith(".jpg")||imgFile.getAbsolutePath().toLowerCase().endsWith(".jpeg")) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        iv_about_us.setImageBitmap(myBitmap);
                    }
                    else {
                        iv_about_us.setImageDrawable(getResources().getDrawable(R.drawable.sample));


                    }
                    tv_english.setText(query.getString(3));
                    tv_malayalam.setText(query.getString(4));
                    //tv_about_us.setText(aboutUsContent);
                    String text;
                    text = "<html><body  style=\"text-align:justify;\">";
                    text += aboutUsContent ;
                    text += "</body></html>";
                    tv_about_us.loadData(text,"text/html", "utf-8");

                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }


    private void getData() {
//
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//               progressDialog.show();
//
//



        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getAboutUs();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mProgressBar.setVisibility(View.INVISIBLE);
                Log.d("URL", "=====" + response.raw().request().url());

                //Log.e("getting about us", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {

                       // dbHelper.deleteTable(DbContract.DbEntry.TABLE_ABOUT_US);

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        //Log.e("Success message",jsonObject.getString("success"));

                        //Log.e("Success is" , " "+ jsonObject.get("success"));
                        if(jsonObject.has("Data")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("Data");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            //tv_about_us.setText(jsonObject1.getString("content"));
                            String text;
                            text = "<html><body  style=\"text-align:justify;\">";
                            text += jsonObject1.getString("content") ;
                            text += "</body></html>";
                            tv_about_us.loadData(text,"text/html", "utf-8");
                            aboutUsModel.setAboutUsContent(jsonObject1.getString("content"));
                            tv_english.setText(jsonObject1.getString("timing_english"));
                            tv_malayalam.setText(jsonObject1.getString("timing_malayalam"));
                            //dbHelper.addAboutUsContent(aboutUsModel);
                            Picasso picasso = Picasso.with(getApplicationContext());
                            picasso.setIndicatorsEnabled(false);
                            picasso.load("http://stpaulsmarthomabahrain.com/membershipform/church_admin/about_us/" + jsonObject1.getString("images"))
                                    .placeholder(R.drawable.sample)
                                    .error(R.drawable.sample)
                                    .into(iv_about_us, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });
                        }
                        else
                        {
                            Toast.makeText(AboutActivity.this, "No data found", Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {

                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }


}
