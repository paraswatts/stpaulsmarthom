package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonElement;
import com.jpardogo.android.googleprogressbar.library.ChromeFloatingCirclesDrawable;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stpaulsmarthom.com.stpaulsmarthom.APIService;
import stpaulsmarthom.com.stpaulsmarthom.APIUrl;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbContract;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbHelper;
import stpaulsmarthom.com.stpaulsmarthom.R;

public class ContactUsActivity extends AppCompatActivity implements OnMapReadyCallback {
    ProgressBar mProgressBar;
    TextView tv_contact_address,tv_contact_phone,tv_contact_email;
    ImageView iv_contact;
    Button bt_prayer_request;
    static double lat,lng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#eb4142"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#eb4142"));
            getWindow().setNavigationBarColor(Color.parseColor("#eb4142"));
        }
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        TextView myActionBarTitle;
        myActionBarTitle = (TextView)findViewById(R.id.myActionBarTitle);
        myActionBarTitle.setText("CONTACT US");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eb4142")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("CONTACT US");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
        mProgressBar = (ProgressBar)findViewById(R.id.google_progress_contact);
        mProgressBar.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(this)
                .build());
        bt_prayer_request = (Button)findViewById(R.id.bt_prayer_request);
        tv_contact_address = (TextView)findViewById(R.id.tv_contact_address);
        tv_contact_phone = (TextView)findViewById(R.id.tv_contact_phone);
        tv_contact_email = (TextView)findViewById(R.id.tv_contact_email);
        iv_contact = (ImageView)findViewById(R.id.iv_contact) ;

        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactUsActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);                finish();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        bt_prayer_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactUsActivity.this,PrayerRequestActivity.class);
                intent.putExtra("api","2");
                startActivity(intent);

            }
        });
        if (isConnected(getApplicationContext())) {
            getData();
        }
        else
        {
            try {
                mProgressBar.setVisibility(View.INVISIBLE);

                DbHelper userDbHelper = new DbHelper(this);
                SQLiteDatabase sqLiteDatabase = userDbHelper.getWritableDatabase();

                Cursor query = sqLiteDatabase.query(DbContract.DbEntry.TABLE_CONTACT_US, //Table to query
                        null, //columns to return
                        null, //columns for the WHERE clause
                        null, //filter by row groups
                        null, null, null);
                int count = query.getCount();
                // Log.e("Db Count",""+count);
                while (query.moveToNext()) {
                    String address = query.getString(1);
                    String email = query.getString(2);
                    String phone = query.getString(3);

                    tv_contact_address.setText(address);
                    tv_contact_email.setText(email);
                    tv_contact_phone.setText(phone);
                    File imgFile = new File(query.getString(4));

                    if(imgFile.getAbsolutePath().toLowerCase().endsWith(".jpg")||imgFile.getAbsolutePath().toLowerCase().endsWith(".jpeg")) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        iv_contact.setImageBitmap(myBitmap);
                    }
                    else {
                        iv_contact.setImageDrawable(getResources().getDrawable(R.drawable.sample));
                    }

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




        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getContact();

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

                       // Log.e("Success is" , " "+ jsonObject.get("success"));
                        if(jsonObject.has("Data")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("Data");
                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                            tv_contact_address.setText(jsonObject1.getString("Address"));
                            tv_contact_phone.setText(jsonObject1.getString("Phone"));
                            tv_contact_email.setText(jsonObject1.getString("Email"));
                            lat = Double.valueOf(jsonObject1.getString("latitude"));
                            lng = Double.valueOf(jsonObject1.getString("longitude"));
                            final String name = jsonObject1.getString("Images");

                            final String imageUrl = "http://stpaulsmarthomabahrain.com/membershipform/church_admin/Contact/" + jsonObject1.getString("Images");


//                        Glide.with(getApplicationContext())
//                                .load(imageUrl)
//                                .placeholder(R.drawable.sample)
//                                .into(iv_contact);
                            Picasso picasso = Picasso.with(getApplicationContext());
                            picasso.setIndicatorsEnabled(false);
                            picasso.load(imageUrl)
                                    .placeholder(R.drawable.sample)
                                    .error(R.drawable.sample)
                                    .into(iv_contact);
                        }
                        else
                        {
                            Toast.makeText(ContactUsActivity.this, "No data found", Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        //Log.e("Exception Image",""+e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Log.e("get lng",""+getIntent().getDoubleExtra("lng",0.0));
        LatLng stPaulsBahrain = new LatLng(getIntent().getDoubleExtra("lng",0),getIntent().getDoubleExtra("lat",0));
        googleMap.addMarker(new MarkerOptions().position(stPaulsBahrain)
                .title("St Paul's Mar Thoma Parish")).showInfoWindow();
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(stPaulsBahrain));
        googleMap.animateCamera(CameraUpdateFactory.zoomIn());
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(16.5f), 2000, null);
        googleMap.getUiSettings().setMapToolbarEnabled(true);




    }


}
