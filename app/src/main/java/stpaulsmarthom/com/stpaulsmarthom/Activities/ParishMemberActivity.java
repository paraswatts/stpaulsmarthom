package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.jpardogo.android.googleprogressbar.library.ChromeFloatingCirclesDrawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stpaulsmarthom.com.stpaulsmarthom.APIService;
import stpaulsmarthom.com.stpaulsmarthom.APIUrl;
import stpaulsmarthom.com.stpaulsmarthom.Adapters.ParishMemberAdapter;
import stpaulsmarthom.com.stpaulsmarthom.AsyncTaskRunner;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbContract;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbHelper;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ChildModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ParishMemberModel;
import stpaulsmarthom.com.stpaulsmarthom.R;
import stpaulsmarthom.com.stpaulsmarthom.services.DownloadService;
import stpaulsmarthom.com.stpaulsmarthom.services.FetchParishDataService;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_ACTIVE;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_AGE;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_AREA;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_AREA_NAME;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_BLOCK;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_BLOOD;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_BLOOD_WIFE;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_BUILDING_NO;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_COMPANY;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_CPRNO;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_DISTRICT;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_DOB;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_DOB_WIFE;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_DOM;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_EMERGENCY_NAME;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_EMER_INDIA_NAME;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_EMP_NAME_WIFE;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_EMP_WIFE;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_FAX;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_FLAT_NO;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_HOME;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_HOUSE_NAME_INDIA;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_IN_BAHRAIN_WIFE;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_MAILING;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_MAILING_ADDRESS;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_MARITAL;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_MOB1;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_MOB2;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_MOB3;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_MOB_CODE;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_NAME;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_NATIVE_WIFE;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_PIN;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_PO;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_ROAD;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_ROLL_NO;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_STATE;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_TEL;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_TELEPHONE_RES1;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_TELEPHONE_RES2;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_TELEPHONE_RES3;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_TEL_CODE_INDIA;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_TEL_EMER;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_TEL_EMER_INDIA;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_TOWN_INDIA;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.MEMBER_WIFENAME;
import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.TABLE_CHILD;

public class ParishMemberActivity extends AppCompatActivity {
    private List<ParishMemberModel> dataList = new ArrayList<>();

    private ParishMemberAdapter mAdapter;
    RecyclerView recyclerView;
    ParishMemberModel parishMemberModel,parishMemberModel1;
    ProgressBar mProgressBar;
    EditText search_name;
    EditText search_roll;
    EditText search_content;
    ChildModel childModel;
    private String mNameFilter;
    private String mContentFilter;
    private String mRollFilter;
    ImageView actionLogout;
    private List<ChildModel> childModelList = new ArrayList<>();

    DbHelper dbHelper;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parish_list);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#eb4142"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#eb4142"));
            getWindow().setNavigationBarColor(Color.parseColor("#eb4142"));
        }
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout_parish);

        TextView myActionBarTitle;
        myActionBarTitle = findViewById(R.id.myActionBarTitle1);
        myActionBarTitle.setText("PARISH MEMBERS");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eb4142")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("PARISH MEMBER DIRECTORY");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);

        actionLogout = findViewById(R.id.actionLogout);
        actionLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("LoginParish",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove("user");
                editor.remove("pass");
                editor.commit();
                Intent intent = new Intent(ParishMemberActivity.this,EDirectoryActivity.class);
                startActivity(intent);
                finish();
            }
        });
