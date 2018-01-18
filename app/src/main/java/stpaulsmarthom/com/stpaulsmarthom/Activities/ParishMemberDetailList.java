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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.squareup.picasso.NetworkPolicy;
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

public class ParishMemberDetailList extends AppCompatActivity implements View.OnClickListener{

    RelativeLayout rl_per_det,rl_add_bah,rl_add_ind,rl_fam_det,rl_child_det;
    TextView tc_mem_name;

    ImageView parish_member_wife,iv_parish_member_detail,iv_parish_child;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parish_member_detail_list);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#eb4142"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#eb4142"));
            getWindow().setNavigationBarColor(Color.parseColor("#eb4142"));
        }

//        ImageView iv_home = findViewById(R.id.home);
//        iv_home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ParishMemberDetailList.this,HomeActivity.class);
//                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//                startActivity(intent);
//                finish();
//            }
//        });

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        TextView myActionBarTitle;
        myActionBarTitle = (TextView)findViewById(R.id.myActionBarTitle);
        myActionBarTitle.setText("DETAILS");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eb4142")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("PARISH MEMBER");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);

        tc_mem_name = findViewById(R.id.tc_mem_name);
        rl_per_det = findViewById(R.id.rl_per_det);
        rl_add_bah = findViewById(R.id.rl_add_bah);
        rl_add_ind = findViewById(R.id.rl_add_ind);
        rl_fam_det = findViewById(R.id.rl_fam_det);
        rl_child_det = findViewById(R.id.rl_child_det);
        iv_parish_member_detail = findViewById(R.id.iv_parish_member_detail);
        //Log.e("Image",getIntent().getStringExtra("iv_parish_member_detail"));
       // Log.e("Image",getIntent().getStringExtra("parish_member_wife"));
        tc_mem_name.setText(getIntent().getStringExtra("parish_member_name"));
        if(isConnected(this)) {
//            Picasso.with(this)
//                    .load("http://stpaulsmarthomabahrain.com/membershipform/"+getIntent().getStringExtra("iv_parish_member_detail"))
//                    .placeholder(R.drawable.sample)
//                    .error(R.drawable.sample)
//                    .into(iv_parish_member_detail);
            Picasso picasso = Picasso.with(this);
            picasso.setIndicatorsEnabled(false);
            picasso.load("http://stpaulsmarthomabahrain.com/membershipform/"+getIntent().getStringExtra("iv_parish_member_detail"))
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(iv_parish_member_detail, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            Picasso.with(getApplicationContext())
                                    .load("http://stpaulsmarthomabahrain.com/membershipform/"+getIntent().getStringExtra("iv_parish_member_detail"))
                                    .error(R.drawable.sample)
                                    .into(iv_parish_member_detail, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                            Log.v("Picasso","Could not fetch image");
                                        }
                                    });
                        }
                    });

