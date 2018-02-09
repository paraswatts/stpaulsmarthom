package stpaulsmarthom.com.stpaulsmarthom;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonElement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import stpaulsmarthom.com.stpaulsmarthom.Database.DbContract;
import stpaulsmarthom.com.stpaulsmarthom.Database.DbHelper;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.AboutUsModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.AchensModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.BishopModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ChildModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ContactUsModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.KaisthanaSamithiModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.KaisthanaSectionModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgDetailModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgSectionModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.OrgTimingModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ParishBulletinModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.ParishMemberModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.PrivacyModel;
import stpaulsmarthom.com.stpaulsmarthom.ModelClasses.VicarModel;
import stpaulsmarthom.com.stpaulsmarthom.services.DownloadService;

/**
 * Created by Paras-Android on 04-01-2018.
 */

public class FetchThread implements Runnable {
    Context context; private List<BishopModel> bishopList = new ArrayList<>();
    BishopModel bishopModel;
    static double lat,lng;
    ParishBulletinModel parishBulletinModel;
    AchensModel achensModel;

    OrgTimingModel orgTimingModel;

    List<OrgTimingModel>
            timeList = new ArrayList<>();
    AboutUsModel aboutUsModel;
    PrivacyModel privacyModel;
    private List<ChildModel> childModelList = new ArrayList<>();

    DbHelper dbHelper;

    OrgDetailModel orgDetailModel;
    ChildModel childModel;
    private List<VicarModel> vicarList = new ArrayList<>();
    VicarModel vicarModel;

    OrgSectionModel orgSectionModel;
    private List<KaisthanaSamithiModel> kaisthanaList = new ArrayList<>();
    KaisthanaSamithiModel kaisthanaSamithiModel;
    ContactUsModel contactUsModel;

    ParishMemberModel parishMemberModel;

    KaisthanaSectionModel kaisthanaSectionModel;

