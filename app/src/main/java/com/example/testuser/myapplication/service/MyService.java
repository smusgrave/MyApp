package com.example.testuser.myapplication.service;

import com.example.testuser.myapplication.model.Post;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface MyService {

    String ENDPOINT = "https://jsonplaceholder.typicode.com/";

    @GET("posts")
    Observable<List<Post>> getPosts();

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static MyService newMyService() {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(MyService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit.create(MyService.class);
        }
    }
}