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
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stpaulsmarthom.com.stpaulsmarthom.APIService;
import stpaulsmarthom.com.stpaulsmarthom.APIUrl;
import stpaulsmarthom.com.stpaulsmarthom.Adapters.VicarAdapter;
import stpaulsmarthom.com.stpaulsmarthom.CursorAdapter.OurVicarCursorAdapter;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbContract;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbHelper;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.VicarModel;
import stpaulsmarthom.com.stpaulsmarthom.R;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class OurVicarActivity extends AppCompatActivity  implements  LoaderManager.LoaderCallbacks<Cursor>{

    private List<VicarModel> dataList = new ArrayList<>();
    private VicarAdapter mAdapter;
    RecyclerView recyclerView;
    VicarModel vicarModel;
    ProgressBar mProgressBar;

    DbHelper dbHelper;
    ListView listView;
    OurVicarCursorAdapter ourVicarCursorAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_vicar);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#f9af10"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#f9af10"));
            getWindow().setNavigationBarColor(Color.parseColor("#f9af10"));
        }


        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        TextView myActionBarTitle;
        myActionBarTitle = (TextView)findViewById(R.id.myActionBarTitle);
        myActionBarTitle.setText("OUR VICAR");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f9af10")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("OUR VICAR");

        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OurVicarActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);                finish();
            }
        });

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
        mProgressBar = (ProgressBar)findViewById(R.id.google_progress_vicar);
        mProgressBar.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(this)
                .build());
        ourVicarCursorAdapter = new OurVicarCursorAdapter(this, null);
        listView = (ListView) findViewById(R.id.listView_Vicar);


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_Vicar) ;
        if(isConnected(this)) {
            listView.setVisibility(View.GONE);

            recyclerView.setVisibility(View.VISIBLE);
            mAdapter = new VicarAdapter(dataList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);


            dbHelper = new DbHelper(this);

            getData();
        }

        else {
            try {

                mProgressBar.setVisibility(View.GONE);
                dbHelper = new DbHelper(this);

                listView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                listView.setAdapter(ourVicarCursorAdapter);

                DbHelper userDbHelper = new DbHelper(this);
                SQLiteDatabase sqLiteDatabase = userDbHelper.getWritableDatabase();

                Cursor query = sqLiteDatabase.query(DbContract.DbEntry.TABLE_OUR_VICAR, //Table to query
                        null, //columns to return
                        null, //columns for the WHERE clause
                        null, //filter by row groups
                        null, null, null);
                int count = query.getCount();
                // Log.e("Db Count",""+count);

                getLoaderManager().initLoader(0, null, this);
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


    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }


    private void getData() {






        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getOurVicar();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mProgressBar.setVisibility(View.INVISIBLE);
               // Log.d("URL", "=====" + response.raw().request().url());

              //  Log.e("getting our vicar", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {
                        //dbHelper.deleteTable(DbContract.DbEntry.TABLE_OUR_VICAR);

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        if(jsonObject.has("Data")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("Data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                //   Log.e("Values","our_vicar"+jsonObject1.getString("Our_vicar")+"Image"+jsonObject1.getString("images"));
                                vicarModel = new VicarModel(jsonObject1.getString("Our_vicar"), jsonObject1.getString("images"), jsonObject1.getString("tital"));
                                dataList.add(vicarModel);
                                //dbHelper.addOurVicar(vicarModel);


                            }

                            mAdapter.notifyDataSetChanged();

                        }
                        else {
                            Toast.makeText(OurVicarActivity.this, "No data found", Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                       // Toast.makeText(getApplicationContext(),"Error Fetching Data",Toast.LENGTH_LONG);
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(this,
                DbContract.DbEntry.CONTENT_URI_OUR_VICAR,null,null,null,null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        ourVicarCursorAdapter.swapCursor(cursor);

    }


    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0,null,this);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ourVicarCursorAdapter.swapCursor(null);

    }


}
