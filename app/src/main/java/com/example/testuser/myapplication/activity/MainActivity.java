package com.example.testuser.myapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.testuser.myapplication.R;
import com.example.testuser.myapplication.model.Post;
import com.example.testuser.myapplication.service.MyService;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        MyService myService = MyService.Creator.newMyService();
        myService.getPosts()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(posts -> ((TextView) findViewById(R.id.text)).setText("Title: " + posts.get(0).getTitle()))
                .subscribeOn(Schedulers.io())
                .subscribe();
    }
}
