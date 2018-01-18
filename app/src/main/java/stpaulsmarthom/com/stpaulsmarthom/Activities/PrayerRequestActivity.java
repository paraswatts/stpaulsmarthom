package stpaulsmarthom.com.stpaulsmarthom.Activities;

import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stpaulsmarthom.com.stpaulsmarthom.APIService;
import stpaulsmarthom.com.stpaulsmarthom.APIUrl;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ParishMemberModel;
import stpaulsmarthom.com.stpaulsmarthom.R;
import stpaulsmarthom.com.stpaulsmarthom.SplashScreen;

public class PrayerRequestActivity extends AppCompatActivity {

    EditText et_prayer_name,et_prayer_email,et_prayer_phone,et_prayer_roll,et_prayer_sub,et_prayer_message;
    Button bt_prayer_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prayer_request);

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
        myActionBarTitle.setText("PRAYER REQUEST");

        ImageView iv_home = findViewById(R.id.home);
        iv_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrayerRequestActivity.this,HomeActivity.class);
      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(intent);                finish();
            }
        });

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#eb4142")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("PRAYER REQUEST");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back_button);
        bt_prayer_submit = findViewById(R.id.bt_prayer_submit);
        et_prayer_name = findViewById(R.id.et_prayer_name);
        et_prayer_email = findViewById(R.id.et_prayer_email);

        et_prayer_phone = findViewById(R.id.et_prayer_phone);

        et_prayer_roll = findViewById(R.id.et_prayer_roll);

        et_prayer_sub = findViewById(R.id.et_prayer_sub);

        et_prayer_message = findViewById(R.id.et_prayer_message);

        bt_prayer_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected(PrayerRequestActivity.this)) {

                    if(TextUtils.isEmpty(et_prayer_name.getText().toString().trim()))
                    {
                        et_prayer_name.requestFocus();
                        et_prayer_name.setError("Please enter a name");

                        return;
                    }
                    else if (TextUtils.isEmpty(et_prayer_email.getText().toString().trim())) {
                        et_prayer_email.requestFocus();
                        et_prayer_email.setError("Please enter an email");

                        return;

                    }
                    else if (!isValidEmail(et_prayer_email.getText().toString().trim())) {
                        et_prayer_email.requestFocus();
                        et_prayer_email.setError("Please enter a valid email");

                        return;

                    }
                    else if(TextUtils.isEmpty(et_prayer_phone.getText().toString().trim()))
                    {
                        et_prayer_phone.requestFocus();
                        et_prayer_phone.setError("Please enter a phone number");

                        return;
                    }
                    else if(TextUtils.isEmpty(et_prayer_roll.getText().toString().trim()))
                    {
                        et_prayer_roll.requestFocus();
                        et_prayer_roll.setError("Please enter a rollno");

                        return;
                    }
                    else if(TextUtils.isEmpty(et_prayer_sub.getText().toString().trim()))
                    {
                        et_prayer_sub.requestFocus();
                        et_prayer_sub.setError("Please enter a prayer subject");

                        return;
                    }
                    else if(TextUtils.isEmpty(et_prayer_message.getText().toString().trim()))
                    {
                        et_prayer_message.requestFocus();
                        et_prayer_message.setError("Please enter a prayer message");

                        return;
                    }

                    else {
                        getData(et_prayer_name.getText().toString().trim(),
                                et_prayer_email.getText().toString().trim(),
                                et_prayer_phone.getText().toString().trim(),
                                et_prayer_roll.getText().toString().trim(),
                                et_prayer_sub.getText().toString().trim(),
                                et_prayer_message.getText().toString().trim()
                        );
                    }
                }
                else
                {
                    Toast.makeText(PrayerRequestActivity.this, "Internet not available", Toast.LENGTH_SHORT).show();
                }

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

    public final static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
    private void getData(String name,String email,String phone,String rollno,String prayerSubject,String prayerMessage) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sending Prayer Request");
        progressDialog.setCancelable(false);
        progressDialog.show();
        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.sendPrayer(name,email,phone,rollno,prayerSubject,prayerMessage);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                progressDialog.dismiss();
               // Log.d("URL", "=====" + response.raw().request().url());

                //Log.e("getting about us", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        //Log.e("Success message",jsonObject.getString("success"));
                        Toast.makeText(PrayerRequestActivity.this,"Prayer Request Successfully Submitted",Toast.LENGTH_LONG);
                        AlertDialog.Builder alert = new AlertDialog.Builder(PrayerRequestActivity.this);
                        alert.setTitle("Prayer Request");
                        alert.setMessage(jsonObject.getString("message"));
                        alert.setCancelable(false);
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(PrayerRequestActivity.this,HomeActivity.class);
                      //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });
                        alert.show();



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
}
