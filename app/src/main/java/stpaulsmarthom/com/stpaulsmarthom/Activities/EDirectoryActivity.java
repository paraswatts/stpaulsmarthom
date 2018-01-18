package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import stpaulsmarthom.com.stpaulsmarthom.R;

public class EDirectoryActivity extends AppCompatActivity implements View.OnClickListener{

    EditText et_user,et_pwd;
    Button bt_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edirectory_dup);
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
        myActionBarTitle.setText("E-DIRECTORY");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eb4142")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("E-DIRECTORY");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);

        et_user = (EditText)findViewById(R.id.et_user);
        et_pwd = (EditText)findViewById(R.id.et_pwd);
        bt_login = (Button)findViewById(R.id.bt_login);

        bt_login.setOnClickListener(this);


        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EDirectoryActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);                finish();
            }
        });
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.bt_login)
        {
            String user = et_user.getText().toString().trim();
            String pwd = et_pwd.getText().toString().trim();

            if(TextUtils.isEmpty(user))
            {
                et_user.requestFocus();
                et_user.setError("Please enter roll no");
            }
            else if(TextUtils.isEmpty(pwd))
            {
                et_pwd.requestFocus();
                et_pwd.setError("Please enter cpr no");
            }
            else {
                //  if(isConnected(this)) {
                SharedPreferences preferences = getSharedPreferences("LoginParish", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("user", user);

                editor.putString("pass", pwd);
                editor.commit();
                Intent parishMemberActivity = new Intent(EDirectoryActivity.this, ParishMemberActivity.class);

                parishMemberActivity.putExtra("user", user);
                parishMemberActivity.putExtra("pwd", pwd);
                startActivity(parishMemberActivity);
                finish();
            }
           // }

//            else{
//                Intent parishMemberActivity = new Intent(EDirectoryActivity.this, ParishMemberOfflineActivity.class);
//
//                startActivity(parishMemberActivity);
//            }
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
}
