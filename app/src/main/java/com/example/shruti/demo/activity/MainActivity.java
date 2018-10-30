package com.example.shruti.demo.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shruti.demo.R;
import com.example.shruti.demo.model.Post;
import com.example.shruti.demo.model.RetroPhoto;
import com.example.shruti.demo.rest.ApiInterface;
import com.example.shruti.demo.rest.REST_Client;


import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.shruti.demo.rest.REST_Client.getClient;

// using Asynchronous HTTP client
public class MainActivity extends AppCompatActivity  {

    private static String TAG="activity";
    private  TextView mResponseTv;
    ApiInterface apiservice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText titleEt = (EditText) findViewById(R.id.et_title);
        final EditText bodyEt = (EditText) findViewById(R.id.et_body);
        Button submitBtn = (Button) findViewById(R.id.btn_submit);
        mResponseTv = (TextView) findViewById(R.id.tv_response);

         apiservice = getClient().create(ApiInterface.class);

        listPhotos();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEt.getText().toString().trim();
                String body = bodyEt.getText().toString().trim();
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(body)) {
                    sendPost(title, body);
                }
            }
        });




    }
    public void listPhotos() {

        Call<List<RetroPhoto>> call = apiservice.getAllPhotos();
        call.enqueue(new Callback<List<RetroPhoto>>() {
            @Override
            public void onResponse(Call<List<RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                Log.i(TAG, "onResponse: called");
                List<RetroPhoto> photos = response.body();
                Log.i(TAG, "onResponse: " + photos.size());
            }

            @Override
            public void onFailure(Call<List<RetroPhoto>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void sendPost(String title, String body) {
        Call<Post> call = apiservice.savePost(title, body, 1);

        call.enqueue( new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {


                    if(response.isSuccessful()) {
                        showResponse(response.body().toString());
                        Log.i(TAG, "post submitted to API." + response.body().toString());
                    }
                }


            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");

            }
        } );

    }

    public void showResponse(String response) {
        if(mResponseTv.getVisibility() == View.GONE) {
            mResponseTv.setVisibility(View.VISIBLE);
        }
        mResponseTv.setText(response);
    }

}