//        ImageView iv_home = findViewById(R.id.home1);
//        iv_home.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ParishMemberActivity.this,HomeActivity.class);
//      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();
//            }
//        });
        search_name = findViewById(R.id.search_name);
        search_roll = findViewById(R.id.search_roll);
        search_content = findViewById(R.id.search_content);

        //listView = (ListView) findViewById(R.id.listView_Parish);
        dbHelper = new DbHelper(this);
        mProgressBar = findViewById(R.id.google_progress_parish);
        mProgressBar.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(this)
                .build());
        recyclerView = findViewById(R.id.recyclerView_Parish) ;
       // mProgressBar.setVisibility(View.GONE);
        if (isConnected(this)) {


            recyclerView.setVisibility(View.VISIBLE);
            //listView.setVisibility(View.GONE);
            mAdapter = new ParishMemberAdapter(dataList, ParishMemberActivity.this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setItemViewCacheSize(20);
            recyclerView.setHasFixedSize(true);
            recyclerView.setDrawingCacheEnabled(true);
            recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
            DividerItemDecoration itemDecor = new DividerItemDecoration(this, VERTICAL);
            recyclerView.addItemDecoration(itemDecor);
            recyclerView.setAdapter(mAdapter);
            SharedPreferences preferences = getSharedPreferences("LoginParish",Context.MODE_PRIVATE);
            final String user = preferences.getString("user","");
            final String pwd = preferences.getString("pass","");
            Intent intent =  new Intent(ParishMemberActivity.this,FetchParishDataService.class);
            intent.putExtra("user",user);
            intent.putExtra("pwd",pwd);
            startService(intent);
            getData(user, pwd);

            addTextListener();
        }
        else{
            try {

                actionLogout.setVisibility(View.GONE);
                mAdapter = new ParishMemberAdapter(dataList, ParishMemberActivity.this);
                search_content.setEnabled(true);
                search_name.setEnabled(true);
                search_roll.setEnabled(true);
                mProgressBar.setVisibility(View.GONE);
                recyclerView.setItemViewCacheSize(20);
                recyclerView.setHasFixedSize(true);
                recyclerView.setDrawingCacheEnabled(true);
                recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
                recyclerView.setVisibility(View.VISIBLE);
                //listView.setVisibility(View.VISIBLE);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                DividerItemDecoration itemDecor = new DividerItemDecoration(this, VERTICAL);
                recyclerView.addItemDecoration(itemDecor);
                recyclerView.setAdapter(mAdapter);
                //recyclerView.setAdapter(mAdapter);
                List<ParishMemberModel> contacts = dbHelper.getParishList();
                //listView_MemberChild.setAdapter(childCursorAdapter);
                for (ParishMemberModel cn : contacts) {

                    parishMemberModel = new ParishMemberModel(cn.getMembername(),
                            cn.getMembernamerollno(),
                            cn.getMembernamearea(),
                            cn.getMemberyears(),
                            cn.getMemberblood(),
                            cn.getMemberdate(),
                            cn.getMembermarital(),
                            cn.getMemberdatemarriage(),
                            cn.getCprno(),
                            cn.getCompanyname(),
                            cn.getMailingadress(),
                            cn.getTelephone(),
                            cn.getFax(),
                            cn.getHomeparirsh(),
                            cn.getFlatno(),
                            cn.getBlgdno(),
                            cn.getRoad(),
                            cn.getBlock(),
                            cn.getArea(),
                            cn.getEmergencyname(),
                            cn.getTelephoneemergency(),
                            cn.getHousenameindia(),
                            cn.getPostofficeindia(),
                            cn.getDistrictindia(),
                            cn.getStateindia(),
                            cn.getTelcodeindia(),
                            cn.getTownindia(),
                            cn.getPinindia(),
                            cn.getEmergencyindianame(),
                            cn.getTelindiaemergency(),
                            cn.getWifename(),
                            cn.getDatebrithfamily(),
                            cn.getInbaharahianfamily(),
                            cn.getEmployefamily(),
                            cn.getEmaploynamefamily(),
                            cn.getNativefamily(),
                            cn.getBloodfamily(),
                            cn.getActive(),
                            cn.getMemberimagepath(),
                            cn.getWife_image(),
                            cn.getTelephoneres1(),
                            cn.getTelephonemob1(),
                            cn.getTelephoneres2(),
                            cn.getTelephonemob2(),
                            cn.getTelephoneres3(),
                            cn.getTelephonemob3(),
                            cn.getMobcodeindia(),
                            cn.getMailingadresss()

                    );
                    // Log.e("Image member",cn.getMemberimagepath());
                    //                    orgDetailModel.setName(cn.getName());
//                    orgDetailModel.setDesignation(cn.getDesignation());
//                    orgDetailModel.setPhone(cn.getPhone());
                    dataList.add(parishMemberModel);
                }
//                Collections.sort(dataList, new Comparator<ParishMemberModel>() {
//                    @Override
//                    public int compare(ParishMemberModel lhs, ParishMemberModel rhs) {
//                        return lhs.getMembername().compareTo(rhs.getMembername());
//                    }
//                });
                mAdapter.notifyDataSetChanged();
                //listView.setAdapter(parishCursorAdapter);

//            parishCursorAdapter.setFilterQueryProvider(new FilterQueryProvider() {
//                @Override
//                public Cursor runQuery(CharSequence constraint) {
//
//                    return null;
//                }
//            });
//            getLoaderManager().initLoader(0, null, this);
//            addTextListenerOff();

                addTextListener();
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
    private void saveData(String user,String pwd) {

        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getParishMemberDirectory(user,pwd);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //Log.d("URL", "=====" + response.raw().request().url());

                //Log.e("getting about us", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {
//                        dbHelper.deleteTable(TABLE_CHILD);
//
//                        dbHelper.deleteTable(DbContract.DbEntry.TABLE_PARISH);

//                        search_content.setEnabled(true);
//                        search_name.setEnabled(true);
//                        search_roll.setEnabled(true);
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        // Log.e("Success message",jsonObject.getString("success"));

                        // Log.e("Success is" , " "+ jsonObject.get("success"));
                        if (jsonObject.has("Data")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("Data");


                            for (int i = 0; i < jsonArray.length(); i++) {
                                final JSONObject jsonObject1 = jsonArray.getJSONObject(i);

//                            final String name = jsonObject1.getString("memberimagepath");
//                            final String actualImage = name.replace("images/","");
//
//                            final String imageUrl= "http://stpaulsmarthomabahrain.com/membershipform/"+jsonObject1.getString("memberimagepath");
//
//                            //File path = getOutputMediaFile("Contact",name);
//
//
//                            File sampleDir = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain"+"/"+"ParishMember");
//                            if (!sampleDir.exists()) {
//                                if (!sampleDir.mkdirs()) {
//                                    return;
//                                }
//                            }
//                            // Log.e("Saving Image", "");
//                            File mediaFile;
//                            String mImageName = actualImage;
//                            mediaFile = new File(sampleDir.getPath() + File.separator + mImageName);
//                            if(!mediaFile.exists()) {
//                                // new DownloadThread(imageUrl,mediaFile.getAbsolutePath(),10).run();;
////                                Intent intent = new Intent(ParishMemberActivity.this, DownloadService.class);
////                                intent.putExtra("imageUrl",imageUrl);
////                                intent.putExtra("imagePath",mediaFile.getAbsolutePath());
////                                startService(intent);
//
////                                Intent intent = new Intent(FetchDataService.context, DownloadService.class);
////                                intent.putExtra("imageUrl",imageUrl);
////                                intent.putExtra("imagePath",mediaFile.getAbsolutePath());
////                                startService(intent);
//                                //new AsyncTaskRunner().execute(imageUrl, mediaFile.getAbsolutePath());
//                            }
//                            else
//                            {
//                                // Log.e("File check","Already Exists");
//                            }
//
//                            //------Wife Image===========//
//
//                            final String name1 = jsonObject1.getString("wife_image");
//                            final String actualImage1 = name1.replace("images/","");
//                            final String imageUrl1= "http://stpaulsmarthomabahrain.com/membershipform/"+jsonObject1.getString("wife_image");
//
//                            //File path = getOutputMediaFile("Contact",name);
//
//
//                            File sampleDir1 = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain"+"/"+"ParishMemberWife");
//                            if (!sampleDir1.exists()) {
//                                if (!sampleDir1.mkdirs()) {
//                                    return;
//                                }
//                            }
//                            // Log.e("Saving Image", "");
//                            File mediaFile1;
//                            String mImageName1 = actualImage1;
//                            mediaFile1 = new File(sampleDir1.getPath() + File.separator + mImageName1);
//                            if(!mediaFile1.exists()) {
//                                //  new DownloadThread(imageUrl,mediaFile.getAbsolutePath(),10).run();;
////                                Intent intent = new Intent(ParishMemberActivity.this, DownloadService.class);
////                                intent.putExtra("imageUrl",imageUrl1);
////                                intent.putExtra("imagePath",mediaFile1.getAbsolutePath());
////                                startService(intent);
//
////                                Intent intent = new Intent(FetchDataService.context, DownloadService.class);
////                                intent.putExtra("imageUrl",imageUrl1);
////                                intent.putExtra("imagePath",mediaFile1.getAbsolutePath());
////                                startService(intent);
//                               //new AsyncTaskRunner().execute(imageUrl1, mediaFile1.getAbsolutePath());
//                            }
//                            else
//                            {
//                                //Log.e("File check","Already Exists");
//                            }

                                //getChildData(jsonObject1.getString("membernamerollno"));


                                parishMemberModel = new ParishMemberModel(
                                        jsonObject1.getString("membername")
                                        , jsonObject1.getString("membernamerollno")
                                        , jsonObject1.getString("membernamearea")
                                        , jsonObject1.getString("memberyears")
                                        , jsonObject1.getString("memberblood")
                                        , jsonObject1.getString("memberdate")
                                        , jsonObject1.getString("membermarital")
                                        , jsonObject1.getString("memberdatemarriage")
                                        , jsonObject1.getString("cprno")
                                        , jsonObject1.getString("companyname")
                                        , jsonObject1.getString("mailingadress")
                                        , jsonObject1.getString("telephone")
                                        , jsonObject1.getString("fax")
                                        , jsonObject1.getString("homeparirsh")
                                        , jsonObject1.getString("flatno")
                                        , jsonObject1.getString("blgdno")
                                        , jsonObject1.getString("road")
                                        , jsonObject1.getString("block")
                                        , jsonObject1.getString("area")
                                        , jsonObject1.getString("emergencyname")
                                        , jsonObject1.getString("telephoneemergency")
                                        , jsonObject1.getString("housenameindia")
                                        , jsonObject1.getString("postofficeindia")
                                        , jsonObject1.getString("districtindia")
                                        , jsonObject1.getString("stateindia")
                                        , jsonObject1.getString("telcodeindia")
                                        , jsonObject1.getString("townindia")
                                        , jsonObject1.getString("pinindia")
                                        , jsonObject1.getString("emergencyindianame")
                                        , jsonObject1.getString("telindiaemergency")
                                        , jsonObject1.getString("wifename")
                                        , jsonObject1.getString("datebrithfamily")
                                        , jsonObject1.getString("inbaharahianfamily")
                                        , jsonObject1.getString("employefamily")
                                        , jsonObject1.getString("emaploynamefamily")
                                        , jsonObject1.getString("nativefamily")
                                        , jsonObject1.getString("bloodfamily")
                                        , jsonObject1.getString("active")
                                        , jsonObject1.getString("memberimagepath")
                                        , jsonObject1.getString("wife_image")
                                        , jsonObject1.getString("telephoneres1")
                                        , jsonObject1.getString("telephonemob1")
                                        , jsonObject1.getString("telephoneres2")
                                        , jsonObject1.getString("telephonemob2")
                                        , jsonObject1.getString("telephoneres3")
                                        , jsonObject1.getString("telephonemob3")
                                        , jsonObject1.getString("mobcodeindia")
                                        , jsonObject1.getString("mailingadresss"));

                                //dataList.add(parishMemberModel);

                                dbHelper.addParishMember(parishMemberModel);

                            }
//                        Collections.sort(dataList, new Comparator<ParishMemberModel>() {
//                            @Override
//                            public int compare(ParishMemberModel lhs, ParishMemberModel rhs) {
//                                return lhs.getMembername().compareTo(rhs.getMembername());
//                            }
//                        });
//                        mProgressBar.setVisibility(View.INVISIBLE);
//
//                        mAdapter.notifyDataSetChanged();
                        }
                        else
                        {
                        }

                        } catch(Exception e){
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

    private void getData(final String user,final String pwd) {

//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        //progressDialog.setProgressDrawable(getDrawable(R.drawable.parish_progress));
//        progressDialog.show();

        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getParishMemberDirectory(user,pwd);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //Log.d("URL", "=====" + response.raw().request().url());

                //Log.e("getting about us", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {
//                        dbHelper.deleteTable(TABLE_CHILD);
//
//                        dbHelper.deleteTable(DbContract.DbEntry.TABLE_PARISH);
                        //progressDialog.dismiss();
                            mProgressBar.setVisibility(View.GONE);
                        //dbHelper.deleteTable(TABLE_CHILD);
                        //dbHelper.deleteTable(DbContract.DbEntry.TABLE_PARISH);

//                        search_content.setEnabled(true);
//                        search_name.setEnabled(true);
//                        search_roll.setEnabled(true);
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                       // Log.e("Success message",jsonObject.getString("success"));

                       // Log.e("Success is" , " "+ jsonObject.get("success"));
                        if(jsonObject.has("Data")) {
                            search_content.setEnabled(true);
                            search_name.setEnabled(true);
                            search_roll.setEnabled(true);
                            JSONArray jsonArray = jsonObject.getJSONArray("Data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                final JSONObject jsonObject1 = jsonArray.getJSONObject(i);

//                            final String name = jsonObject1.getString("memberimagepath");
//                            final String actualImage = name.replace("images/","");
//
//                            final String imageUrl= "http://stpaulsmarthomabahrain.com/membershipform/"+jsonObject1.getString("memberimagepath");
//
//                            //File path = getOutputMediaFile("Contact",name);
//
//
//                            File sampleDir = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain"+"/"+"ParishMember");
//                            if (!sampleDir.exists()) {
//                                if (!sampleDir.mkdirs()) {
//                                    return;
//                                }
//                            }
//                            // Log.e("Saving Image", "");
//                            File mediaFile;
//                            String mImageName = actualImage;
//                            mediaFile = new File(sampleDir.getPath() + File.separator + mImageName);
//                            if(!mediaFile.exists()) {
//                                // new DownloadThread(imageUrl,mediaFile.getAbsolutePath(),10).run();;
////                                Intent intent = new Intent(ParishMemberActivity.this, DownloadService.class);
////                                intent.putExtra("imageUrl",imageUrl);
////                                intent.putExtra("imagePath",mediaFile.getAbsolutePath());
////                                startService(intent);
//
////                                Intent intent = new Intent(FetchDataService.context, DownloadService.class);
////                                intent.putExtra("imageUrl",imageUrl);
////                                intent.putExtra("imagePath",mediaFile.getAbsolutePath());
////                                startService(intent);
//                                //new AsyncTaskRunner().execute(imageUrl, mediaFile.getAbsolutePath());
//                            }
//                            else
//                            {
//                                // Log.e("File check","Already Exists");
//                            }
//
//                            //------Wife Image===========//
//
//                            final String name1 = jsonObject1.getString("wife_image");
//                            final String actualImage1 = name1.replace("images/","");
//                            final String imageUrl1= "http://stpaulsmarthomabahrain.com/membershipform/"+jsonObject1.getString("wife_image");
//
//                            //File path = getOutputMediaFile("Contact",name);
//
//
//                            File sampleDir1 = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain"+"/"+"ParishMemberWife");
//                            if (!sampleDir1.exists()) {
//                                if (!sampleDir1.mkdirs()) {
//                                    return;
//                                }
//                            }
//                            // Log.e("Saving Image", "");
//                            File mediaFile1;
//                            String mImageName1 = actualImage1;
//                            mediaFile1 = new File(sampleDir1.getPath() + File.separator + mImageName1);
//                            if(!mediaFile1.exists()) {
//                                //  new DownloadThread(imageUrl,mediaFile.getAbsolutePath(),10).run();;
////                                Intent intent = new Intent(ParishMemberActivity.this, DownloadService.class);
////                                intent.putExtra("imageUrl",imageUrl1);
////                                intent.putExtra("imagePath",mediaFile1.getAbsolutePath());
////                                startService(intent);
//
////                                Intent intent = new Intent(FetchDataService.context, DownloadService.class);
////                                intent.putExtra("imageUrl",imageUrl1);
////                                intent.putExtra("imagePath",mediaFile1.getAbsolutePath());
////                                startService(intent);
//                               //new AsyncTaskRunner().execute(imageUrl1, mediaFile1.getAbsolutePath());
//                            }
//                            else
//                            {
//                                //Log.e("File check","Already Exists");
//                            }


                                parishMemberModel = new ParishMemberModel(
                                        jsonObject1.getString("membername")
                                        , jsonObject1.getString("membernamerollno")
                                        , jsonObject1.getString("membernamearea")
                                        , jsonObject1.getString("memberyears")
                                        , jsonObject1.getString("memberblood")
                                        , jsonObject1.getString("memberdate")
                                        , jsonObject1.getString("membermarital")
                                        , jsonObject1.getString("memberdatemarriage")
                                        , jsonObject1.getString("cprno")
                                        , jsonObject1.getString("companyname")
                                        , jsonObject1.getString("mailingadress")
                                        , jsonObject1.getString("telephone")
                                        , jsonObject1.getString("fax")
                                        , jsonObject1.getString("homeparirsh")
                                        , jsonObject1.getString("flatno")
                                        , jsonObject1.getString("blgdno")
                                        , jsonObject1.getString("road")
                                        , jsonObject1.getString("block")
                                        , jsonObject1.getString("area")
                                        , jsonObject1.getString("emergencyname")
                                        , jsonObject1.getString("telephoneemergency")
                                        , jsonObject1.getString("housenameindia")
                                        , jsonObject1.getString("postofficeindia")
                                        , jsonObject1.getString("districtindia")
                                        , jsonObject1.getString("stateindia")
                                        , jsonObject1.getString("telcodeindia")
                                        , jsonObject1.getString("townindia")
                                        , jsonObject1.getString("pinindia")
                                        , jsonObject1.getString("emergencyindianame")
                                        , jsonObject1.getString("telindiaemergency")
                                        , jsonObject1.getString("wifename")
                                        , jsonObject1.getString("datebrithfamily")
                                        , jsonObject1.getString("inbaharahianfamily")
                                        , jsonObject1.getString("employefamily")
                                        , jsonObject1.getString("emaploynamefamily")
                                        , jsonObject1.getString("nativefamily")
                                        , jsonObject1.getString("bloodfamily")
                                        , jsonObject1.getString("active")
                                        , jsonObject1.getString("memberimagepath")
                                        , jsonObject1.getString("wife_image")
                                        , jsonObject1.getString("telephoneres1")
                                        , jsonObject1.getString("telephonemob1")
                                        , jsonObject1.getString("telephoneres2")
                                        , jsonObject1.getString("telephonemob2")
                                        , jsonObject1.getString("telephoneres3")
                                        , jsonObject1.getString("telephonemob3")
                                        , jsonObject1.getString("mobcodeindia")
                                        , jsonObject1.getString("mailingadresss"));

                                dataList.add(parishMemberModel);

                                //dbHelper.addParishMember(parishMemberModel);
                                //getChildData(jsonObject1.getString("membernamerollno"));

                            }
//                        Collections.sort(dataList, new Comparator<ParishMemberModel>() {
//                            @Override
//                            public int compare(ParishMemberModel lhs, ParishMemberModel rhs) {
//                                return lhs.getMembername().compareTo(rhs.getMembername());
//                            }
//                        });
                            mProgressBar.setVisibility(View.INVISIBLE);

                            mAdapter.notifyDataSetChanged();


                        }
                        else {
                            Toast.makeText(ParishMemberActivity.this, "No data found", Toast.LENGTH_SHORT).show();


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        //Log.e("Exception Image",""+e.getMessage());
                    }
                    finally {
                        //saveData(user, pwd);
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


    public void addTextListener(){

        search_name.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                List<ParishMemberModel> filteredList = new ArrayList<>();

                for (int i = 0; i < dataList.size(); i++) {

                    final String name = dataList.get(i).getMembername().toLowerCase();

                        if (name.contains(query)) {

                            filteredList.add(dataList.get(i));
                        }


                }

                recyclerView.setLayoutManager(new LinearLayoutManager(ParishMemberActivity.this));
                mAdapter = new ParishMemberAdapter(filteredList, ParishMemberActivity.this);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();  // data set changed
            }
        });

        search_roll.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                List<ParishMemberModel> filteredList = new ArrayList<>();

                for (int i = 0; i < dataList.size(); i++) {

                    final String rollno = dataList.get(i).getMembernamerollno().toLowerCase();

                        if (rollno.contains(query)) {

                            filteredList.add(dataList.get(i));
                        }

                }

                recyclerView.setLayoutManager(new LinearLayoutManager(ParishMemberActivity.this));
                mAdapter = new ParishMemberAdapter(filteredList, ParishMemberActivity.this);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();  // data set changed
            }
        });

        search_content.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                List<ParishMemberModel> filteredList = new ArrayList<>();

                for (int i = 0; i < dataList.size(); i++) {

                    final String rollno = dataList.get(i).getMembernamerollno().toLowerCase()
                            +dataList.get(i).getMembername().toLowerCase()
                            +dataList.get(i).getMembernamearea().toLowerCase()
                            +dataList.get(i).getMemberyears().toLowerCase()
                            +dataList.get(i).getMemberblood().toLowerCase()
                            +dataList.get(i).getMemberdate().toLowerCase()
                            +dataList.get(i).getMembermarital().toLowerCase()
                            +dataList.get(i).getMemberdatemarriage().toLowerCase()
                            +dataList.get(i).getCprno().toLowerCase()
                            +dataList.get(i).getCompanyname().toLowerCase()
                            +dataList.get(i).getMailingadress().toLowerCase()
                            +dataList.get(i).getTelephone().toLowerCase()
                            +dataList.get(i).getFax().toLowerCase()
                            +dataList.get(i).getHomeparirsh().toLowerCase()
                            +dataList.get(i).getFlatno().toLowerCase()
                            +dataList.get(i).getBlgdno().toLowerCase()
                            +dataList.get(i).getRoad().toLowerCase()
                            +dataList.get(i).getBlock().toLowerCase()
                            +dataList.get(i).getArea().toLowerCase()
                            +dataList.get(i).getEmergencyname().toLowerCase()
                            +dataList.get(i).getTelephoneemergency().toLowerCase()
                            +dataList.get(i).getHousenameindia().toLowerCase()
                            +dataList.get(i).getPostofficeindia().toLowerCase()
                            +dataList.get(i).getDistrictindia().toLowerCase()
                            +dataList.get(i).getStateindia().toLowerCase()
                            +dataList.get(i).getTelcodeindia().toLowerCase()
                            +dataList.get(i).getTownindia().toLowerCase()
                            +dataList.get(i).getPinindia().toLowerCase()
                            +dataList.get(i).getEmergencyindianame().toLowerCase()
                            +dataList.get(i).getTelindiaemergency().toLowerCase()
                            +dataList.get(i).getWifename().toLowerCase()
                            +dataList.get(i).getDatebrithfamily().toLowerCase()
                            +dataList.get(i).getInbaharahianfamily().toLowerCase()
                            +dataList.get(i).getEmployefamily().toLowerCase()
                            +dataList.get(i).getEmaploynamefamily().toLowerCase()
                            +dataList.get(i).getNativefamily().toLowerCase()
                            +dataList.get(i).getBloodfamily().toLowerCase()
                            +dataList.get(i).getActive().toLowerCase()
                            +dataList.get(i).getTelephonemob1().toLowerCase()
                            +dataList.get(i).getTelephoneres1().toLowerCase()
                            +dataList.get(i).getTelephonemob2().toLowerCase()
                            +dataList.get(i).getTelephoneres2().toLowerCase()
                            +dataList.get(i).getTelephonemob3().toLowerCase()
                            +dataList.get(i).getTelephoneres3().toLowerCase()
                            +dataList.get(i).getMobcodeindia().toLowerCase()
                            +dataList.get(i).getMailingadresss().toLowerCase();


                        if (rollno.contains(query)) {

                            filteredList.add(dataList.get(i));
                        }

                }

                recyclerView.setLayoutManager(new LinearLayoutManager(ParishMemberActivity.this));
                mAdapter = new ParishMemberAdapter(filteredList, ParishMemberActivity.this);
                recyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();  // data set changed
            }
        });
    }




}
