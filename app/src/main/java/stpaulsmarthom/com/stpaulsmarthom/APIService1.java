package stpaulsmarthom.com.stpaulsmarthom;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Paras-Android on 22-12-2017.
 */

public interface APIService1 {

    @GET("stpauls.php?")
    Call<JsonElement> getWorking(@Query("message") String message);


}
