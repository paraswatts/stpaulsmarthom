package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

import stpaulsmarthom.com.stpaulsmarthom.R;

public class FamilyDetailsActivity extends AppCompatActivity {

    TextView parish_wife_name, parish_dob_wife, parish_emplr_name, parish_native, parish_blood_wife, parish_wife_in_bah,
            parish_wife_employee;
    ImageView parish_member_wife;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.family_details);
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
        myActionBarTitle.setText("FAMILY DETAILS");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eb4142")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("PARISH MEMBER");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);

        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FamilyDetailsActivity.this, HomeActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();
            }
        });


        parish_wife_name = (TextView) findViewById(R.id.parish_wife_name);
        parish_dob_wife = (TextView) findViewById(R.id.parish_dob_wife);
        parish_emplr_name = (TextView) findViewById(R.id.parish_emplr_name);
        parish_native = (TextView) findViewById(R.id.parish_native);
        parish_blood_wife = (TextView) findViewById(R.id.parish_blood_wife);
        parish_wife_in_bah = (TextView) findViewById(R.id.parish_wife_in_bah);
        parish_wife_employee = (TextView) findViewById(R.id.parish_wife_employee);
        parish_member_wife = findViewById(R.id.parish_member_wife);

        parish_wife_name.setText(getIntent().getStringExtra("parish_wife_name"));
        parish_wife_name.setAllCaps(true);

        parish_dob_wife.setText(getIntent().getStringExtra("parish_dob_wife"));
        parish_dob_wife.setAllCaps(true);

        parish_emplr_name.setText(getIntent().getStringExtra("parish_emplr_name"));
        parish_emplr_name.setAllCaps(true);

        parish_blood_wife.setText(getIntent().getStringExtra("parish_blood_wife"));
        parish_blood_wife.setAllCaps(true);

        parish_wife_in_bah.setText(getIntent().getStringExtra("parish_wife_in_bah"));
        parish_wife_in_bah.setAllCaps(true);

        parish_wife_employee.setText(getIntent().getStringExtra("parish_wife_employee"));
        parish_wife_employee.setAllCaps(true);

        parish_native.setText(getIntent().getStringExtra("parish_native"));
        parish_native.setAllCaps(true);


        //  if(isConnected(this)) {
//            Picasso.with(this)
//                    .load("http://stpaulsmarthomabahrain.com/membershipform/"+getIntent().getStringExtra("parish_member_wife"))
//                    .placeholder(R.drawable.sample)
//                    .error(R.drawable.sample)
//                    .into(parish_member_wife);

        Picasso picasso = Picasso.with(getApplicationContext());
        picasso.setIndicatorsEnabled(false);
        picasso.load("http://stpaulsmarthomabahrain.com/membershipform/" + getIntent().getStringExtra("parish_member_wife"))
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(parish_member_wife, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        //Try again online if cache failed
                        Picasso.with(getApplicationContext())
                                .load("http://stpaulsmarthomabahrain.com/membershipform/" + getIntent().getStringExtra("parish_member_wife"))
                                .error(R.drawable.sample)
                                .into(parish_member_wife, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {

                                    }

                                    @Override
                                    public void onError() {
                                        Log.v("Picasso", "Could not fetch image");
                                    }
                                });
                    }
                });

//
//        else
//        {
//            Picasso.with(this)
//                    .load("http://stpaulsmarthomabahrain.com/membershipform/"+getIntent().getStringExtra("parish_member_wife"))
//                    .networkPolicy(NetworkPolicy.OFFLINE)
//                    .into(parish_member_wife, new com.squareup.picasso.Callback() {
//                        @Override
//                        public void onSuccess() {
//
//                        }
//
//                        @Override
//                        public void onError() {
//                            //Try again online if cache failed
//                            Picasso.with(getApplicationContext())
//                                    .load("http://stpaulsmarthomabahrain.com/membershipform/"+getIntent().getStringExtra("parish_member_wife"))
//                                    .error(R.drawable.sample)
//                                    .into(parish_member_wife, new com.squareup.picasso.Callback() {
//                                        @Override
//                                        public void onSuccess() {
//
//                                        }
//
//                                        @Override
//                                        public void onError() {
//                                            Log.v("Picasso","Could not fetch image");
//                                        }
//                                    });
//                        }
//                    });
//            File imgFile = new File(getIntent().getStringExtra("parish_member_wife"));
//            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//            parish_member_wife.setImageBitmap(myBitmap);
    //     }


    }

    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }



}
