package com.example.shruti.demo.rest;

import com.example.shruti.demo.model.Post;
import com.example.shruti.demo.model.RetroPhoto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Shruti on 23/10/18.
 * defining the endpoints
 */

public interface ApiInterface {

    @GET("/photos")
    Call<List<RetroPhoto>> getAllPhotos();

    @POST("/posts")
    @FormUrlEncoded
    Call<Post> savePost(@Field("title") String title,
                        @Field("body") String body,
                        @Field("userId") long userId);

}