    public FetchThread(Context context,String rollno,String cprno) {
        
        this.context = context;
    }
    String org_timing;
    @Override
    public void run() {
        dbHelper = new DbHelper(context);
        aboutUsModel = new AboutUsModel();
        privacyModel = new PrivacyModel();
        contactUsModel = new ContactUsModel();

        if(isConnected(context)) {
            dbHelper.deleteTable(DbContract.DbEntry.TABLE_CHILD);

            dbHelper.deleteTable(DbContract.DbEntry.TABLE_ORGANISATIONS);
            dbHelper.deleteTable(DbContract.DbEntry.TABLE_ORGANISATIONS_TIMING);

            dbHelper.deleteTable(DbContract.DbEntry.TABLE_ORGANISATIONS_SECTIONS);
            getAboutData();
            getBishopData();
            getVicarData();
            getKaisthanaData();
            getContactData();
            getPublicationsData();
            getAchensData();
            getPrivacy();
            getYearPlannerData();
            for (int i = 1; i <= 6; i++) {
                try {

                    getTiming(String.valueOf(i));
                } catch (Exception e) {
                    e.printStackTrace();
                    //("Error org",e.getMessage());
                }

            }
                for (int i = 1; i <= 6; i++) {
            try {

                    getOrganisation(String.valueOf(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                        //("Error org",e.getMessage());
                    }
                }
            //getParishData("28", "720416000");
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


    private void getAboutData() {

        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getAboutUs();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //Log.d("URL", "=====" + response.raw().request().url());

                //  Log.e("getting about us", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {

                        dbHelper.deleteTable(DbContract.DbEntry.TABLE_ABOUT_US);

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        //  Log.e("Success message",jsonObject.getString("success"));

                        //   Log.e("Success is" , " "+ jsonObject.get("success"));
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                        final String name = jsonObject1.getString("images");
                        final String imageUrl= "http://stpaulsmarthomabahrain.com/membershipform/church_admin/about_us/"+jsonObject1.getString("images");

                        //File path = getOutputMediaFile("Contact",name);


                        File sampleDir = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain"+"/"+"AboutUs");
                        if (!sampleDir.exists()) {
                            if (!sampleDir.mkdirs()) {
                                return;
                            }
                        }
                        // Log.e("Saving Image", "");
                        File mediaFile;
                        String mImageName = name;
                        mediaFile = new File(sampleDir.getPath() + File.separator + mImageName);
                        if(!mediaFile.exists()) {
                           //1 new DownloadThread(imageUrl,mediaFile.getAbsolutePath(),10).run();;
                            Intent intent = new Intent(context, DownloadService.class);
                            intent.putExtra("imageUrl",imageUrl);
                            intent.putExtra("imagePath",mediaFile.getAbsolutePath());
                            intent.putExtra("quality",10);
                            context.startService(intent);
                            //new AsyncTaskRunner().execute(imageUrl, mediaFile.getAbsolutePath());
                        }
                        else
                        {
                            //Log.e("File check","Already Exists");
                        }
                        aboutUsModel.setChurchTimeEnglish(jsonObject1.getString("timing_english"));
                        aboutUsModel.setChurchTimeMalayalam(jsonObject1.getString("timing_malayalam"));
                        aboutUsModel.setAboutUsContent(jsonObject1.getString("content"));
                        aboutUsModel.setAboutUsImage(mediaFile.getAbsolutePath());
                        dbHelper.addAboutUsContent(aboutUsModel);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    private void getPrivacy() {

        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getPrivacy();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                // Log.d("URL", "=====" + response.raw().request().url());

                //  Log.e("getting about us", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {

                        dbHelper.deleteTable(DbContract.DbEntry.TABLE_PRIVACY);

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        //  Log.e("Success message",jsonObject.getString("success"));

                        //   Log.e("Success is" , " "+ jsonObject.get("success"));
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        privacyModel.setAboutUsContent(jsonObject1.getString("phone_Policy"));
                        dbHelper.addPrivacy(privacyModel);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }


    private void getBishopData() {



        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getBishops();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //Log.d("URL", "=====" + response.raw().request().url());

                //  Log.e("getting our vicar", "" + response.body().toString());

                if (response.isSuccessful() && response.body() != null) {
                    try {
                        dbHelper.deleteTable(DbContract.DbEntry.TABLE_OUR_BISHOPS);

                        JSONObject jsonObject = new JSONObject(response.body().toString());

                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        //  Log.e("Array Length", "" + jsonArray.length());
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            final String name = jsonObject1.getString("image");
                            //File path = getOutputMediaFile("Bishops",name);
                            final String imageUrl= "http://stpaulsmarthomabahrain.com/membershipform/church_admin/Bishops/"+jsonObject1.getString("image");

                            File sampleDir = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain"+"/"+"Bishops");
                            if (!sampleDir.exists()) {
                                if (!sampleDir.mkdirs()) {
                                    return;
                                }
                            }
                            //Log.e("Saving Image", "");
                            File mediaFile;
                            String mImageName = name;
                            mediaFile = new File(sampleDir.getPath() + File.separator + mImageName);
                            if(!mediaFile.exists()) {
                                //new DownloadThread(imageUrl,mediaFile.getAbsolutePath(),10).run();;
                                Intent intent = new Intent(context, DownloadService.class);
                                intent.putExtra("imageUrl",imageUrl);
                                intent.putExtra("imagePath",mediaFile.getAbsolutePath());
                                intent.putExtra("quality",10);
                                context.startService(intent);
//                                Intent intent = new Intent(FetchDataService.context, DownloadService.class);
//                                intent.putExtra("imageUrl",imageUrl);
//                                intent.putExtra("imagePath",mediaFile.getAbsolutePath());
//                                startService(intent);
                                //new AsyncTaskRunner().execute(imageUrl, mediaFile.getAbsolutePath());
                            }
                            else
                            {
                                //Log.e("File check","Already Exists");
                            }
                            //   Log.e("Name ", "" + jsonObject1.getString("name"));
                            bishopModel = new BishopModel(jsonObject1.getString("name")
                                    , jsonObject1.getString("phone")
                                    , jsonObject1.getString("address")
                                    , jsonObject1.getString("email")
                                    , mediaFile.getAbsolutePath()
                                    ,jsonObject1.getString("birthdate")
                            );



                            bishopList.add(bishopModel);

                            dbHelper.addOurBishops(bishopModel);


                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    private void getVicarData() {








        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getOurVicar();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //Log.d("URL", "=====" + response.raw().request().url());

                //   Log.e("getting our vicar", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {
                        dbHelper.deleteTable(DbContract.DbEntry.TABLE_OUR_VICAR);

                        JSONObject jsonObject = new JSONObject(response.body().toString());

                        JSONArray jsonArray = jsonObject.getJSONArray("Data");

                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            final String name = jsonObject1.getString("images");
                            File path = getOutputMediaFile("Vicar",name);
                            final String imageUrl= "http://stpaulsmarthomabahrain.com/membershipform/church_admin/upload/"+jsonObject1.getString("images");

                            File sampleDir = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain"+"/"+"Vicar");
                            if (!sampleDir.exists()) {
                                if (!sampleDir.mkdirs()) {
                                    return;
                                }
                            }
                            //Log.e("Saving Image", "");
                            File mediaFile;
                            String mImageName = name;
                            mediaFile = new File(sampleDir.getPath() + File.separator + mImageName);
                            if(!mediaFile.exists()) {
                               // new DownloadThread(imageUrl,mediaFile.getAbsolutePath(),30).run();;
                                Intent intent = new Intent(context, DownloadService.class);
                                intent.putExtra("imageUrl",imageUrl);
                                intent.putExtra("imagePath",mediaFile.getAbsolutePath());
                                intent.putExtra("quality",100);
                                context.startService(intent);

//                                Intent intent = new Intent(FetchDataService.context, DownloadService.class);
//                                intent.putExtra("imageUrl",imageUrl);
//                                intent.putExtra("imagePath",mediaFile.getAbsolutePath());
//                                startService(intent);
                                //new AsyncTaskRunner().execute(imageUrl, mediaFile.getAbsolutePath());
                            }
                            else
                            {
                                //Log.e("File check","Already Exists");
                            }
                            //    Log.e("Values","our_vicar"+jsonObject1.getString("Our_vicar")+"Image"+jsonObject1.getString("images"));
                            vicarModel = new VicarModel(jsonObject1.getString("Our_vicar"),mediaFile.getAbsolutePath(),jsonObject1.getString("tital"));
                            vicarList.add(vicarModel);





                            dbHelper.addOurVicar(vicarModel);


                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    private void getKaisthanaData() {






        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getKaisthanaSamithi();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //Log.d("URL", "=====" + response.raw().request().url());

                // Log.e("getting our vicar", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {
                        dbHelper.deleteTable(DbContract.DbEntry.TABLE_KAISTHANA);
                        dbHelper.deleteTable(DbContract.DbEntry.TABLE_KAIS_SECTIONS);

                        JSONObject jsonObject = new JSONObject(response.body().toString());

                        JSONArray jsonArray = jsonObject.getJSONArray("Data");

                        //   Log.e("Array Length",""+jsonArray.length()+  jsonArray.getJSONObject(0));
//                        for(int i=0;i<jsonArray.length();i++) {
//                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//
//                            kaisthanaSamithiModel = new KaisthanaSamithiModel(jsonObject1.getString("Name")
//                                    ,jsonObject1.getString("Phone")
//                                    ,jsonObject1.getString("Designation")
//                            );
//                            kaisthanaList.add(kaisthanaSamithiModel);
//
//                        }

                        for(int i=0;i<jsonArray.length();i++) {
                            JSONArray jsonArray1 = jsonArray.getJSONArray(i);
                            //Log.e("Array Length",""+jsonArray1.length());
                            kaisthanaSectionModel =  new KaisthanaSectionModel();
                            if(jsonArray1.length()>0)
                            {
                            kaisthanaSectionModel.setSection(jsonArray1.getJSONObject(0).getString("Member_type"));

                            dbHelper.addKaisSection(kaisthanaSectionModel);

                            for (int j = 0; j < jsonArray1.length(); j++) {
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                                final String imageUrl= "http://stpaulsmarthomabahrain.com/membershipform/church_admin/Kaisthana/"+jsonObject1.getString("images");

                                //File path = getOutputMediaFile("Contact",name);


                                File sampleDir = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain"+"/"+"Kaisthana");
                                if (!sampleDir.exists()) {
                                    if (!sampleDir.mkdirs()) {
                                        return;
                                    }
                                }
                                // Log.e("Saving Image", "");
                                File mediaFile;
                                String mImageName = jsonObject1.getString("images");
                                mediaFile = new File(sampleDir.getPath() + File.separator + mImageName);
                                SaveShared("KaisImage",mediaFile.getAbsolutePath());

                                if(!mediaFile.exists()) {
                                    // new DownloadThread(imageUrl,mediaFile.getAbsolutePath(),10).run();;
                                    Intent intent = new Intent(context, DownloadService.class);
                                    intent.putExtra("imageUrl",imageUrl);
                                    intent.putExtra("imagePath",mediaFile.getAbsolutePath());
                                    intent.putExtra("quality",50);
                                    context.startService(intent);

//                            Intent intent = new Intent(FetchDataService.context, DownloadService.class);
//                            intent.putExtra("imageUrl",imageUrl);
//                            intent.putExtra("imagePath",mediaFile.getAbsolutePath());
//                            startService(intent);
                                    //new AsyncTaskRunner().execute(imageUrl, mediaFile.getAbsolutePath());
                                }
                                else
                                {
                                    //Log.e("File check","Already Exists");
                                }
                                //Log.e("Name ",""+jsonObject1.getString("Name"));
                                kaisthanaSamithiModel = new KaisthanaSamithiModel(jsonObject1.getString("Name")
                                        , jsonObject1.getString("Phone")
                                        , jsonObject1.getString("Designation"),
                                        jsonObject1.getString("Member_type"),
                                        jsonObject1.getString("rollno"),
                                        mediaFile.getAbsolutePath()

                                );
//                                kaisthanaSamithiModel.setName(jsonObject1.getString("Name"));
//                                kaisthanaSamithiModel.setPhone(jsonObject1.getString("Phone"));
//                                kaisthanaSamithiModel.setDesignation(jsonObject1.getString("Designation"));
//                                kaisthanaSamithiModel.setMemberType(jsonObject1.getString("Member_type")
                                //   );
                                dbHelper.addKaisthana(kaisthanaSamithiModel);

                            }
                            }
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                        //Log.e("Exception",e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    private void getContactData() {






        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getContact();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //Log.d("URL", "=====" + response.raw().request().url());

                //Log.e("getting about us", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {
                        dbHelper.deleteTable(DbContract.DbEntry.TABLE_CONTACT_US);

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        //Log.e("Success message",jsonObject.getString("success"));

                        // Log.e("Success is" , " "+ jsonObject.get("success"));
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        final JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        contactUsModel.setContactAddress(jsonObject1.getString("Address"));
                        contactUsModel.setContactPhone(jsonObject1.getString("Phone"));
                        contactUsModel.setContactEmail(jsonObject1.getString("Email"));

                        lat = Double.valueOf(jsonObject1.getString("latitude"));
                        lng = Double.valueOf(jsonObject1.getString("longitude"));
                        final String name = jsonObject1.getString("Images");
                        final String imageUrl= "http://stpaulsmarthomabahrain.com/membershipform/church_admin/Contact/"+jsonObject1.getString("Images");

                        //File path = getOutputMediaFile("Contact",name);


                        File sampleDir = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain"+"/"+"Contact");
                        if (!sampleDir.exists()) {
                            if (!sampleDir.mkdirs()) {
                                return;
                            }
                        }
                        // Log.e("Saving Image", "");
                        File mediaFile;
                        String mImageName = name;
                        mediaFile = new File(sampleDir.getPath() + File.separator + mImageName);
                        if(!mediaFile.exists()) {
                           // new DownloadThread(imageUrl,mediaFile.getAbsolutePath(),10).run();;
                            Intent intent = new Intent(context, DownloadService.class);
                            intent.putExtra("imageUrl",imageUrl);
                            intent.putExtra("imagePath",mediaFile.getAbsolutePath());
                            intent.putExtra("quality",10);
                            context.startService(intent);

//                            Intent intent = new Intent(FetchDataService.context, DownloadService.class);
//                            intent.putExtra("imageUrl",imageUrl);
//                            intent.putExtra("imagePath",mediaFile.getAbsolutePath());
//                            startService(intent);
                            //new AsyncTaskRunner().execute(imageUrl, mediaFile.getAbsolutePath());
                        }
                        else
                        {
                            //Log.e("File check","Already Exists");
                        }


                        contactUsModel.setContactImage(mediaFile.getAbsolutePath());

                        dbHelper.addContact(contactUsModel);



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    private void getTiming(final String orgNumber) {
        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getOrgTiming(orgNumber);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                Log.e("URL", "=====" + response.raw().request().url());

                //Log.e("getting our vicar", ""+response.body().toString());

                if (response.isSuccessful() && response.body() != null) {
                    try {

                        JSONObject jsonObject = new JSONObject(response.body().toString());

                        if (jsonObject.has("Data")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("Data");

                            //Log.e("Array Length",""+jsonArray.length());


                            //Log.e("Member Type",""+jsonArray1.getJSONObject(0).getString("Member_type"));

                            if (jsonArray.length() > 0) {
                                for (int j = 0; j < jsonArray.length(); j++) {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                                    orgTimingModel = new OrgTimingModel(jsonObject1.getString("Practice")
                                            , jsonObject1.getString("Meeting"),
                                            orgNumber,
                                            jsonObject1.getString("Member_type")

                                    );

                                    //header_timing.setText(jsonObject1.getString("Member_type"));

//                                    orgDetailModel.setName(jsonObject1.getString("Name"));
//                                    orgDetailModel.setPhone(jsonObject1.getString("Phone"));
//                                    orgDetailModel.setDesignation(jsonObject1.getString("Designation"));
//                                    orgDetailModel.setOrgNumber(orgNumber);
//                                    orgDetailModel.setMemberType(jsonObject1.getString("Member_type")
//                                    );
                                    //timeList.add(orgTimingModel);

                                    dbHelper.addOrganisationsTiming(orgTimingModel);
                                    // Log.e("Name", "Name=" + jsonObject1.getString("Name") + " Phone" + jsonObject1.getString("Phone"));
                                    //dataList.add(orgDetailModel);
                                }

                            }

                        } else {
                            //Toast.makeText(OrganisationDetailActivity.this, "No data found", Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("Exception timing", e.getMessage());

                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    private void getOrganisation(final String orgNumber) {





        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getOrgDetail(orgNumber);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //Log.d("URL", "=====" + response.raw().request().url());

                //Log.e("getting our vicar", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {

                        //dbHelper.deleteTable(DbContract.DbEntry.TABLE_ORGANISATIONS);

                        JSONObject jsonObject = new JSONObject(response.body().toString());

                        JSONArray jsonArray = jsonObject.getJSONArray("Data");

                        // Log.e("Array Length",""+jsonArray.length()+  jsonArray.getJSONObject(0));
//                        for(int i=0;i<jsonArray.length();i++) {
//                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//

//
//                        }

                        for(int i=0;i<jsonArray.length();i++) {
                            JSONArray jsonArray1 = jsonArray.getJSONArray(i);
                            // Log.e("Array Length",""+jsonArray1.length());
                            if(jsonArray1!=null && jsonArray1.length()>0) {
                                orgSectionModel = new OrgSectionModel();
                                orgSectionModel.setSectionNumber(orgNumber);
                                //Log.e("Org no", "" + orgNumber + "membertype = ");


                                orgSectionModel.setSection(jsonArray1.getJSONObject(0).getString("Member_type"));
                                dbHelper.addOrgSection(orgSectionModel);
                            }
                            for (int j = 0; j < jsonArray1.length(); j++) {
                                JSONObject jsonObject1 = jsonArray1.getJSONObject(j);

//
//                                orgDetailModel.setName(jsonObject1.getString("Name"));
//                                orgDetailModel.setPhone(jsonObject1.getString("Phone"));
//                                orgDetailModel.setDesignation(jsonObject1.getString("Designation"));
//                                orgDetailModel.setOrgNumber(orgNumber);
//                                orgDetailModel.setMemberType(jsonObject1.getString("Member_type")
//                                );
                                orgDetailModel = new OrgDetailModel(jsonObject1.getString("Name")
                                        ,jsonObject1.getString("Phone")
                                        ,jsonObject1.getString("Designation"),
                                        orgNumber,
                                        jsonObject1.getString("Member_type")
                                );
                                dbHelper.addOrganisations(orgDetailModel);
                            }
                            //dbHelper.addOrganisations(orgDetailModel);

                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                        //Log.e("Error org",e.getMessage());
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    private void getParishData(String user,String pwd) {



        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getParishMemberDirectory(user,pwd);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //Log.d("URL", "=====" + response.raw().request().url());

                // Log.e("getting about us", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {
                        dbHelper.deleteTable(DbContract.DbEntry.TABLE_PARISH);

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        //    Log.e("Success message",jsonObject.getString("success"));

                        //   Log.e("Success is" , " "+ jsonObject.get("success"));
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        for(int i=0;i<jsonArray.length();i++) {
                            final JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            final String name = jsonObject1.getString("memberimagepath");
                            final String actualImage = name.replace("images/","");

                            final String imageUrl= "http://stpaulsmarthomabahrain.com/membershipform/"+jsonObject1.getString("memberimagepath");

                            //File path = getOutputMediaFile("Contact",name);


                            File sampleDir = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain"+"/"+"ParishMember");
                            if (!sampleDir.exists()) {
                                if (!sampleDir.mkdirs()) {
                                    return;
                                }
                            }
                            // Log.e("Saving Image", "");
                            File mediaFile;
                            String mImageName = actualImage;
                            mediaFile = new File(sampleDir.getPath() + File.separator + mImageName);
                            if(!mediaFile.exists()) {
                               // new DownloadThread(imageUrl,mediaFile.getAbsolutePath(),10).run();;
                                Intent intent = new Intent(context, DownloadService.class);
                                intent.putExtra("imageUrl",imageUrl);
                                intent.putExtra("imagePath",mediaFile.getAbsolutePath());
                                intent.putExtra("quality",10);
                                context.startService(intent);

//                                Intent intent = new Intent(FetchDataService.context, DownloadService.class);
//                                intent.putExtra("imageUrl",imageUrl);
//                                intent.putExtra("imagePath",mediaFile.getAbsolutePath());
//                                startService(intent);
                                //new AsyncTaskRunner().execute(imageUrl, mediaFile.getAbsolutePath());
                            }
                            else
                            {
                                // Log.e("File check","Already Exists");
                            }

                            //------Wife Image===========//

                            final String name1 = jsonObject1.getString("wife_image");
                            final String actualImage1 = name1.replace("images/","");
                            final String imageUrl1= "http://stpaulsmarthomabahrain.com/membershipform/"+jsonObject1.getString("wife_image");

                            //File path = getOutputMediaFile("Contact",name);


                            File sampleDir1 = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain"+"/"+"ParishMemberWife");
                            if (!sampleDir1.exists()) {
                                if (!sampleDir1.mkdirs()) {
                                    return;
                                }
                            }
                            // Log.e("Saving Image", "");
                            File mediaFile1;
                            String mImageName1 = actualImage1;
                            mediaFile1 = new File(sampleDir1.getPath() + File.separator + mImageName1);
                            if(!mediaFile1.exists()) {
                              //  new DownloadThread(imageUrl,mediaFile.getAbsolutePath(),10).run();;
                                Intent intent = new Intent(context, DownloadService.class);
                                intent.putExtra("imageUrl",imageUrl1);
                                intent.putExtra("imagePath",mediaFile1.getAbsolutePath());
                                intent.putExtra("quality",10);
                                context.startService(intent);

//                                Intent intent = new Intent(FetchDataService.context, DownloadService.class);
//                                intent.putExtra("imageUrl",imageUrl1);
//                                intent.putExtra("imagePath",mediaFile1.getAbsolutePath());
//                                startService(intent);
                                //new AsyncTaskRunner().execute(imageUrl1, mediaFile1.getAbsolutePath());
                            }
                            else
                            {
                                //Log.e("File check","Already Exists");
                            }
                            Thread threadAbout = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        getChildData(jsonObject1.getString("membernamerollno"));
                                    }
                                    catch (JSONException e)
                                    {
                                        e.printStackTrace();}
                                }
                            });
                            threadAbout.start();




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
                                    , mediaFile.getAbsolutePath()
                                    ,mediaFile1.getAbsolutePath()
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
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    private void getPublicationsData() {


        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getPublications();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //Log.d("URL", "=====" + response.raw().request().url());

                //Log.e("getting about us", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {
                        dbHelper.deleteTable(DbContract.DbEntry.TABLE_PUBLICATIONS);

                        JSONObject jsonObject = new JSONObject(response.body().toString());

                        JSONArray jsonArray = jsonObject.getJSONArray("Data");

                        //Log.e("Array Length",""+jsonArray.length()+  jsonArray.getJSONObject(0));
                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            parishBulletinModel = new ParishBulletinModel(jsonObject1.getString("Name")
                                    ,jsonObject1.getString("file_name"),""

                            );
                            dbHelper.addPublications(parishBulletinModel);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        //Log.e("Exception pub",e.getMessage());
                    }

                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    private void getAchensData() {


        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getAchens();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //Log.d("URL", "=====" + response.raw().request().url());

                //Log.e("getting about us", ""+response.body().toString());

                if(response.isSuccessful() && response.body()!=null) {
                    try {
                        dbHelper.deleteTable(DbContract.DbEntry.TABLE_ACHENS);

                        JSONObject jsonObject = new JSONObject(response.body().toString());

                        JSONArray jsonArray = jsonObject.getJSONArray("Data");

                        //Log.e("Array Length",""+jsonArray.length()+  jsonArray.getJSONObject(0));
                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            achensModel = new AchensModel(jsonObject1.getString("Name")
                                    ,jsonObject1.getString("file_name"),""

                            );
                            dbHelper.addAchens(achensModel);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                        //Log.e("Exception pub",e.getMessage());
                    }

                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }


    private void getYearPlannerData() {
//
//        final ProgressDialog progressDialog = new ProgressDialog(context);
//               progressDialog.show();
//
//



        APIService service = APIUrl.getClient().create(APIService.class);

        Call<JsonElement> call = service.getYearPlanner();

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
               // Log.d("URL", "=====" + response.raw().request().url());

                //Log.e("getting about us", ""+response.body().toString());


                if(response.isSuccessful() && response.body()!=null) {
                    try {

                        // dbHelper.deleteTable(DbContract.DbEntry.TABLE_ABOUT_US);

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        //Log.e("Success message",jsonObject.getString("success"));

                        // Log.e("Success is" , " "+ jsonObject.get("success"));
                        JSONArray jsonArray = jsonObject.getJSONArray("Data");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);

                        final String name = jsonObject1.getString("images");

                        final String imageUrl= "http://stpaulsmarthomabahrain.com/membershipform/church_admin/Year_Images/"+jsonObject1.getString("images");

                        //File path = getOutputMediaFile("Contact",name);


                        File sampleDir = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain"+"/"+"YearPlanner");
                        if (!sampleDir.exists()) {
                            if (!sampleDir.mkdirs()) {
                                return;
                            }
                        }
                        // Log.e("Saving Image", "");
                        File mediaFile;
                        String mImageName = name;
                        mediaFile = new File(sampleDir.getPath() + File.separator + mImageName);
                        SaveShared("YearPlanner",mediaFile.getAbsolutePath());

                        if(!mediaFile.exists()) {
                            //new DownloadThread(imageUrl,mediaFile.getAbsolutePath(),10).run();;
                            Intent intent = new Intent(context, DownloadService.class);
                            intent.putExtra("imageUrl",imageUrl);
                            intent.putExtra("imagePath",mediaFile.getAbsolutePath());
                            intent.putExtra("quality",10);
                            context.startService(intent);

//                            Intent intent = new Intent(FetchDataService.context, DownloadService.class);
//                            intent.putExtra("imageUrl",imageUrl);
//                            intent.putExtra("imagePath",mediaFile.getAbsolutePath());
//                            startService(intent);
                            //new AsyncTaskRunner().execute(imageUrl, mediaFile.getAbsolutePath());
                        }
                        else
                        {
                            //Log.e("File check","Already Exists");
                        }


                        //dbHelper.addAboutUsContent(aboutUsModel);

                    } catch (Exception e) {
                        e.printStackTrace();
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

                if(response.isSuccessful() && response.body()!=null) {
                    try {

                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        //   Log.e("Success message",jsonObject.getString("success"));

                        //   Log.e("Success is" , " "+ jsonObject.get("success"));

                        if(jsonObject.has("Data")) {
                            JSONArray jsonArray = jsonObject.getJSONArray("Data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                final String name = jsonObject1.getString("childsimage");
                                final String actualImage = name.replace("images/", "");

                                final String imageUrl = "http://stpaulsmarthomabahrain.com/membershipform/" + jsonObject1.getString("childsimage");
                                //Log.e("Child Image Url", "" + imageUrl + "and Roll no =" + rollno);
                                //File path = getOutputMediaFile("Contact",name);


                                File sampleDir = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain" + "/" + "ParishMemberChild");
                                if (!sampleDir.exists()) {
                                    if (!sampleDir.mkdirs()) {
                                        return;
                                    }
                                }
                                // Log.e("Saving Image", "");
                                File mediaFile;
                                String mImageName = actualImage;
                                mediaFile = new File(sampleDir.getPath() + File.separator + mImageName);
                                if (!mediaFile.exists()) {
//
                                    //Log.e("getting ","child image");
                                    Intent intent = new Intent(context, DownloadService.class);
                                    intent.putExtra("imageUrl", imageUrl);
                                    intent.putExtra("imagePath", mediaFile.getAbsolutePath());
                                    intent.putExtra("quality",10);
                                    context.startService(intent);
                                    //new AsyncTaskRunner().execute(imageUrl, mediaFile.getAbsolutePath());
                                } else {
                                    // Log.e("File check","Already Exists");
                                }

                                childModel = new ChildModel(
                                        jsonObject1.getString("childname")
                                        , jsonObject1.getString("childinbaharian")
                                        , jsonObject1.getString("sexchild")
                                        , jsonObject1.getString("datebrithchild")
                                        , jsonObject1.getString("bloodchild")
                                        , jsonObject1.getString("studentchild")
                                        , jsonObject1.getString("employechild")
                                        , mediaFile.getAbsolutePath()
                                        , jsonObject1.getString("employername")
                                        , jsonObject1.getString("rollno")
                                );
                                childModelList.add(childModel);
                                dbHelper.addParishChild(childModel);

                            }
                        }



                    } catch (Exception e) {
                        e.printStackTrace();

                       // Log.e("Exception",""+rollno);
                    }
                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });

    }

    public void SaveShared(String key,String value)
    {
        //Log.e("Key Name",key + "value"+value);
        SharedPreferences preferences = context.getSharedPreferences("Year", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(key,value);
        editor.commit();
    }

    private File getOutputMediaFile(String groupName,String name) {

        File sampleDir = new File(Environment.getExternalStorageDirectory(), "/StPaulsBahrain"+"/"+groupName);
        if (!sampleDir.exists()) {
            if (!sampleDir.mkdirs()) {
                return null;
            }
        }
        //Log.e("Saving Image", "");
        File mediaFile;
        String mImageName = name;
        mediaFile = new File(sampleDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    
    
}
