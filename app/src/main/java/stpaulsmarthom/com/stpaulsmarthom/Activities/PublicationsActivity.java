package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import stpaulsmarthom.com.stpaulsmarthom.R;

public class PublicationsActivity extends AppCompatActivity implements View.OnClickListener {

    RelativeLayout rl_parish,rl_year_planner,rl_archen_letter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publications);
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
                Intent intent = new Intent(PublicationsActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);                finish();
            }
        });
       // getSupportActionBar().setTitle("PUBLICATIONS");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
        rl_parish = (RelativeLayout)findViewById(R.id.rl_parish);
        rl_year_planner = (RelativeLayout)findViewById(R.id.rl_year_planner);

        rl_archen_letter = (RelativeLayout)findViewById(R.id.rl_archen_letter);


        rl_parish.setOnClickListener(this);
        rl_archen_letter.setOnClickListener(this);
        rl_year_planner.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.rl_parish)
        {
            //if(isConnected(PublicationsActivity.this)) {
                startActivity(new Intent(this, ParishDetailActivity.class));
            //}
           // else{
                //Toast.makeText(this, "Please connect to internet .", Toast.LENGTH_SHORT).show();
               // startActivity(new Intent(this, ParishOfflineActivity.class));
           // }

        }

        if(view.getId() == R.id.rl_archen_letter)
        {
            //if(isConnected(PublicationsActivity.this)) {
            startActivity(new Intent(this, ArchensLetterActivity.class));
            //}
            // else{
            //Toast.makeText(this, "Please connect to internet .", Toast.LENGTH_SHORT).show();
            // startActivity(new Intent(this, ParishOfflineActivity.class));
            // }

        }
        if(view.getId() == R.id.rl_year_planner)
        {
            startActivity(new Intent(this, YearPlannerActivity.class));
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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
