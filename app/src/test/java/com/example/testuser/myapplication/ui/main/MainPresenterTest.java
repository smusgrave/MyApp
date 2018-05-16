package com.example.testuser.myapplication.ui.main;

import com.example.testuser.myapplication.model.Post;
import com.example.testuser.myapplication.rules.RxSchedulersOverrideRule;
import com.example.testuser.myapplication.service.MyService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.plugins.RxJavaPlugins;

import static junit.framework.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterTest {

    @Rule
    public RxSchedulersOverrideRule mRxSchedulersOverrideRule = new RxSchedulersOverrideRule();

    @Mock
    private MainContract.View mView;

    @Mock
    private MyService mService;

    private MainPresenter mPresenter;
    private List<Post> mMockPosts = new ArrayList<>(0);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new MainPresenter(mView, mService);
        when(mService.getPosts()).thenReturn(Observable.just(mMockPosts));
    }

    @Test
    public void refreshPosts() {
        mPresenter.refreshPosts();
        verify(mView, times(1)).showProgress();
        verify(mService, times(1)).getPosts();
    }

    @Test
    public void onResultSuccess() {
        when(mService.getPosts()).thenReturn(Observable.just(mMockPosts));
        mPresenter.refreshPosts();
        verify(mView, times(1)).hideProgress();
        verify(mView, times(1)).showPosts(mMockPosts);
        verify(mView, never()).showError(anyString());
    }

    @Test
    public void onResultError() {
        RxJavaPlugins.setErrorHandler(throwable -> {});
        final String errorMessage = "Error";
        when(mService.getPosts()).thenReturn(Observable.error(new RuntimeException(errorMessage)));
        mPresenter.refreshPosts();
        verify(mView, times(1)).hideProgress();
        verify(mView, never()).showPosts(anyList());
        verify(mView, times(1)).showError(errorMessage);
    }

    @Test
    public void onDestroy() {
        mPresenter.onDestroy();
        assertNull(mPresenter.getView());
    }
}
