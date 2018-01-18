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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stpaulsmarthom.com.stpaulsmarthom.APIService;
import stpaulsmarthom.com.stpaulsmarthom.APIUrl;
import stpaulsmarthom.com.stpaulsmarthom.Adapters.ChildAdapter;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbHelper;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ChildModel;
import stpaulsmarthom.com.stpaulsmarthom.R;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class PersonalDetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView parish_member_name,parish_roll_no,parish_area,parish_years_bah,parish_member_blood,parish_marital_status,
            parish_dom,parish_dob,parish_email,parish_company,parish_tel_off,parish_tel_res,parish_tel_mobile,
            parish_fax,parish_home;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_details);
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
        myActionBarTitle.setText("PERSONAL DETAILS");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eb4142")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("PARISH MEMBER");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);

        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalDetailActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);                finish();
            }
        });



        parish_member_name = (TextView)findViewById(R.id.parish_member_name);
        parish_roll_no = (TextView)findViewById(R.id.parish_roll_no);
        parish_area = (TextView)findViewById(R.id.parish_area);
        parish_years_bah = (TextView)findViewById(R.id.parish_years_bah);
        parish_member_blood = (TextView)findViewById(R.id.parish_member_blood);
        parish_marital_status = (TextView)findViewById(R.id.parish_marital_status);
        parish_dom = (TextView)findViewById(R.id.parish_dom);
        parish_dob = (TextView)findViewById(R.id.parish_dob);
        parish_email = (TextView)findViewById(R.id.parish_email);
        parish_company = (TextView)findViewById(R.id.parish_company);
        parish_tel_off = (TextView)findViewById(R.id.parish_tel_off);
        parish_tel_res = (TextView)findViewById(R.id.parish_tel_res);
        parish_tel_mobile = (TextView)findViewById(R.id.parish_tel_mobile);
        parish_fax = (TextView)findViewById(R.id.parish_fax);
        parish_home = (TextView)findViewById(R.id.parish_home);



        parish_member_name.setText(getIntent().getStringExtra("parish_member_name"));
        parish_member_name.setAllCaps(true);
        parish_roll_no.setText(getIntent().getStringExtra("parish_roll_no"));
        parish_roll_no.setAllCaps(true);
        parish_area.setText(getIntent().getStringExtra("parish_area"));
        parish_area.setAllCaps(true);
        parish_years_bah.setText(getIntent().getStringExtra("parish_years_bah"));
        parish_years_bah.setAllCaps(true);

        parish_member_blood.setText(getIntent().getStringExtra("parish_member_blood"));
        parish_member_blood.setAllCaps(true);

        parish_marital_status.setText(getIntent().getStringExtra("parish_marital_status"));
        parish_area.setAllCaps(true);

        parish_marital_status.setText(getIntent().getStringExtra("parish_dom"));
        parish_dob.setText(getIntent().getStringExtra("parish_dob"));
        parish_dob.setAllCaps(true);

        parish_email.setText(getIntent().getStringExtra("parish_email"));
        parish_email.setAllCaps(true);

        parish_company.setText(getIntent().getStringExtra("parish_company"));
        parish_company.setAllCaps(true);

        parish_tel_res.setText(getIntent().getStringExtra("parish_tel_res"));
        parish_tel_res.setAllCaps(true);

        parish_tel_mobile.setText(getIntent().getStringExtra("parish_tel_mobile"));
        parish_tel_mobile.setAllCaps(true);

        parish_fax.setText(getIntent().getStringExtra("parish_fax"));
        parish_fax.setAllCaps(true);

        parish_home.setText(getIntent().getStringExtra("parish_home"));
        parish_home.setAllCaps(true);





            parish_email.setOnClickListener(this);
            parish_tel_off.setOnClickListener(this);
            parish_tel_mobile.setOnClickListener(this);
            parish_tel_res.setOnClickListener(this);




    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }



public void sendEmail(String emailAddress)
{
    Intent testIntent = new Intent(Intent.ACTION_SENDTO);
    testIntent.setData(Uri.parse("mailto:"+emailAddress));
    startActivity(testIntent);
}


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.parish_email)
        {
            if(!TextUtils.isEmpty(parish_email.getText().toString().trim())) {
                sendEmail(getIntent().getStringExtra("parish_email"));
            }
        }

        if(view.getId()==R.id.parish_tel_off)
        {
            if(!TextUtils.isEmpty(parish_tel_off.getText().toString().trim())) {
                callPhone(getIntent().getStringExtra("parish_tel_off"));
            }
        }

        if(view.getId()==R.id.parish_tel_mobile)
        {
            if(!TextUtils.isEmpty(parish_tel_mobile.getText().toString().trim())) {
                callPhone(getIntent().getStringExtra("parish_tel_mobile"));
            }
        }

        if(view.getId()==R.id.parish_tel_res)
        {
            if(!TextUtils.isEmpty(parish_tel_res.getText().toString().trim())) {
                callPhone(getIntent().getStringExtra("parish_tel_res"));
            }
        }

    }

    private void callPhone(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phoneNumber));
        startActivity(intent);
    }
}
