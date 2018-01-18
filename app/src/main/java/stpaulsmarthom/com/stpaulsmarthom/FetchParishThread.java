package stpaulsmarthom.com.stpaulsmarthom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbContract;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbHelper;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.AboutUsModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.BishopModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ChildModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ContactUsModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.KaisthanaSamithiModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.KaisthanaSectionModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgDetailModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgSectionModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ParishBulletinModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ParishMemberModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.PrivacyModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.VicarModel;
import stpaulsmarthom.com.stpaulsmarthom.services.DownloadService;

import static stpaulsmarthom.com.stpaulsmarthom.Database.DbContract.DbEntry.TABLE_CHILD;

/**
 * Created by Paras-Android on 04-01-2018.
 */

public class FetchParishThread implements Runnable {
    Context context;
    AboutUsModel aboutUsModel;
    PrivacyModel privacyModel;
    private List<ChildModel> childModelList = new ArrayList<>();

    DbHelper dbHelper;


    ChildModel childModel;

    ContactUsModel contactUsModel;

    ParishMemberModel parishMemberModel;



    String rollno,cprno;
    public FetchParishThread(Context context, String rollno, String cprno) {
        
        this.context = context;
        this.rollno = rollno;
        this.cprno = cprno;
    }
    @Override
    public void run() {
        dbHelper = new DbHelper(context);
        aboutUsModel = new AboutUsModel();
        privacyModel = new PrivacyModel();
        contactUsModel = new ContactUsModel();

        if(isConnected(context)) {
            dbHelper.deleteTable(DbContract.DbEntry.TABLE_CHILD);

            Log.e("Thread","in thread");
            getData(rollno, cprno);
            }

    }



    public static boolean isConnected(Context context){
        NetworkInfo info = getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
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

                        dbHelper.deleteTable(DbContract.DbEntry.TABLE_PARISH);
                        //progressDialog.dismiss();

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        // Log.e("Success message",jsonObject.getString("success"));

                        // Log.e("Success is" , " "+ jsonObject.get("success"));
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");

                        for(int i=0;i<jsonArray.length();i++) {
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

                            getChildData(jsonObject1.getString("membernamerollno"));




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


                            dbHelper.addParishMember(parishMemberModel);

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
    private void getChildData(final String rollno) {

        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getParishMemberChild(rollno);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                // mProgressBar.setVisibility(View.INVISIBLE);
                //Log.d("URL", "=====" + response.raw().request().url());

                // Log.e("getting about us", ""+response.body().toString());

                if (response.isSuccessful() && response.body() != null) {
                    try {

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        //   Log.e("Success message",jsonObject.getString("success"));

                        //   Log.e("Success is" , " "+ jsonObject.get("success"));

                        if (jsonObject.has("Data")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("Data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

//                                final String name = jsonObject1.getString("childsimage");
//                                final String actualImage = name.replace("images/", "");
//
//                                final String imageUrl = "http://stpaulsmarthomabahrain.com/membershipform/" + jsonObject1.getString("childsimage");
//                                //Log.e("Child Image Url", "" + imageUrl + "and Roll no =" + rollno);
//                                //File path = getOutputMediaFile("Contact",name);
//
//
//                                File sampleDir = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain" + "/" + "ParishMemberChild");
//                                if (!sampleDir.exists()) {
//                                    if (!sampleDir.mkdirs()) {
//                                        return;
//                                    }
//                                }
//                                // Log.e("Saving Image", "");
//                                File mediaFile;
//                                String mImageName = actualImage;
//                                mediaFile = new File(sampleDir.getPath() + File.separator + mImageName);
//                                if (!mediaFile.exists()) {
////
//                                    Log.e("getting ","child image");
//                                    Intent intent = new Intent(ParishMemberActivity.this, DownloadService.class);
//                                    intent.putExtra("imageUrl", imageUrl);
//                                    intent.putExtra("imagePath", mediaFile.getAbsolutePath());
//                                    startService(intent);
//                                    //new AsyncTaskRunner().execute(imageUrl, mediaFile.getAbsolutePath());
//                                } else {
//                                    // Log.e("File check","Already Exists");
//                                }

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
                                childModelList.add(childModel);
                                dbHelper.addParishChild(childModel);

                            }

                        }


                    } catch (Exception e) {
                        e.printStackTrace();

                        //Log.e("Exception",""+rollno);
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });


    }

    }
