package com.omeerfk.dizitakibi;

import com.omeerfk.dizitakibi.model.MostPopular;
import com.omeerfk.dizitakibi.model.TelevisionShow;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Faruk Karadeniz on 31.07.2017.
 */

public interface ShowsApi {

    @GET("most-popular")
    Call<MostPopular> getMostPopularPage(@Query("page") int page);

    @GET("search")
    Call<List<TelevisionShow>> searchByName(@Query("q") String name,
                                    @Query("page") int page);

    @GET("show-details")
    Call<TelevisionShow> getTvShow(@Query("q") String id);

    class Reference{
        public static ShowsApi getInstance(){
            final String baseUrl = "https://www.episodate.com/api/";

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(ShowsApi.class);
        }
    }
}
