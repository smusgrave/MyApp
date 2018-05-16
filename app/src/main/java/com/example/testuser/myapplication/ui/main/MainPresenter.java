package com.example.testuser.myapplication.ui.main;

import com.example.testuser.myapplication.model.Post;
import com.example.testuser.myapplication.service.MyService;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements MainContract.Presenter {

    private final MyService mService;
    private MainContract.View mView;

    MainPresenter(MainContract.View view) {
        this(view, MyService.Creator.newMyService());
    }

    // Added for testing as no DI present
    MainPresenter(MainContract.View view, MyService service) {
        mView = view;
        mService = service;
    }

    @Override
    public void refreshPosts() {
        mView.showProgress();
        mService.getPosts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnNext(this::onResultSuccess)
                .doOnError(this::onResultError)
                .subscribe();
    }

    @Override
    public void onResultSuccess(List<Post> posts) {
        if (mView != null) {
            mView.showPosts(posts);
            mView.hideProgress();
        }
    }

    @Override
    public void onResultError(Throwable throwable) {
        // TODO Add more elegant error handling
        if (mView != null) {
            mView.showError(throwable.getMessage());
            mView.hideProgress();
        }
    }

    @Override
    public void onDestroy() {
        mView = null;
    }

    public MainContract.View getView() {
        return mView;
    }
}
