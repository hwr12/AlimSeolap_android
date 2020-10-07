package com.whysly.alimseolap1.interfaces;

import com.google.gson.JsonObject;
import com.whysly.alimseolap1.models.Age;
import com.whysly.alimseolap1.models.City;
import com.whysly.alimseolap1.models.Province;
import com.whysly.alimseolap1.models.State;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MyService {

    @POST("api/noti_data/")
    Call<JsonObject> createPost(@Body JsonObject jsonObject);



    @POST("auth")
    Call<JsonObject> postSignUpSNS(@Body JsonObject jsonObject);

    @GET("areas/states")
    Call<List<Age>> getAges();

    @GET("areas/states")
    Call<List<State>> getStates();


    @GET("areas/cities")
    Call<List<City>> getCities(@Query("parent_id") String id);

    @GET("areas/provinces")
    Call<List<Province>> getProvinces(@Query("parent_id") String id);

    @GET("me")
    Call<JsonObject> getMe(@Header("Authorization") String value);

    @PATCH("me")
    Call<JsonObject> patchMe(@Header("Authorization") String value, @Body JsonObject jsonObject);

}