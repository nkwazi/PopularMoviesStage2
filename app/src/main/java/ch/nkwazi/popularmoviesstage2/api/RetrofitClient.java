package ch.nkwazi.popularmoviesstage2.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nkwazi on 06.02.19.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;
    public static final String API_BASE_URL = "https://api.themoviedb.org/3/";

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
