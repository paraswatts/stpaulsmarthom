package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.jpardogo.android.googleprogressbar.library.ChromeFloatingCirclesDrawable;
import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stpaulsmarthom.com.stpaulsmarthom.APIService;
import stpaulsmarthom.com.stpaulsmarthom.APIUrl;
import stpaulsmarthom.com.stpaulsmarthom.NetworkConnectivityChange;
import stpaulsmarthom.com.stpaulsmarthom.R;

import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_IMAGE;

public class YearPlannerActivity extends AppCompatActivity {
    ProgressBar mProgressBar,progress_year;

    ZoomageView iv_year_planner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_planner); if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#7ecce2"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#7ecce2"));
            getWindow().setNavigationBarColor(Color.parseColor("#7ecce2"));
        }


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        TextView myActionBarTitle;
        myActionBarTitle = findViewById(R.id.myActionBarTitle);
        myActionBarTitle.setText("PUBLICATIONS");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7ecce2")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("PUBLICATIONS");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(YearPlannerActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();
            }
        });

        progress_year = findViewById(R.id.progress_year);
        //registerReceiver(new NetworkConnectivityChange(),new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        iv_year_planner = findViewById(R.id.iv_year_planner);
        mProgressBar = findViewById(R.id.google_progress_year);
        mProgressBar.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(this)
                .build());
        SharedPreferences preferences = getSharedPreferences("Year",Context.MODE_PRIVATE);
        String path = preferences.getString("YearPlanner","");


        Log.e("pdf path",""+path);
        if (isConnected(this)) {
            getData();
        }
        else
        {
            mProgressBar.setVisibility(View.GONE);

            if(!TextUtils.isEmpty(path))
            {
                File imgFile = new  File(path);
                Log.e("Image path",""+imgFile.getAbsolutePath());
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                iv_year_planner.setImageBitmap(myBitmap);

            }
            else{
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

        Call<JsonElement> call = service.getYearPlanner();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mProgressBar.setVisibility(View.INVISIBLE);
                //Log.d("URL", "=====" + response.raw().request().url());

                //Log.e("getting about us", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {

                        // dbHelper.deleteTable(DbContract.DbEntry.TABLE_ABOUT_US);
                        progress_year.setVisibility(View.VISIBLE);
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        //("Success message",jsonObject.getString("success"));

                        //Log.e("Success is" , " "+ jsonObject.get("success"));
                        if(jsonObject.has("Data")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("Data");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            //("Image Path","http://stpaulsmarthomabahrain.com/membershipform/church_admin/Year_Images/"+jsonObject1.getString("images"));
                            Picasso picasso = Picasso.with(YearPlannerActivity.this);
                            picasso.setIndicatorsEnabled(false);
                            picasso
                                    .load("http://stpaulsmarthomabahrain.com/membershipform/church_admin/Year_Images/" + jsonObject1.getString("images"))
                                    .placeholder(R.drawable.sample)
                                    .error(R.drawable.sample)
                                    .into(iv_year_planner, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {
                                            progress_year.setVisibility(View.GONE);
                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });
                            //dbHelper.addAboutUsContent(aboutUsModel);
                        }
                        else
                        {
                            Toast.makeText(YearPlannerActivity.this, "No data found", Toast.LENGTH_SHORT).show();
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
