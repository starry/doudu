package com.rain.doudu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rain.doudu.R;

import butterknife.BindView;

/**
 * Created by rain on 2017/4/28.
 */

public class BookWebFragment extends BaseFragment {
    @BindView(R.id.webView)
    WebView mWebView;
    //@BindView(R.id.toolbar)
    //Toolbar mToolbar;
    //#FF7A18
   // private String url = "http://dushu.m.baidu.com/cate";
    private String url = "http://dushu.m.baidu.com/cate";

    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_book_web, container, false);


    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData(boolean isSavedNull) {


        mWebView.loadUrl(url);

        //启用JS
        mWebView.getSettings().setJavaScriptEnabled(true);
        //设置是否支持变焦
        mWebView.getSettings().setSupportZoom(true);
        //启用缓存
        mWebView.getSettings().setAppCacheEnabled(true);
        //设置缓存模式
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    public static BookWebFragment newInstance() {
        return new BookWebFragment();
    }

   /* public void onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack(); //goBack()表示返回WebView的上一页面
        }
    }*/
}
