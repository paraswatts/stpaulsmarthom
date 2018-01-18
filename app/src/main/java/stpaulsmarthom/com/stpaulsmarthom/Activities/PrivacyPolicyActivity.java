package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.google.gson.JsonElement;
import com.jpardogo.android.googleprogressbar.library.ChromeFloatingCirclesDrawable;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stpaulsmarthom.com.stpaulsmarthom.APIService;
import stpaulsmarthom.com.stpaulsmarthom.APIUrl;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbContract;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbHelper;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.AboutUsModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.PrivacyModel;
import stpaulsmarthom.com.stpaulsmarthom.R;

public class PrivacyPolicyActivity extends AppCompatActivity {
    TextView myActionBarTitle;
    WebView tv_about_us;
    PrivacyModel aboutUsModel;
    DbHelper dbHelper;
    ProgressBar mProgressBar;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#eb4142"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#eb4142"));
            getWindow().setNavigationBarColor(Color.parseColor("#eb4142"));
        }
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        TextView myActionBarTitle;
        myActionBarTitle = (TextView) findViewById(R.id.myActionBarTitle);
        myActionBarTitle.setText("PRIVACY POLICY");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eb4142")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrivacyPolicyActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);                finish();
            }
        });

        //getSupportActionBar().setTitle("PRIVACY POLICY");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);

        mProgressBar = (ProgressBar) findViewById(R.id.google_progress_privacy);
        mProgressBar.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(this)
                .build());


        tv_about_us =  findViewById(R.id.tv_privacy);
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
        tv_about_us.getSettings().setJavaScriptEnabled(true);
        tv_about_us.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        tv_about_us.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        tv_about_us.getSettings().setAppCacheEnabled(false);
        tv_about_us.getSettings().setBlockNetworkImage(true);
        tv_about_us.getSettings().setLoadsImagesAutomatically(true);
        tv_about_us.getSettings().setNeedInitialFocus(true);
        tv_about_us.getSettings().setGeolocationEnabled(false);
        tv_about_us.getSettings().setSaveFormData(false);
        dbHelper = new DbHelper(this);
        if (isConnected(getApplicationContext())) {
            getData();
        } else {
            try {
                mProgressBar.setVisibility(View.INVISIBLE);

                DbHelper userDbHelper = new DbHelper(this);
                SQLiteDatabase sqLiteDatabase = userDbHelper.getWritableDatabase();

                Cursor query = sqLiteDatabase.query(DbContract.DbEntry.TABLE_PRIVACY, //Table to query
                        null, //columns to return
                        null, //columns for the WHERE clause
                        null, //filter by row groups
                        null, null, null);
                int count = query.getCount();
                //Log.e("Db Count", "" + count);
                while (query.moveToNext()) {
                    String aboutUsContent = query.getString(1);

                    String text;
                    text = "<html><body  style=\"text-align:justify;\">";
                    text += aboutUsContent ;
                    text += "</body></html>";
                    tv_about_us.loadData(text,"text/html", "utf-8");

                    //tv_about_us.setText(aboutUsContent);

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

        Call<JsonElement> call = service.getPrivacy();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
       mProgressBar.setVisibility(View.INVISIBLE);
                //Log.d("URL", "=====" + response.raw().request().url());

                //Log.e("getting about us", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {

                        // dbHelper.deleteTable(DbContract.DbEntry.TABLE_ABOUT_US);

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        if(jsonObject.has("Data")) {
                            //Log.e("Success message",jsonObject.getString("success"));

                            // Log.e("Success is" , " "+ jsonObject.get("success"));
                            JSONArray jsonArray = jsonObject.getJSONArray("Data");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                           // tv_about_us.setText(jsonObject1.getString("phone_Policy"));
                            String text;
                            text = "<html><body  style=\"text-align:justify;\">";
                            text += jsonObject1.getString("phone_Policy") ;
                            text += "</body></html>";
                            tv_about_us.loadData(text,"text/html", "utf-8");
                            aboutUsModel.setAboutUsContent(jsonObject1.getString("phone_Policy"));
                            //dbHelper.addAboutUsContent(aboutUsModel);
                        }
                        else
                        {
                            Toast.makeText(PrivacyPolicyActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }
}
