package com.whysly.alimseolap1.interfaces;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MyService {

    @POST("api/noti_data/")
    Call<JsonObject> createPost(@Body JsonObject jsonObject);

}