//            Picasso.with(this)
//                    .load("http://stpaulsmarthomabahrain.com/membershipform/"+getIntent().getStringExtra("parish_member_wife"))
//                    .placeholder(R.drawable.sample)
//                    .error(R.drawable.sample)
//                    .into(parish_member_wife);
        }

        else {

            Picasso.with(this)
                    .load("http://stpaulsmarthomabahrain.com/membershipform/"+getIntent().getStringExtra("iv_parish_member_detail"))
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(iv_parish_member_detail, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            Picasso.with(getApplicationContext())
                                    .load("http://stpaulsmarthomabahrain.com/membershipform/"+getIntent().getStringExtra("iv_parish_member_detail"))
                                    .error(R.drawable.sample)
                                    .into(iv_parish_member_detail, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                            Log.v("Picasso","Could not fetch image");
                                        }
                                    });
                        }
                    });

        }

        rl_per_det.setOnClickListener(this);
        rl_add_bah.setOnClickListener(this);
        rl_add_ind.setOnClickListener(this);
        rl_fam_det.setOnClickListener(this);
        rl_child_det.setOnClickListener(this);



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


    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.rl_per_det)
        {
            Intent personalDetails =  new Intent(ParishMemberDetailList.this,PersonalDetailActivity.class);
            personalDetails.putExtra("parish_member_name",getIntent().getStringExtra("parish_member_name"));
            personalDetails.putExtra("parish_roll_no",getIntent().getStringExtra("parish_roll_no"));
            personalDetails.putExtra("parish_area",getIntent().getStringExtra("parish_area"));
            personalDetails.putExtra("parish_years_bah",getIntent().getStringExtra("parish_years_bah"));
            personalDetails.putExtra("parish_member_blood",getIntent().getStringExtra("parish_member_blood"));
            personalDetails.putExtra("parish_marital_status",getIntent().getStringExtra("parish_marital_status"));
            personalDetails.putExtra("parish_dom",getIntent().getStringExtra("parish_dom"));
            personalDetails.putExtra("parish_dob",getIntent().getStringExtra("parish_dob"));
            personalDetails.putExtra("parish_email",getIntent().getStringExtra("parish_email"));
            personalDetails.putExtra("parish_company",getIntent().getStringExtra("parish_company"));
            personalDetails.putExtra("parish_tel_res",getIntent().getStringExtra("parish_tel_res"));
            personalDetails.putExtra("parish_tel_mobile",getIntent().getStringExtra("parish_tel_mobile"));
            personalDetails.putExtra("parish_fax",getIntent().getStringExtra("parish_fax"));
            personalDetails.putExtra("parish_home",getIntent().getStringExtra("parish_home"));

            startActivity(personalDetails);
            
        }

        if(v.getId() == R.id.rl_add_bah)
        {
            Intent addBahDetails =  new Intent(ParishMemberDetailList.this,AddressBahrainActivity.class);

            addBahDetails.putExtra("parish_flat",getIntent().getStringExtra("parish_flat"));
            addBahDetails.putExtra("parish_bldg",getIntent().getStringExtra("parish_bldg"));
            addBahDetails.putExtra("parish_area_bah",getIntent().getStringExtra("parish_area_bah"));
            addBahDetails.putExtra("parish_road",getIntent().getStringExtra("parish_road"));
            addBahDetails.putExtra("parish_block",getIntent().getStringExtra("parish_block"));
            addBahDetails.putExtra("parish_tel_res_bah",getIntent().getStringExtra("parish_tel_res_bah"));
            addBahDetails.putExtra("parish_tel_mob_bah",getIntent().getStringExtra("parish_tel_mob_bah"));
            addBahDetails.putExtra("parish_emer_contact",getIntent().getStringExtra("parish_emer_contact"));
            addBahDetails.putExtra("parish_tel_off_bah",getIntent().getStringExtra("parish_tel_off_bah"));
            startActivity(addBahDetails);

        }

        if(v.getId() == R.id.rl_add_ind)
        {

            Intent addIndDetails =  new Intent(ParishMemberDetailList.this,AddressIndiaActivity.class);

            addIndDetails.putExtra("parish_po",getIntent().getStringExtra("parish_po"));
            addIndDetails.putExtra("parish_house_ind",getIntent().getStringExtra("parish_house_ind"));
            addIndDetails.putExtra("parish_town",getIntent().getStringExtra("parish_town"));
            addIndDetails.putExtra("parish_district",getIntent().getStringExtra("parish_district"));
            addIndDetails.putExtra("parish_pin",getIntent().getStringExtra("parish_pin"));
            addIndDetails.putExtra("parish_state",getIntent().getStringExtra("parish_state"));
            addIndDetails.putExtra("parish_tel_code",getIntent().getStringExtra("parish_tel_code"));
            addIndDetails.putExtra("parish_mobile",getIntent().getStringExtra("parish_mobile"));
            addIndDetails.putExtra("parish_emer_tel_office",getIntent().getStringExtra("parish_emer_tel_office"));
            addIndDetails.putExtra("parish_emer_tel_res",getIntent().getStringExtra("parish_emer_tel_res"));
            addIndDetails.putExtra("parish_emer_contact_india",getIntent().getStringExtra("parish_emer_contact_india"));
            addIndDetails.putExtra("parish_emer_mobile",getIntent().getStringExtra("parish_emer_mobile"));
            startActivity(addIndDetails);


        }
        
        if(v.getId() == R.id.rl_fam_det)
        {

            Intent familyDetailIntent =  new Intent(ParishMemberDetailList.this,FamilyDetailsActivity.class);


            familyDetailIntent.putExtra("parish_wife_name",getIntent().getStringExtra("parish_wife_name"));
            familyDetailIntent.putExtra("parish_dob_wife",getIntent().getStringExtra("parish_dob_wife"));
            familyDetailIntent.putExtra("parish_emplr_name",getIntent().getStringExtra("parish_emplr_name"));
            familyDetailIntent.putExtra("parish_native",getIntent().getStringExtra("parish_native"));
            familyDetailIntent.putExtra("parish_blood_wife",getIntent().getStringExtra("parish_blood_wife"));
            familyDetailIntent.putExtra("parish_wife_in_bah",getIntent().getStringExtra("parish_wife_in_bah"));
            familyDetailIntent.putExtra("parish_wife_employee",getIntent().getStringExtra("parish_wife_employee"));
            familyDetailIntent.putExtra("parish_member_wife",getIntent().getStringExtra("parish_member_wife"));
            startActivity(familyDetailIntent);

        }

        if(v.getId() == R.id.rl_child_det)
        {
            Intent childDetailIntent =  new Intent(ParishMemberDetailList.this,ChildDetailsActivity.class);
            childDetailIntent.putExtra("parish_roll_no",getIntent().getStringExtra("parish_roll_no"));
            startActivity(childDetailIntent);
        }
    }
}
