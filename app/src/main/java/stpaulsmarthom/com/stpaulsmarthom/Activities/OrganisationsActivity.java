package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import stpaulsmarthom.com.stpaulsmarthom.Adapters.OrgItemAdapter;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbHelper;
import stpaulsmarthom.com.stpaulsmarthom.R;

public class OrganisationsActivity extends AppCompatActivity {
    String[] organisations = {
            "Senior Citizen Forum",
            "Edavaka Mission",
            "Sevika Sangham",
            "Yuvajana Sakhyam",
            "Choir",
            "Sunday School",
    } ;
    String[] orgNumber = {
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
    } ;
    private OrgItemAdapter mAdapter;
    DbHelper dbHelper;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisations);
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
        myActionBarTitle.setText("ORGANISATIONS");

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#f9af10")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("ORGANISATIONS");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);


        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrganisationsActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);                finish();
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView_organisations) ;
        mAdapter = new OrgItemAdapter(organisations,orgNumber,organisations,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        dbHelper = new DbHelper(this);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Runtime.getRuntime().gc();
    }
}


