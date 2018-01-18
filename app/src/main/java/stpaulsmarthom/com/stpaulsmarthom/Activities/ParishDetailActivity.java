package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.jpardogo.android.googleprogressbar.library.ChromeFloatingCirclesDrawable;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stpaulsmarthom.com.stpaulsmarthom.APIService;
import stpaulsmarthom.com.stpaulsmarthom.APIUrl;
import stpaulsmarthom.com.stpaulsmarthom.Adapters.ParishBulletinAdapter;
import stpaulsmarthom.com.stpaulsmarthom.CursorAdapter.PublicationsCursorAdapter;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbContract;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbHelper;
import stpaulsmarthom.com.stpaulsmarthom.FileDownloader;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ParishBulletinModel;
import stpaulsmarthom.com.stpaulsmarthom.R;

public class ParishDetailActivity extends AppCompatActivity implements  LoaderManager.LoaderCallbacks<Cursor>{
    private List<ParishBulletinModel> dataList = new ArrayList<>();
    private ParishBulletinAdapter mAdapter;
    GridView gridView;
    ParishBulletinModel parishBulletinModel;
    ProgressBar mProgressBar;
    PublicationsCursorAdapter publicationsCursorAdapter;
    DbHelper dbHelper;
    ProgressBar google_progress_pdf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parish_detail);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, null, Color.parseColor("#7ecce2"));

            setTaskDescription(td);
            getWindow().setStatusBarColor(Color.parseColor("#7ecce2"));
            getWindow().setNavigationBarColor(Color.parseColor("#7ecce2"));
        }

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_layout);

        TextView myActionBarTitle;
        myActionBarTitle = (TextView)findViewById(R.id.myActionBarTitle);
        myActionBarTitle.setText("PUBLICATIONS");


        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#7ecce2")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParishDetailActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);                finish();
            }
        });

        //getSupportActionBar().setTitle("PUBLICATIONS");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
        mProgressBar = (ProgressBar)findViewById(R.id.google_progress_pdf);
        mProgressBar.setIndeterminateDrawable(new ChromeFloatingCirclesDrawable.Builder(this)
                .build());
        publicationsCursorAdapter = new PublicationsCursorAdapter(this,null);

        gridView = (GridView)findViewById(R.id.gridView);
if(isConnected(this)) {
    mAdapter = new ParishBulletinAdapter(dataList, this);
    gridView.setAdapter(mAdapter);


    getData();
}
else
{
    try {


        mProgressBar.setVisibility(View.GONE);

        gridView.setAdapter(publicationsCursorAdapter);
        dbHelper = new DbHelper(this);
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


    private void getData() {


        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getPublications();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                mProgressBar.setVisibility(View.INVISIBLE);
                //Log.d("URL", "=====" + response.raw().request().url());

                //Log.e("getting about us", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        if (jsonObject.has("Data"))
                        {
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");


                            // Log.e("Array Length",""+jsonArray.length()+  jsonArray.getJSONObject(0));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                parishBulletinModel = new ParishBulletinModel(jsonObject1.getString("Name")
                                        , jsonObject1.getString("file_name"), ""

                                );
                                dataList.add(parishBulletinModel);

                            }
                    }
                    else
                        {
                            Toast.makeText(ParishDetailActivity.this, "No data found", Toast.LENGTH_SHORT).show();

                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    mAdapter.notifyDataSetChanged();

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
                DbContract.DbEntry.CONTENT_URI_PARISH_PUBLICATIONS,null,null,null,null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        publicationsCursorAdapter.swapCursor(cursor);

    }


    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0,null,this);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        publicationsCursorAdapter.swapCursor(null);

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
