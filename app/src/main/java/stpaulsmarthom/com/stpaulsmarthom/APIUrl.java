package stpaulsmarthom.com.stpaulsmarthom;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Paras-Android on 22-12-2017.
 */

public class APIUrl {
    public static final String BASE_URL = "http://stpaulsmarthomabahrain.com/WebServices/";

    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
