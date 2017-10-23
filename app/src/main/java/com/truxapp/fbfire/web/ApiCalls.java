package com.truxapp.fbfire.web;


import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by sharadsingh on 10/08/17.
 */

public interface ApiCalls {
    @POST
    Call<ResponseBody> someCallPost(@Url String url, @Body RequestBody params);

    @GET
    Call<ResponseBody> someCallGet(@Url String url, @Body RequestBody params);

    @POST
    Call<ResponseBody> someCallPostNoBody(@Url String url);

    @GET
    Call<ResponseBody> someCallGetNoBody(@Url String url);
}