package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentFilter;
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
import stpaulsmarthom.com.stpaulsmarthom.Adapters.BishopAdapter;
import stpaulsmarthom.com.stpaulsmarthom.Adapters.KaisthanaSamithiAdapter;
import stpaulsmarthom.com.stpaulsmarthom.Adapters.KaisthanaSectionRecycler;
import stpaulsmarthom.com.stpaulsmarthom.Adapters.OrgSectionRecycler;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbContract;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbHelper;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.BishopModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.KaisthanaSamithiModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.KaisthanaSectionModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgDetailModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgSectionModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.SectionHeader;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.SectionHeaderKais;
import stpaulsmarthom.com.stpaulsmarthom.NetworkConnectivityChange;
import stpaulsmarthom.com.stpaulsmarthom.R;

public class KaisthanaSamithiActivity extends AppCompatActivity {
    private List<KaisthanaSamithiModel> dataList;
    private KaisthanaSamithiAdapter mAdapter;
    RecyclerView recyclerView;
    KaisthanaSamithiModel kaisthanaSamithiModel;
    DbHelper dbHelper;
    ListView listView;
    RelativeLayout rl_header;
 ProgressBar mProgressBar;
    KaisthanaSectionRecycler adapterRecycler;
    List<SectionHeaderKais> sectionHeaders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kaisthana_samithi);
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
        myActionBarTitle.setText("KAISTHANA SAMITHI");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eb4142")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//       registerReceiver(new NetworkConnectivityChange(),new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        //getSupportActionBar().setTitle("KAISTHANA SAMITHI");
        mProgressBar = (ProgressBar) findViewById(R.id.google_progress_kais);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_Kaisthana);
        //listView = (ListView) findViewById(R.id.listView_Kaisthana);
        rl_header = findViewById(R.id.rl_header);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
        //kaisthanaSamithiModel = new KaisthanaSamithiModel();
        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(KaisthanaSamithiActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);
                finish();
            }
        });

        //kaisthanaSamithiModel = new KaisthanaSamithiModel();


        if(isConnected(this)) {
            mProgressBar.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(this)
                    .build());

            //ecyclerView.setVisibility(View.VISIBLE);
            //listView.setVisibility(View.GONE);

            //mAdapter = new KaisthanaSamithiAdapter(dataList,this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            //recyclerView.setAdapter(mAdapter);
            dbHelper = new DbHelper(this);

            getData();
        }
        else {
            try {
                mProgressBar.setVisibility(View.GONE);
                //recyclerView.setVisibility(View.GONE);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
//            listView.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                //ourKaisthanaCursorAdapter = new KaisthanaCursorAdapter(this, null);
//            listView.setVisibility(View.VISIBLE);

                dbHelper = new DbHelper(this);

                List<KaisthanaSectionModel> contacts = dbHelper.getKaisSections(getIntent().getStringExtra("orgNumber"));
                //OrgSectionModel orgSectionModel = new OrgSectionModel();
                int i = 0;
                for (KaisthanaSectionModel orgSectionModel : contacts) {
                    String log = "Id: " + orgSectionModel.getSection();
                    //Log.d("Id: ", log);
                    i++;
                    // Writing Contacts to log
                    dataList = new ArrayList<>();
                    List<KaisthanaSamithiModel> contactDetails = dbHelper.getKaisList("" + orgSectionModel.getSection(), getIntent().getStringExtra("orgNumber"));
                    sectionHeaders.add(new SectionHeaderKais(dataList, orgSectionModel.getSection(), i));

                    for (KaisthanaSamithiModel cn : contactDetails) {

//                    kaisthanaSamithiModel.setName(cn.getName());
//                    kaisthanaSamithiModel.setDesignation(cn.getDesignation());
//                    kaisthanaSamithiModel.setPhone(cn.getPhone());

                        kaisthanaSamithiModel = new KaisthanaSamithiModel(cn.getName(), cn.getPhone(), cn.getDesignation(), "",cn.getRollno(),cn.getImage());
                        dataList.add(kaisthanaSamithiModel);
                        String data = "Id: " + cn.getName() + " ,Name: " + cn.getDesignation() + " ,Phone: " + cn.getPhone();
                        Log.e("Name: ", data);
                    }
                    //Log.e("ChildList",dataList.get(0).getName()+"section"+orgSectionModel.getSection()+"i="+i);


                }
                adapterRecycler = new KaisthanaSectionRecycler(getApplicationContext(), sectionHeaders);
                recyclerView.setAdapter(adapterRecycler);
                adapterRecycler.notifyDataSetChanged();


                //dbHelper = new DbHelper(this);
                mProgressBar.setVisibility(View.GONE);
                //listView.setAdapter(ourKaisthanaCursorAdapter);


                //getLoaderManager().initLoader(0, null, this);
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

    private void getData() {
        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getKaisthanaSamithi();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mProgressBar.setVisibility(View.INVISIBLE);
                //Log.d("URL", "=====" + response.raw().request().url());

                //Log.e("getting our vicar", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {
                        //dbHelper.deleteTable(DbContract.DbEntry.TABLE_KAISTHANA);

                        JSONObject jsonObject = new JSONObject(response.body().toString());

                        if(jsonObject.has("Data")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("Data");

                            // Log.e("Array Length",""+jsonArray.length());


                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONArray jsonArray1 = jsonArray.getJSONArray(i);
                                // Log.e("Array Length",""+jsonArray1.length());
                                dataList = new ArrayList<>();
                                if (jsonArray1.length() > 0)
                                {
                                        for (int j = 0; j < jsonArray1.length(); j++) {
                                            JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                                            //  Log.e("Name ",""+jsonObject1.getString("Name"));
                                            kaisthanaSamithiModel = new KaisthanaSamithiModel(jsonObject1.getString("Name")
                                                    , jsonObject1.getString("Phone")
                                                    , jsonObject1.getString("Designation"),
                                                    jsonObject1.getString("Member_type"),
                                                    jsonObject1.getString("rollno"),
                                                    jsonObject1.getString("images")
                                            );
//                                kaisthanaSamithiModel.setName(jsonObject1.getString("Name"));
//                                kaisthanaSamithiModel.setPhone(jsonObject1.getString("Phone"));
//                                kaisthanaSamithiModel.setDesignation(jsonObject1.getString("Designation"));
//                                kaisthanaSamithiModel.setMemberType(jsonObject1.getString("Member_type"));
                                            //dataList.add(kaisthanaSamithiModel);
                                            dataList.add(kaisthanaSamithiModel);

                                        }

                                sectionHeaders.add(new SectionHeaderKais(dataList, jsonArray1.getJSONObject(0).getString("Member_type"), i));
                                }
                            }
                            //dbHelper.addKaisthana(kaisthanaSamithiModel);

                            adapterRecycler = new KaisthanaSectionRecycler(getApplicationContext(), sectionHeaders);
                            recyclerView.setAdapter(adapterRecycler);

                            adapterRecycler.notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(KaisthanaSamithiActivity.this, "No data found", Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    //Log.e("Exception",""+e.getMessage());
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


}
