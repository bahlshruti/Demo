package com.example.shruti.demo.rest;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Shruti on 22/10/18.
 */
/** To send network requests to an API, we need to use the Retrofit Builder class and \
   specify the base URL for the service**/

public class REST_Client {

    static String API_BASE_URL = "https://jsonplaceholder.typicode.com";

    private  static Retrofit retrofit=null;

    public static Retrofit getClient()
    {
        if (retrofit==null){
            retrofit= new Retrofit.Builder().baseUrl(API_BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();

        }
        return retrofit;
    }

}
