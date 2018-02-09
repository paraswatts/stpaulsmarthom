package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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

public class ParishMemberDetailActivity extends AppCompatActivity implements View.OnClickListener{

    TextView parish_po,parish_member_name,parish_roll_no,parish_area,parish_years_bah,parish_member_blood,parish_marital_status,
            parish_dom,parish_dob,parish_email,parish_company,parish_tel_off,parish_tel_res,parish_tel_mobile,
            parish_fax,parish_home,parish_flat,parish_bldg,parish_area_bah,parish_road,parish_block,parish_emer_contact,
            parish_tel_off_bah,parish_tel_res_bah,parish_house_ind,parish_town,parish_district,parish_pin,
            parish_state,parish_tel_code,parish_mobile,parish_emer_tel_office,parish_emer_tel_res,parish_emer_mobile,
            parish_wife_name,parish_dob_wife,parish_emplr_name,parish_native,parish_blood_wife,parish_wife_in_bah,
            parish_wife_employee,parish_child_name,parish_dob_child,parish_blood_child,parish_in_bah_child,parish_employee_child,
            parish_sex_child,parish_inst_child,parish_emplr_name_child,parish_tel_mob_bah,parish_emer_contact_india,child_header;
    ChildModel childModel;
    DbHelper dbHelper;
    ListView listView_MemberChild;
    RecyclerView recyclerView ;
    private List<ChildModel> dataList = new ArrayList<>();
    ChildAdapter mAdapter;

