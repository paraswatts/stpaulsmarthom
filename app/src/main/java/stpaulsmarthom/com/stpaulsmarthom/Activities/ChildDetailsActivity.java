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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.jpardogo.android.googleprogressbar.library.ChromeFloatingCirclesDrawable;
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

public class ChildDetailsActivity extends AppCompatActivity{

    ChildModel childModel;
    DbHelper dbHelper;
    ListView listView_MemberChild;
    RecyclerView recyclerView ;
    private List<ChildModel> dataList = new ArrayList<>();
    ChildAdapter mAdapter;
    ProgressBar google_progress_child;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.child_details);
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
        myActionBarTitle.setText("CHILD DETAILS");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eb4142")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("PARISH MEMBER");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);

        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChildDetailsActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);                finish();
            }
        });
        google_progress_child = findViewById(R.id.google_progress_child);
        google_progress_child.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(this)
                .build());
        recyclerView = findViewById(R.id.recyclerView_MemberChild) ;
        //listView_MemberChild = findViewById(R.id.listView_MemberChild);
        mAdapter = new ChildAdapter(dataList, this);

        if(isConnected(this)) {
            // listView_MemberChild.setVisibility(View.GONE);
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

                google_progress_child.setVisibility(View.GONE);
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
                if(contacts.size()>0) {
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

                        Log.e("Emp name child",""+cn.getEmployername() + cn.getRollno());
                    }

                    mAdapter.notifyDataSetChanged();
                }
                else
                {
                    Toast.makeText(ChildDetailsActivity.this, "No children data found", Toast.LENGTH_SHORT).show();

                }

                //getLoaderManager().initLoader(0, null, this);

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void getData(String rollno) {

        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getParishMemberChild(rollno);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                // mProgressBar.setVisibility(View.INVISIBLE);
               // Log.d("URL", "=====" + response.raw().request().url());
//
              //   Log.e("getting child", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {
                        google_progress_child.setVisibility(View.GONE);

                        JSONObject jsonObject = new JSONObject(response.body().toString());



                        if(jsonObject.has("Data")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("Data");
                            Boolean success = Boolean.valueOf(String.valueOf(jsonObject.get("success")));
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
                        else
                        {
                            Toast.makeText(ChildDetailsActivity.this, "No children data found", Toast.LENGTH_SHORT).show();
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
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }



}
