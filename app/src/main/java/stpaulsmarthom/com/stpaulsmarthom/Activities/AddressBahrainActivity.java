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

public class AddressBahrainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView parish_flat,parish_bldg,parish_area_bah,parish_road,parish_block,parish_emer_contact,
            parish_tel_off_bah,parish_tel_res_bah,parish_tel_mob_bah;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bahrain);
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
        myActionBarTitle.setText("ADDRESS IN BAHRAIN");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eb4142")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("PARISH MEMBER");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);

        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressBahrainActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);                finish();
            }
        });



        parish_flat = (TextView)findViewById(R.id.parish_flat);
        parish_bldg = (TextView)findViewById(R.id.parish_bldg);
        parish_area_bah = (TextView)findViewById(R.id.parish_area_bah);
        parish_road = (TextView)findViewById(R.id.parish_road);
        parish_block = (TextView)findViewById(R.id.parish_block);
        parish_emer_contact = (TextView)findViewById(R.id.parish_emer_contact);
        parish_tel_off_bah = (TextView)findViewById(R.id.parish_tel_off_bah);
        parish_tel_res_bah = (TextView)findViewById(R.id.parish_tel_res_bah);
        parish_tel_mob_bah = findViewById(R.id.parish_tel_mob_bah);

        parish_flat.setText(getIntent().getStringExtra("parish_flat"));
        parish_flat.setAllCaps(true);

        parish_bldg.setText(getIntent().getStringExtra("parish_bldg"));
        parish_bldg.setAllCaps(true);

        parish_area_bah.setText(getIntent().getStringExtra("parish_area_bah"));
        parish_area_bah.setAllCaps(true);

        parish_road.setText(getIntent().getStringExtra("parish_road"));
        parish_road.setAllCaps(true);

        parish_block.setText(getIntent().getStringExtra("parish_block"));
        parish_block.setAllCaps(true);

        parish_tel_res_bah.setText(getIntent().getStringExtra("parish_tel_res_bah"));
        parish_tel_res_bah.setAllCaps(true);

        parish_emer_contact.setText(getIntent().getStringExtra("parish_emer_contact"));
        parish_emer_contact.setAllCaps(true);

        parish_tel_off_bah.setText(getIntent().getStringExtra("parish_tel_off_bah"));
        parish_tel_off_bah.setAllCaps(true);

        parish_tel_mob_bah.setText(getIntent().getStringExtra("parish_tel_mob_bah"));
        parish_tel_mob_bah.setAllCaps(true);






        parish_tel_res_bah.setOnClickListener(this);
        parish_tel_off_bah.setOnClickListener(this);
        parish_tel_mob_bah.setOnClickListener(this);




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
        if(view.getId()==R.id.parish_tel_res_bah)
        {
            if(!TextUtils.isEmpty(parish_tel_res_bah.getText().toString().trim())) {
                callPhone(getIntent().getStringExtra("parish_tel_res_bah"));
            }
        }

        if(view.getId()==R.id.parish_tel_off_bah)
        {
            if(!TextUtils.isEmpty(parish_tel_off_bah.getText().toString().trim())) {
                callPhone(getIntent().getStringExtra("parish_tel_off_bah"));
            }
        }

        if(view.getId()==R.id.parish_tel_mob_bah)
        {
            if(!TextUtils.isEmpty(parish_tel_mob_bah.getText().toString().trim())) {
                callPhone(getIntent().getStringExtra("parish_tel_mob_bah"));
            }
        }


    }

    private void callPhone(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phoneNumber));
        startActivity(intent);
    }
}
