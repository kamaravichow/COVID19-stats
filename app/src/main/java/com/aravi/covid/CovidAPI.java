package com.aravi.covid;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class CovidAPI {
    private static final String url = "https://api.covid19api.com/";

    public static PostService postService = null;

    public static PostService getService() {
        if (postService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            postService = retrofit.create(PostService.class);
        }
        return postService;
    }


    public interface PostService {
        @GET("summary")
        Call<API> getData();
    }

}

