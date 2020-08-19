package com.whysly.alimseolap1.Server;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    public static final String API_URL = "http://35.225.204.156:7999/";

    @GET("commets")
    Call<ResponseBody> getComment(@Query("postId") int postId);

    @POST("notification/")
    Call<JsonObject> PostToWord2Vec(@Body JsonObject jsonObject);



}
