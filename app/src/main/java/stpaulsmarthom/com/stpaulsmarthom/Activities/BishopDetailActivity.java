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
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.io.File;

import stpaulsmarthom.com.stpaulsmarthom.R;

public class BishopDetailActivity extends AppCompatActivity {

    CircularImageView iv_bishop_detail;
    TextView tv_bishop_email,tv_bishop_location,tv_bishop_phone,tv_bishop_name,tv_bishop_dob;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_bishop_detail);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#a3cc2e"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#a3cc2e"));
            getWindow().setNavigationBarColor(Color.parseColor("#a3cc2e"));

        }
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        TextView myActionBarTitle;
         myActionBarTitle = (TextView)findViewById(R.id.myActionBarTitle);
        myActionBarTitle.setText("BISHOP PROFILE");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#a3cc2e")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setTitle("BISHOP PROFILE");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);

        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BishopDetailActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);                finish();
            }
        });

        tv_bishop_dob = findViewById(R.id.tv_bishop_dob);
        iv_bishop_detail = (CircularImageView)findViewById(R.id.iv_bishop_detail);
        tv_bishop_email = (TextView)findViewById(R.id.tv_bishop_email);
        tv_bishop_location = (TextView)findViewById(R.id.tv_bishop_location);
        tv_bishop_phone = (TextView)findViewById(R.id.tv_bishop_phone);
        tv_bishop_name = (TextView)findViewById(R.id.tv_bishop_name);


        tv_bishop_location.setText(getIntent().getStringExtra("bishopLocation"));
        tv_bishop_email.setText(getIntent().getStringExtra("bishopEmail"));
        tv_bishop_phone.setText(getIntent().getStringExtra("bishopPhone"));
        tv_bishop_name.setText(getIntent().getStringExtra("bishopName"));
        tv_bishop_dob.setText(getIntent().getStringExtra("bishopDob"));
        if(isConnected(this))
        {
            Picasso.with(this)
                    .load(getIntent().getStringExtra("bishopImage"))
                    .placeholder(R.drawable.sample)
                    .error(R.drawable.sample)
                    .into(iv_bishop_detail);
//            Glide.with(this)
//                    .load(getIntent().getStringExtra("bishopImage"))
//                    .placeholder(R.drawable.sample)
//                    .into(iv_bishop_detail);
        }
        else {
            File imgFile = new File(getIntent().getStringExtra("bishopImage"));
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            iv_bishop_detail.setImageBitmap(myBitmap);
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

}
