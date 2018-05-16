package com.example.testuser.myapplication.ui.main;

import com.example.testuser.myapplication.model.Post;

import java.util.List;

interface MainContract {

    interface View {

        void showPosts(List<Post> posts);

        void showError(String error);

        void showProgress();

        void hideProgress();

    }

    interface Presenter {

        void refreshPosts();

        void onResultSuccess(List<Post> posts);

        void onResultError(Throwable throwable);

        void onDestroy();

    }

}
