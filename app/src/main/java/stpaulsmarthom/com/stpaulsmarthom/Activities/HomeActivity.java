package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;


import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stpaulsmarthom.com.stpaulsmarthom.APIService;
import stpaulsmarthom.com.stpaulsmarthom.APIUrl;
import stpaulsmarthom.com.stpaulsmarthom.FetchThread;
import stpaulsmarthom.com.stpaulsmarthom.services.FetchDataService;

import stpaulsmarthom.com.stpaulsmarthom.R;

public class HomeActivity extends AppCompatActivity  implements View.OnClickListener{
    RelativeLayout rl_about_us;
    RelativeLayout rl_our_bishop;
    RelativeLayout rl_our_vicar;
    RelativeLayout rl_kaisthana;
    RelativeLayout rl_organisation;
    RelativeLayout rl_edirectory;
    RelativeLayout rl_publications;
    RelativeLayout rl_resources;
    RelativeLayout rl_contact_us;
    RelativeLayout rl_about;
    RelativeLayout rl_dir;
    RelativeLayout rl_privacy;
   
    static double lat,lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#367eae"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#367eae"));
            getWindow().setNavigationBarColor(Color.parseColor("#367eae"));

        }
        getSupportActionBar().hide();

        rl_about_us = findViewById(R.id.rl_about_us);
        rl_our_bishop = findViewById(R.id.rl_our_bishop);
        rl_our_vicar = findViewById(R.id.rl_our_vicar);
        rl_kaisthana = findViewById(R.id.rl_kaisthana);
        rl_organisation = findViewById(R.id.rl_organisation);
        rl_edirectory = findViewById(R.id.rl_edirectory);
        rl_publications = findViewById(R.id.rl_publications);
        rl_resources = findViewById(R.id.rl_resources);
        rl_contact_us = findViewById(R.id.rl_contact_us);
        rl_about = findViewById(R.id.rl_about);
        rl_dir = findViewById(R.id.rl_dir);
        rl_privacy = findViewById(R.id.rl_privacy);


        rl_about_us.setOnClickListener(this);
        rl_our_bishop.setOnClickListener(this);
        rl_our_vicar.setOnClickListener(this);
        rl_kaisthana.setOnClickListener(this);
        rl_organisation.setOnClickListener(this);
        rl_edirectory.setOnClickListener(this);
        rl_publications.setOnClickListener(this);
        rl_resources.setOnClickListener(this);
        rl_contact_us.setOnClickListener(this);
        rl_about.setOnClickListener(this);
        rl_dir.setOnClickListener(this);
        rl_privacy.setOnClickListener(this);
        getData();
