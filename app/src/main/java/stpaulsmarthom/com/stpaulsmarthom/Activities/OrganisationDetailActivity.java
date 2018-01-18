package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.jpardogo.android.googleprogressbar.library.ChromeFloatingCirclesDrawable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stpaulsmarthom.com.stpaulsmarthom.APIService;
import stpaulsmarthom.com.stpaulsmarthom.APIUrl;
import stpaulsmarthom.com.stpaulsmarthom.Adapters.OrgSectionRecycler;
import stpaulsmarthom.com.stpaulsmarthom.Adapters.OrgDetailAdapter;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbContract;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbHelper;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgDetailModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgSectionModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgTimingModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ParishMemberModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.SectionHeader;
import stpaulsmarthom.com.stpaulsmarthom.R;

public class OrganisationDetailActivity extends AppCompatActivity {
    OrgDetailAdapter mAdapter;
    OrgDetailModel orgDetailModel;
    OrgTimingModel orgTimingModel;

    RecyclerView recyclerView;
    OrgSectionRecycler adapterRecycler;
    List<SectionHeader> sectionHeaders = new ArrayList<>();

    RecyclerView recyclerViewTiming;

    ProgressBar mProgressBar;
    List<OrgDetailModel> childList;
    List<OrgTimingModel>
            timeList = new ArrayList<>();

    DbHelper dbHelper;
    String org_timing;
    RelativeLayout rl_head, rl_header;

