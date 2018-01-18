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
import stpaulsmarthom.com.stpaulsmarthom.Adapters.BishopAdapter;
import stpaulsmarthom.com.stpaulsmarthom.CursorAdapter.OurBishopCursorAdapter;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbContract;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbHelper;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.BishopModel;
import stpaulsmarthom.com.stpaulsmarthom.R;

import static android.support.v7.widget.DividerItemDecoration.VERTICAL;

public class OurBishopsActivity extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<Cursor>{
    private List<BishopModel> dataList = new ArrayList<>();
    private BishopAdapter mAdapter;
    RecyclerView recyclerView;
    BishopModel bishopModel;
    ProgressBar mProgressBar;

    DbHelper dbHelper;
    ListView listView;
    OurBishopCursorAdapter ourBishopCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_our_bishops);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#a3cc2e"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#a3cc2e"));
            getWindow().setNavigationBarColor(Color.parseColor("#a3cc2e"));

        }

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        TextView myActionBarTitle;
        myActionBarTitle = (TextView)findViewById(R.id.myActionBarTitle);
        myActionBarTitle.setText("OUR BISHOPS");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#a3cc2e")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setTitle("OUR BISHOPS");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
        ourBishopCursorAdapter = new OurBishopCursorAdapter(this, null);
        mProgressBar = (ProgressBar) findViewById(R.id.google_progress_bishop);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_Bishop);
        listView = (ListView) findViewById(R.id.listView_Bishop);
        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OurBishopsActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);                finish();
            }
        });

        if(isConnected(this)) {
            mProgressBar.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(this)
                    .build());

            dbHelper = new DbHelper(this);
            recyclerView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);

            mAdapter = new BishopAdapter(dataList, this);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            DividerItemDecoration itemDecor = new DividerItemDecoration(this, VERTICAL);
            recyclerView.addItemDecoration(itemDecor);
            recyclerView.setAdapter(mAdapter);
            getData();

        }
        else
        {
            try {


                mProgressBar.setVisibility(View.GONE);
                ourBishopCursorAdapter = new OurBishopCursorAdapter(this, null);
                recyclerView.setVisibility(View.GONE);

                listView.setVisibility(View.VISIBLE);
                dbHelper = new DbHelper(this);


                listView.setAdapter(ourBishopCursorAdapter);


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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        return new CursorLoader(this,
                DbContract.DbEntry.CONTENT_URI_OUR_BISHOPS,null,null,null,null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        ourBishopCursorAdapter.swapCursor(cursor);

    }


    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0,null,this);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ourBishopCursorAdapter.swapCursor(null);

    }

    private void getData() {

//        final ProgressDialog progressDialog = new ProgressDialog(this);
//               progressDialog.show();
//
//




        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getBishops();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mProgressBar.setVisibility(View.INVISIBLE);

               // Log.d("URL", "=====" + response.raw().request().url());

              //  Log.e("getting our vicar", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {
                        //dbHelper.deleteTable(DbContract.DbEntry.TABLE_OUR_BISHOPS);

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        if(jsonObject.has("Data")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("Data");
                            //   Log.e("Array Length",""+jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                //     Log.e("Name ",""+jsonObject1.getString("name"));
                                bishopModel = new BishopModel(jsonObject1.getString("name")
                                        , jsonObject1.getString("phone")
                                        , jsonObject1.getString("address")
                                        , jsonObject1.getString("email")
                                        , jsonObject1.getString("image")
                                        , jsonObject1.getString("birthdate")
                                );
                                dataList.add(bishopModel);
                                //dbHelper.addOurBishops(bishopModel);


                            }

                            mAdapter.notifyDataSetChanged();
                        }
                        else
                        {
                            Toast.makeText(OurBishopsActivity.this, "No data found", Toast.LENGTH_SHORT).show();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                       // Log.e("Exception bishop",e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }


}
