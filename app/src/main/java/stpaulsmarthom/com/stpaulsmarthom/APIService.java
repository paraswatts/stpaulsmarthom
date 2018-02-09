package stpaulsmarthom.com.stpaulsmarthom;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Paras-Android on 22-12-2017.
 */

public interface APIService {

    @GET("organizations.php?pages=about_us")
    Call<JsonElement> getAboutUs();



    @GET("organizations.php?pages=Policy")
    Call<JsonElement> getPrivacy();

    @GET("organizations.php?pages=our_vicar")
    Call<JsonElement> getOurVicar();

    @GET("organizations.php?pages=bishops")
    Call<JsonElement> getBishops();

    @GET("organizations.php?org=7")
    Call<JsonElement> getKaisthanaSamithi();

    @GET("organizations.php?pages=Contact")
    Call<JsonElement> getContact();

    @GET("organizations.php?pages=resources")
    Call<JsonElement> getResources();

    @GET("organizations.php?pages=publications")
    Call<JsonElement> getPublications();

    @GET("organizations.php?pages=achens_letter")
    Call<JsonElement> getAchens();

    @GET("organizations.php?")
    Call<JsonElement> getOrgDetail(@Query("org") String orgNumber);


    @GET("organizationsTiming.php?")
    Call<JsonElement> getOrgTiming(@Query("timing") String orgNumber);


    @GET("E-directory.php?")
    Call<JsonElement> getParishMemberDirectory(@Query("user") String user,@Query("pwd") String pwd);

    @GET("organizations1.php?pages=Year_Planner")
    Call<JsonElement> getYearPlanner();

    @GET("E-directory.php?")
    Call<JsonElement> getParishMemberChild(@Query("rollno") String rollNo);

    @GET("prayer_api.php?")
    Call<JsonElement> sendPrayer(@Query("Name") String name,@Query("Email") String email,@Query("PhoneNumber") String phone,@Query("RollNumber") String rollno,@Query("PrayerSubject") String prayerSubject,@Query("PrayerMessage") String prayerMessage);

    @GET("Sunday_School_Prayer.php?")
    Call<JsonElement> sendPrayerSundaySchool(@Query("Name") String name,@Query("Email") String email,@Query("PhoneNumber") String phone,@Query("RollNumber") String rollno,@Query("PrayerSubject") String prayerSubject,@Query("PrayerMessage") String prayerMessage);
}