    TextView header_timing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisation_detail);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#f9af10"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#f9af10"));
            getWindow().setNavigationBarColor(Color.parseColor("#f9af10"));

        }
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        TextView myActionBarTitle;
        myActionBarTitle = findViewById(R.id.myActionBarTitle);
        myActionBarTitle.setText(getIntent().getStringExtra("orgName"));
        myActionBarTitle.setAllCaps(true);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f9af10")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrganisationDetailActivity.this, HomeActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();
            }
        });

        // getSupportActionBar().setTitle("ORGANISATIONS");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);

        mProgressBar = findViewById(R.id.google_progress_org);
        mProgressBar.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(this)
                .build());
        rl_head = findViewById(R.id.rl_head);
        rl_header = findViewById(R.id.rl_header_timing);

        header_timing = findViewById(R.id.header_timing);

        recyclerView = findViewById(R.id.recyclerView_orgDetail);
        recyclerViewTiming = findViewById(R.id.recyclerView_orgTiming);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerViewTiming.setNestedScrollingEnabled(false);

        if (isConnected(this)) {

            //mySection = new MySection(dataList,this);
            //mAdapter = new OrgDetailAdapter(dataList,this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setVisibility(View.VISIBLE);

            RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());

            recyclerViewTiming.setLayoutManager(mLayoutManager1);
            recyclerViewTiming.setItemAnimator(new DefaultItemAnimator());
            Log.e("org number",getIntent().getStringExtra("orgNumber"));
            getData(getIntent().getStringExtra("orgNumber"));

            if (getIntent().getStringExtra("orgNumber").equals("2")) {

                Log.e("Timing number", "1");
                //  mAdapter = new OrgDetailAdapter(timeList,this);
                mAdapter = new OrgDetailAdapter(timeList, this);

                recyclerViewTiming.setAdapter(mAdapter);
                org_timing = "1";
                getTiming(org_timing);
            }
            else {

                rl_header.setVisibility(View.GONE);

            }if (getIntent().getStringExtra("orgNumber").equals("3")) {
                // mAdapter = new OrgDetailAdapter(timeList,this);
                Log.e("Timing number", "2");
                //  mAdapter = new OrgDetailAdapter(timeList,this);
                mAdapter = new OrgDetailAdapter(timeList, this);

                recyclerViewTiming.setAdapter(mAdapter);
                org_timing = "2";
                getTiming(org_timing);

            }
            else {

                rl_header.setVisibility(View.GONE);

            }if (getIntent().getStringExtra("orgNumber").equals("5")) {
                Log.e("Timing number", "3");
                //  mAdapter = new OrgDetailAdapter(timeList,this);
                mAdapter = new OrgDetailAdapter(timeList, this);

                recyclerViewTiming.setAdapter(mAdapter);
                org_timing = "3";
                getTiming(org_timing);

            }
            else {

                rl_header.setVisibility(View.GONE);

            }if (getIntent().getStringExtra("orgNumber").equals("6")) {
                Log.e("Timing number", "4");
                //  mAdapter = new OrgDetailAdapter(timeList,this);
                mAdapter = new OrgDetailAdapter(timeList, this);

                recyclerViewTiming.setAdapter(mAdapter);
                org_timing = "4";
                getTiming(org_timing);

            } else {

                rl_header.setVisibility(View.GONE);

            }


        } else {
            try {

                mProgressBar.setVisibility(View.INVISIBLE);

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
//            listView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                dbHelper = new DbHelper(this);

                List<OrgSectionModel> contacts = dbHelper.getSections(getIntent().getStringExtra("orgNumber"));
                //OrgSectionModel orgSectionModel = new OrgSectionModel();
                int i = 0;
                for (OrgSectionModel orgSectionModel : contacts) {
                    String log = "Id: " + orgSectionModel.getSection();
                    Log.d("Id: ", log);
                    i++;
                    // Writing Contacts to log
                    childList = new ArrayList<>();
                    List<OrgDetailModel> contactDetails = dbHelper.getOrgList("" + orgSectionModel.getSection(), getIntent().getStringExtra("orgNumber"));
                    sectionHeaders.add(new SectionHeader(childList, orgSectionModel.getSection(), i));

                    for (OrgDetailModel cn : contactDetails) {

                        orgDetailModel = new OrgDetailModel(cn.getName(), cn.getPhone(), cn.getDesignation(), "", "");
//                    orgDetailModel.setName(cn.getName());
//                    orgDetailModel.setDesignation(cn.getDesignation());
//                    orgDetailModel.setPhone(cn.getPhone());
                        childList.add(orgDetailModel);
                        String data = "Id: " + cn.getName() + " ,Name: " + cn.getDesignation() + " ,Phone: " + cn.getPhone();
                        // Log.d("Name: ", data );
                    }
//                Log.e("ChildList",childList.get(0).getName()+"section"+orgSectionModel.getSection()+"i="+i);


                }
                adapterRecycler = new OrgSectionRecycler(getApplicationContext(), sectionHeaders);
                recyclerView.setAdapter(adapterRecycler);
                adapterRecycler.notifyDataSetChanged();

                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext());

                recyclerViewTiming.setLayoutManager(mLayoutManager1);
                recyclerViewTiming.setItemAnimator(new DefaultItemAnimator());
                if (getIntent().getStringExtra("orgNumber").equals("2")) {

                    org_timing = "1";
                    mAdapter = new OrgDetailAdapter(timeList, this);
                    Log.e("Timing number", "1");
                    recyclerViewTiming.setAdapter(mAdapter);
                    getTime(org_timing);

                } else if (getIntent().getStringExtra("orgNumber").equals("3")) {
                    // mAdapter = new OrgDetailAdapter(timeList,this);
                    mAdapter = new OrgDetailAdapter(timeList, this);
                    Log.e("Timing number", "2");
                    recyclerViewTiming.setAdapter(mAdapter);
                    org_timing = "2";
                    getTime(org_timing);


                } else if (getIntent().getStringExtra("orgNumber").equals("5")) {
                    // mAdapter = new OrgDetailAdapter(timeList,this);
                    mAdapter = new OrgDetailAdapter(timeList, this);
                    Log.e("Timing number", "3");
                    recyclerViewTiming.setAdapter(mAdapter);
                    org_timing = "3";

                        // Log.e("Image member",cn.getMemberimagepath());
                        //                    orgDetailModel.setName(cn.getName());
//                    orgDetailModel.setDesignation(cn.getDesignation());
//                    orgDetailModel.setPhone(cn.getPhone());
                        getTime(org_timing);


                } else if (getIntent().getStringExtra("orgNumber").equals("6")) {
                    Log.e("Timing number", "4");
                    //  mAdapter = new OrgDetailAdapter(timeList,this);
                    mAdapter = new OrgDetailAdapter(timeList, this);

                    recyclerViewTiming.setAdapter(mAdapter);
                    org_timing = "4";
                    getTime(org_timing);
//                    List<OrgTimingModel> timings = dbHelper.getOrgTiming(org_timing);
//                    //listView_MemberChild.setAdapter(childCursorAdapter);
//                    for (OrgTimingModel cn : timings) {
//
//                        orgTimingModel = new OrgTimingModel(cn.getName(),
//                                cn.getPhone(),
//                                cn.getTiming_number(),
//                                cn.getMember_type()
//
//                        );
//                        // Log.e("Image member",cn.getMemberimagepath());
//                        //                    orgDetailModel.setName(cn.getName());
////                    orgDetailModel.setDesignation(cn.getDesignation());
////                    orgDetailModel.setPhone(cn.getPhone());
//                        timeList.add(orgTimingModel);
                    }

                 else {

                    rl_header.setVisibility(View.GONE);

                }


//                Collections.sort(dataList, new Comparator<ParishMemberModel>() {
//                    @Override
//                    public int compare(ParishMemberModel lhs, ParishMemberModel rhs) {
//                        return lhs.getMembername().compareTo(rhs.getMembername());
//                    }
//                });

                //dbHelper = new DbHelper(this);
//            rl_head.setVisibility(View.VISIBLE);
//
//            listView.setAdapter(orgCursorAdapter);
//            listView.setVisibility(View.VISIBLE);
                //getLoaderManager().initLoader(0, null, this);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception", e.getMessage());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }

    public void getTime(String org_timing)
    {
        List<OrgTimingModel> timings = dbHelper.getOrgTiming(org_timing);
        //listView_MemberChild.setAdapter(childCursorAdapter);
        rl_header.setVisibility(View.VISIBLE);

        for (OrgTimingModel cn : timings) {

            orgTimingModel = new OrgTimingModel(cn.getName(),
                    cn.getPhone(),
                    cn.getTiming_number(),
                    cn.getMember_type()

            );
            header_timing.setText(cn.getMember_type());



            Log.e("Values","Timing type"+cn.getName()+"Timing"+cn.getPhone()+cn.getTiming_number()+cn.getMember_type());
            // Log.e("Image member",cn.getMemberimagepath());
            //                    orgDetailModel.setName(cn.getName());
//                    orgDetailModel.setDesignation(cn.getDesignation());
//                    orgDetailModel.setPhone(cn.getPhone());
            timeList.add(orgTimingModel);
        }
        mAdapter.notifyDataSetChanged();

    }

    public static boolean isConnected(Context context) {
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    private void getTiming(final String orgNumber) {
        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getOrgTiming(orgNumber);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.e("URL", "=====" + response.raw().request().url());

                //Log.e("getting our vicar", ""+response.body().toString());

                if (response.isSuccessful() && response.body() != null) {
                    try {

                        JSONObject jsonObject = new JSONObject(response.body().toString());

                        if (jsonObject.has("Data")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("Data");

                            //Log.e("Array Length",""+jsonArray.length());


                                //Log.e("Member Type",""+jsonArray1.getJSONObject(0).getString("Member_type"));

                                if (jsonArray.length() > 0) {
                                    rl_header.setVisibility(View.VISIBLE);

                                    for (int j = 0; j < jsonArray.length(); j++) {
                                        JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                                        orgTimingModel = new OrgTimingModel(jsonObject1.getString("Practice")
                                                , jsonObject1.getString("Meeting"),
                                                orgNumber,
                                                jsonObject1.getString("Member_type")

                                        );

                                        header_timing.setText(jsonObject1.getString("Member_type"));

//                                    orgDetailModel.setName(jsonObject1.getString("Name"));
//                                    orgDetailModel.setPhone(jsonObject1.getString("Phone"));
//                                    orgDetailModel.setDesignation(jsonObject1.getString("Designation"));
//                                    orgDetailModel.setOrgNumber(orgNumber);
//                                    orgDetailModel.setMemberType(jsonObject1.getString("Member_type")
//                                    );
                                        timeList.add(orgTimingModel);

                                        // Log.e("Name", "Name=" + jsonObject1.getString("Name") + " Phone" + jsonObject1.getString("Phone"));
                                        //dataList.add(orgDetailModel);
                                    }
                                    mAdapter.notifyDataSetChanged();

                                }




                        } else {
                            Toast.makeText(OrganisationDetailActivity.this, "data not found", Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("Exception timing", e.getMessage());

                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    private void getData(final String orgNumber) {
        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getOrgDetail(orgNumber);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //mProgressBar.setVisibility(View.INVISIBLE);
                Log.d("URL", "=====" + response.raw().request().url());

                //Log.e("getting our vicar", ""+response.body().toString());

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        mProgressBar.setVisibility(View.INVISIBLE);

                        JSONObject jsonObject = new JSONObject(response.body().toString());

                        if (jsonObject.has("Data")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("Data");

                            //Log.e("Array Length",""+jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                                //Log.e("Member Type",""+jsonArray1.getJSONObject(0).getString("Member_type"));
                                childList = new ArrayList<>();

                                if (jsonArray1.length() > 0) {
                                    for (int j = 0; j < jsonArray1.length(); j++) {
                                        JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                                        orgDetailModel = new OrgDetailModel(jsonObject1.getString("Name")
                                                , jsonObject1.getString("Phone")
                                                , jsonObject1.getString("Designation"),
                                                orgNumber,
                                                jsonObject1.getString("Member_type")
                                        );


//                                    orgDetailModel.setName(jsonObject1.getString("Name"));
//                                    orgDetailModel.setPhone(jsonObject1.getString("Phone"));
//                                    orgDetailModel.setDesignation(jsonObject1.getString("Designation"));
//                                    orgDetailModel.setOrgNumber(orgNumber);
//                                    orgDetailModel.setMemberType(jsonObject1.getString("Member_type")
//                                    );
                                        childList.add(orgDetailModel);

                                        // Log.e("Name", "Name=" + jsonObject1.getString("Name") + " Phone" + jsonObject1.getString("Phone"));
                                        //dataList.add(orgDetailModel);
                                    }
                                    sectionHeaders.add(new SectionHeader(childList, jsonArray1.getJSONObject(0).getString("Member_type"), i));
                                }


                            }
                            adapterRecycler = new OrgSectionRecycler(getApplicationContext(), sectionHeaders);
                            recyclerView.setAdapter(adapterRecycler);
                            adapterRecycler.notifyDataSetChanged();

                        } else {
                            Toast.makeText(OrganisationDetailActivity.this, "No data found", Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }


}