    ImageView parish_member_wife,iv_parish_member_detail,iv_parish_child;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parish_member_detail);
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
        myActionBarTitle.setText("PARISH MEMBER");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eb4142")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("PARISH MEMBER");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);

        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParishMemberDetailActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);                finish();
            }
        });


        recyclerView = findViewById(R.id.recyclerView_MemberChild) ;
        //listView_MemberChild = findViewById(R.id.listView_MemberChild);
        mAdapter = new ChildAdapter(dataList, this);


        child_header = findViewById(R.id.child_header);
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
        parish_flat = (TextView)findViewById(R.id.parish_flat);
        parish_bldg = (TextView)findViewById(R.id.parish_bldg);
        parish_area_bah = (TextView)findViewById(R.id.parish_area_bah);
        parish_road = (TextView)findViewById(R.id.parish_road);
        parish_block = (TextView)findViewById(R.id.parish_block);
        parish_emer_contact = (TextView)findViewById(R.id.parish_emer_contact);
        parish_tel_off_bah = (TextView)findViewById(R.id.parish_tel_off_bah);
        parish_tel_res_bah = (TextView)findViewById(R.id.parish_tel_res_bah);
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
        parish_wife_name = (TextView)findViewById(R.id.parish_wife_name);
        parish_dob_wife = (TextView)findViewById(R.id.parish_dob_wife);
        parish_emplr_name = (TextView)findViewById(R.id.parish_emplr_name);
        parish_native = (TextView)findViewById(R.id.parish_native);
        parish_blood_wife = (TextView)findViewById(R.id.parish_blood_wife);
        parish_wife_in_bah = (TextView)findViewById(R.id.parish_wife_in_bah);
        parish_wife_employee = (TextView)findViewById(R.id.parish_wife_employee);
        parish_child_name = (TextView)findViewById(R.id.parish_child_name);
        parish_dob_child = (TextView)findViewById(R.id.parish_dob_child);
        parish_blood_child = (TextView)findViewById(R.id.parish_blood_child);
        parish_in_bah_child = (TextView)findViewById(R.id.parish_in_bah_child);
        parish_employee_child = (TextView)findViewById(R.id.parish_employee_child);
        parish_sex_child = (TextView)findViewById(R.id.parish_sex_child);
        parish_inst_child = (TextView)findViewById(R.id.parish_inst_child);
        parish_emplr_name_child = (TextView)findViewById(R.id.parish_emplr_name_child);
        parish_emer_contact = findViewById(R.id.parish_emer_contact);
        parish_tel_mob_bah = (TextView)findViewById(R.id.parish_tel_mob_bah);
        parish_po = findViewById(R.id.parish_po);
        parish_emer_contact_india = findViewById(R.id.parish_emer_contact_india);

        iv_parish_member_detail = findViewById(R.id.iv_parish_member_detail);
        parish_member_wife = findViewById(R.id.parish_member_wife);
        iv_parish_child = findViewById(R.id.iv_parish_child);

        //Log.e("Image",getIntent().getStringExtra("iv_parish_member_detail"));
       // Log.e("Image",getIntent().getStringExtra("parish_member_wife"));
        if(isConnected(this)) {
            Picasso.with(this)
                    .load("http://stpaulsmarthomabahrain.com/membershipform/"+getIntent().getStringExtra("iv_parish_member_detail"))
                    .placeholder(R.drawable.sample)
                    .error(R.drawable.sample)
                    .into(iv_parish_member_detail);


            Picasso.with(this)
                    .load("http://stpaulsmarthomabahrain.com/membershipform/"+getIntent().getStringExtra("parish_member_wife"))
                    .placeholder(R.drawable.sample)
                    .error(R.drawable.sample)
                    .into(parish_member_wife);
        }

        else
        {
            File imgFile = new File(getIntent().getStringExtra("iv_parish_member_detail"));
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            iv_parish_member_detail.setImageBitmap(myBitmap);

            File imgFile1 = new File(getIntent().getStringExtra("parish_member_wife"));
            Bitmap myBitmap1 = BitmapFactory.decodeFile(imgFile1.getAbsolutePath());
            parish_member_wife.setImageBitmap(myBitmap1);
        }

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

        parish_tel_mob_bah.setText(getIntent().getStringExtra("parish_tel_mob_bah"));
        parish_tel_mob_bah.setAllCaps(true);

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


        parish_emer_tel_office.setText(getIntent().getStringExtra("parish_emer_tel_office"));
        parish_emer_tel_office.setAllCaps(true);

        parish_emer_tel_res.setText(getIntent().getStringExtra("parish_emer_tel_res"));
        parish_emer_tel_res.setAllCaps(true);


        parish_tel_off.setText(getIntent().getStringExtra("parish_tel_off"));
        parish_tel_off.setAllCaps(true);

        parish_emer_contact.setText(getIntent().getStringExtra("parish_emer_contact"));
        parish_emer_contact.setAllCaps(true);

        parish_emer_contact_india.setText(getIntent().getStringExtra("parish_emer_contact_india"));
        parish_emer_contact_india.setAllCaps(true);

        parish_po.setText(getIntent().getStringExtra("parish_po"));
        parish_po.setAllCaps(true);

        parish_emer_mobile.setText(getIntent().getStringExtra("parish_emer_mobile"));
        parish_emer_mobile.setAllCaps(true);

        parish_native.setText(getIntent().getStringExtra("parish_native"));
        parish_native.setAllCaps(true);
        if(isConnected(this)) {
           // listView_MemberChild.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            DividerItemDecoration itemDecor = new DividerItemDecoration(this, VERTICAL);
            recyclerView.addItemDecoration(itemDecor);
            recyclerView.setAdapter(mAdapter);
            getData(getIntent().getStringExtra("parish_roll_no"));
        }
        else {
            try {

                recyclerView.setVisibility(View.VISIBLE);
                recyclerView.setAdapter(mAdapter);
                //listView_MemberChild.setVisibility(View.VISIBLE);
                dbHelper = new DbHelper(this);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
//            listView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                dbHelper = new DbHelper(this);

                List<ChildModel> contacts = dbHelper.getChildList(getIntent().getStringExtra("parish_roll_no"));
                //listView_MemberChild.setAdapter(childCursorAdapter);
                for (ChildModel cn : contacts) {

                    childModel = new ChildModel(cn.getChildname(),
                            cn.getChildinbaharian(),
                            cn.getSexchild(),
                            cn.getDatebrithchild(),
                            cn.getBloodchild(),
                            cn.getStudentchild(),
                            cn.getEmployechild(),
                            cn.getChildsimage(),
                            cn.getEmployername(),
                            cn.getRollno());
//                    orgDetailModel.setName(cn.getName());
//                    orgDetailModel.setDesignation(cn.getDesignation());
//                    orgDetailModel.setPhone(cn.getPhone());
                    dataList.add(childModel);
                }

                mAdapter.notifyDataSetChanged();

                //getLoaderManager().initLoader(0, null, this);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

            parish_email.setOnClickListener(this);
            parish_tel_off.setOnClickListener(this);
            parish_tel_mobile.setOnClickListener(this);
            parish_tel_res.setOnClickListener(this);
            parish_tel_off_bah.setOnClickListener(this);
            parish_tel_res_bah.setOnClickListener(this);
            parish_tel_mob_bah.setOnClickListener(this);
            parish_tel_code.setOnClickListener(this);
            parish_mobile.setOnClickListener(this);
            parish_emer_contact_india.setOnClickListener(this);
            parish_emer_tel_office.setOnClickListener(this);
            parish_emer_mobile.setOnClickListener(this);
            parish_emer_tel_res.setOnClickListener(this);



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }



public void sendEmail(String emailAddress)
{
    Intent testIntent = new Intent(Intent.ACTION_SENDTO);
    testIntent.setData(Uri.parse("mailto:"));
    testIntent.putExtra(Intent.EXTRA_EMAIL  , new String[] {emailAddress });
    testIntent.putExtra(Intent.EXTRA_SUBJECT, "My subject");

    startActivity(Intent.createChooser(testIntent, "Email via..."));
}


    private void getData(String rollno) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        //progressDialog.setProgressDrawable(getDrawable(R.drawable.parish_progress));
        progressDialog.show();
        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getParishMemberChild(rollno);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
               // mProgressBar.setVisibility(View.INVISIBLE);
                //Log.d("URL", "=====" + response.raw().request().url());
//
               // Log.e("getting about us", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {

                        JSONObject jsonObject = new JSONObject(response.body().toString());

                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        Boolean success = Boolean.valueOf(String.valueOf(jsonObject.get("success")));

                        if(success) {
                          //  Log.e("Success is ", "" + success);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                childModel = new ChildModel(
                                        jsonObject1.getString("childname")
                                        , jsonObject1.getString("childinbaharian")
                                        , jsonObject1.getString("sexchild")
                                        , jsonObject1.getString("datebrithchild")
                                        , jsonObject1.getString("bloodchild")
                                        , jsonObject1.getString("studentchild")
                                        , jsonObject1.getString("employechild")
                                        , jsonObject1.getString("childsimage")
                                        , jsonObject1.getString("employername")
                                        , jsonObject1.getString("rollno")

                                );
                                dataList.add(childModel);
                            }
                            mAdapter.notifyDataSetChanged();


                        }
                        else {
                            child_header.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                       // Log.e("Exception Image",""+e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

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

        if(view.getId()==R.id.parish_tel_off_bah)
        {
            if(!TextUtils.isEmpty(parish_tel_off_bah.getText().toString().trim())) {
                callPhone(getIntent().getStringExtra("parish_tel_off_bah"));
            }
        }

        if(view.getId()==R.id.parish_tel_res_bah)
        {
            if(!TextUtils.isEmpty(parish_tel_res_bah.getText().toString().trim())) {
                callPhone(getIntent().getStringExtra("parish_tel_res_bah"));
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
        if(view.getId()==R.id.parish_emer_contact_india)
        {
            if(!TextUtils.isEmpty(parish_emer_contact_india.getText().toString().trim())) {
                callPhone(getIntent().getStringExtra("parish_emer_contact_india"));
            }
        }
        if(view.getId()==R.id.parish_emer_tel_office)
        {
            if(!TextUtils.isEmpty(parish_emer_tel_office.getText().toString().trim())) {
                callPhone(getIntent().getStringExtra("parish_emer_tel_office"));
            }
        }

        if(view.getId()==R.id.parish_emer_mobile)
        {
            if(!TextUtils.isEmpty(parish_emer_mobile.getText().toString().trim())) {
                callPhone(getIntent().getStringExtra("parish_emer_mobile"));
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