//        FetchThread fetchThread = new  FetchThread(HomeActivity.this);
//        fetchThread.run();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        HomeActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }
    public void onClick(View v) {

        if( v.getId() == R.id.rl_about_us)
        {
//            Fragment fragment = null;
//            fragment = new HomeFragment();
//            if (fragment != null) {
//                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
//                ft.replace(R.id.content_frame, fragment);
//                ft.commit();
//            }
            startActivity(new Intent(HomeActivity.this,AboutActivity.class));
        }
        if( v.getId() == R.id.rl_our_vicar) {
            //if(isConnected(this)) {
            // Log.e("Online","Bishop");
            startActivity(new Intent(HomeActivity.this, OurVicarActivity.class));
            // }
            // else
            //{
            //      startActivity(new Intent(HomeActivity.this, OurVicarOfflineActivity.class));

            //  }        }
        }
        if( v.getId() == R.id.rl_our_bishop)
        {
            // Log.e("Online","Bishop");

            //if(isConnected(this)) {
            //   Log.e("Online","Bishop");
            startActivity(new Intent(HomeActivity.this, OurBishopsActivity.class));
            //}
            //  else
            // {
            //        startActivity(new Intent(HomeActivity.this, OurBishopsOfflineActivity.class));

            //  }
        }

        if( v.getId() == R.id.rl_kaisthana)
        {
            // if(isConnected(this)) {
            //  Log.e("Online","Bishop");
            startActivity(new Intent(HomeActivity.this, KaisthanaSamithiActivity.class));
            //  }
            //  else
            //   {
            //    startActivity(new Intent(HomeActivity.this, KaisthanaOfflineActivity.class));

            //  }
        }

        if( v.getId() == R.id.rl_contact_us)
        {
            Intent intent = new Intent(HomeActivity.this,ContactUsActivity.class);
            intent.putExtra("lat",lat);
            intent.putExtra("lng",lng);
            //  Log.e("latlong",""+lat+lng);
            startActivity(intent);
        }

        if( v.getId() == R.id.rl_resources)
        {
            startActivity(new Intent(HomeActivity.this,ResourcesActivity.class));
        }

        if( v.getId() == R.id.rl_publications)
        {
            startActivity(new Intent(HomeActivity.this,PublicationsActivity.class));
        }

        if(v.getId() == R.id.rl_organisation)
        {
            startActivity(new Intent(HomeActivity.this,OrganisationsActivity.class));

        }
        if(v.getId() == R.id.rl_edirectory)
        {
            SharedPreferences preferences = HomeActivity.this.getSharedPreferences("LoginParish",Context.MODE_PRIVATE);
            String user = preferences.getString("user","");
            String pwd = preferences.getString("pass","");
            //Log.e("dsds",user+pwd+ TextUtils.isEmpty(user));

            if(isConnected(HomeActivity.this)) {
                if (TextUtils.isEmpty(user)) {
                    startActivity(new Intent(HomeActivity.this, EDirectoryActivity.class));

                } else {
                    startActivity(new Intent(HomeActivity.this, ParishMemberActivity.class));

                }
            }
            else
            {
                startActivity(new Intent(HomeActivity.this, ParishMemberActivity.class));

            }

        }

        if( v.getId() == R.id.rl_about)
        {
            Intent intent = new Intent(this, PdfViewerActivityStatic.class);

            intent.putExtra("pdfName","STORY BEHIND THE LOGO");
            intent.putExtra("pdfFile","story.pdf");
            //mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://stpaulsmarthomabahrain.com/membershipform/church_admin/publication_pdf/" +parish.getFile_name())));
            startActivity(intent);        }

        if( v.getId() == R.id.rl_dir)
        {
//            SharedPreferences preferences = HomeActivity.this.getSharedPreferences("LoginParish",Context.MODE_PRIVATE);
//            String user = preferences.getString("user","");
//            String pwd = preferences.getString("pass","");
//            // Log.e("dsds",user+pwd+TextUtils.isEmpty(user));
//
//            if(isConnected(HomeActivity.this)) {
//                if (TextUtils.isEmpty(user)) {
//                    startActivity(new Intent(HomeActivity.this, EDirectoryActivity.class));
//
//                } else {
//                    startActivity(new Intent(HomeActivity.this, ParishMemberActivity.class));
//
//                }
//            }
//            else
//            {
//                startActivity(new Intent(HomeActivity.this, ParishMemberActivity.class));
//
//            }

            Intent intent = new Intent(this, PdfViewerActivityStatic.class);

            intent.putExtra("pdfName","VERSES - SPECIAL OCCASIONS");
            intent.putExtra("pdfFile","verses.pdf");

            //mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://stpaulsmarthomabahrain.com/membershipform/church_admin/publication_pdf/" +parish.getFile_name())));
            startActivity(intent);

        }

        if( v.getId() == R.id.rl_privacy)
        {
            startActivity(new Intent(HomeActivity.this,PrivacyPolicyActivity.class));
        }
    }

    private void getData() {


        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getContact();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //Log.d("URL", "=====" + response.raw().request().url());

                // Log.e("getting about us", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        //     Log.e("Success message",jsonObject.getString("success"));

                        //   Log.e("Success is" , " "+ jsonObject.get("success"));
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                        lat = Double.valueOf(jsonObject1.getString("latitude"));
                        lng = Double.valueOf(jsonObject1.getString("longitude"));


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
