package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import stpaulsmarthom.com.stpaulsmarthom.R;

public class AddressIndiaActivity extends AppCompatActivity implements View.OnClickListener{

    TextView parish_house_ind,parish_town,parish_district,parish_pin,
    parish_state,parish_tel_code,parish_mobile,parish_emer_tel_office,parish_emer_tel_res,parish_emer_mobile,parish_po,parish_emer_contact_india;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_india);
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
        myActionBarTitle.setText("ADDRESS IN INDIA");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eb4142")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("PARISH MEMBER");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);

        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressIndiaActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);                finish();
            }
        });


        parish_po = findViewById(R.id.parish_po);
        parish_house_ind = (TextView)findViewById(R.id.parish_house_ind);
        parish_town = (TextView)findViewById(R.id.parish_town);
        parish_district = (TextView)findViewById(R.id.parish_district);
        parish_pin = (TextView)findViewById(R.id.parish_pin);
        parish_state = (TextView)findViewById(R.id.parish_state);
        parish_tel_code = (TextView)findViewById(R.id.parish_tel_code);
        parish_mobile = (TextView)findViewById(R.id.parish_mobile);
        parish_emer_tel_office = (TextView)findViewById(R.id.parish_emer_tel_office);
        parish_emer_tel_res = (TextView)findViewById(R.id.parish_emer_tel_res);
        parish_emer_mobile = (TextView)findViewById(R.id.parish_emer_mobile);
        parish_emer_contact_india = findViewById(R.id.parish_emer_contact_india);

        parish_emer_mobile.setText(getIntent().getStringExtra("parish_emer_mobile"));
        parish_emer_mobile.setAllCaps(true);


        parish_emer_tel_res.setText(getIntent().getStringExtra("parish_emer_tel_res"));
        parish_emer_tel_res.setAllCaps(true);


        parish_emer_tel_office.setText(getIntent().getStringExtra("parish_emer_tel_office"));
        parish_emer_tel_office.setAllCaps(true);


        parish_emer_contact_india.setText(getIntent().getStringExtra("parish_emer_contact_india"));
        parish_emer_contact_india.setAllCaps(true);

        parish_house_ind.setText(getIntent().getStringExtra("parish_house_ind"));
        parish_house_ind.setAllCaps(true);

        parish_town.setText(getIntent().getStringExtra("parish_town"));
        parish_town.setAllCaps(true);

        parish_district.setText(getIntent().getStringExtra("parish_district"));
        parish_district.setAllCaps(true);

        parish_pin.setText(getIntent().getStringExtra("parish_pin"));
        parish_pin.setAllCaps(true);

        parish_state.setText(getIntent().getStringExtra("parish_state"));
        parish_state.setAllCaps(true);

        parish_tel_code.setText(getIntent().getStringExtra("parish_tel_code"));
        parish_tel_code.setAllCaps(true);

        parish_mobile.setText(getIntent().getStringExtra("parish_mobile"));
        parish_mobile.setAllCaps(true);

        parish_po.setText(getIntent().getStringExtra("parish_po"));
        parish_po.setAllCaps(true);



        parish_emer_tel_office.setOnClickListener(this);
        parish_tel_code.setOnClickListener(this);
        parish_mobile.setOnClickListener(this);
        parish_emer_tel_res.setOnClickListener(this);
        parish_emer_mobile.setOnClickListener(this);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }



public void sendEmail(String emailAddress)
{
//    Intent testIntent = new Intent(Intent.ACTION_SENDTO);
//    testIntent.setData(Uri.parse("mailto:"+emailAddress));
//    startActivity(testIntent);

    Intent testIntent = new Intent(Intent.ACTION_SENDTO);
    testIntent.setData(Uri.parse("mailto:"));
    testIntent.putExtra(Intent.EXTRA_EMAIL  , new String[] {emailAddress });
    testIntent.putExtra(Intent.EXTRA_SUBJECT, "My subject");

    startActivity(Intent.createChooser(testIntent, "Email via..."));
}


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.parish_emer_tel_office)
        {
            if(!TextUtils.isEmpty(parish_emer_tel_office.getText().toString().trim())) {
                callPhone(getIntent().getStringExtra("parish_emer_tel_office"));
            }
        }

        if(view.getId()==R.id.parish_tel_code)
        {
            if(!TextUtils.isEmpty(parish_tel_code.getText().toString().trim())) {
                callPhone(getIntent().getStringExtra("parish_tel_code"));
            }
        }

        if(view.getId()==R.id.parish_mobile)
        {
            if(!TextUtils.isEmpty(parish_mobile.getText().toString().trim())) {
                callPhone(getIntent().getStringExtra("parish_mobile"));
            }
        }

        if(view.getId()==R.id.parish_emer_tel_res)
        {
            if(!TextUtils.isEmpty(parish_emer_tel_res.getText().toString().trim())) {
                callPhone(getIntent().getStringExtra("parish_emer_tel_res"));
            }
        }

        if(view.getId()==R.id.parish_emer_mobile)
        {
            if(!TextUtils.isEmpty(parish_emer_mobile.getText().toString().trim())) {
                callPhone(getIntent().getStringExtra("parish_emer_mobile"));
            }
        }


    }

    private void callPhone(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phoneNumber));
        startActivity(intent);
    }
}
