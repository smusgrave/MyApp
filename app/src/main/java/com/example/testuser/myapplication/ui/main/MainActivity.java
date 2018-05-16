package com.example.testuser.myapplication.ui.main;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.testuser.myapplication.R;
import com.example.testuser.myapplication.model.Post;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainPresenter mPresenter;
    private MainAdapter mAdapter;
    private List<Post> mPosts;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mPresenter = new MainPresenter(this);
        mPosts = new ArrayList<>();
        mAdapter = new MainAdapter(mPosts);

        mRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        mRefreshLayout.setOnRefreshListener(() -> mPresenter.refreshPosts());

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        mPresenter.refreshPosts();
    }

    @Override
    public void showPosts(List<Post> posts) {
        mPosts.clear();
        mPosts.addAll(posts);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, "Error: " + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }
}
