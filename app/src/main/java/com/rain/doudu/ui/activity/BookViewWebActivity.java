package com.rain.doudu.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.rain.doudu.R;
import com.rain.doudu.utils.common.ToastUtils;

public class BookViewWebActivity extends AppCompatActivity {

    //String url = "http://dushu.m.baidu.com/searchresult?query=搜索结果&word=";
    //String url = "https://m.douban.com/book/subject/";
    //String url = "https://read.douban.com/search?q=";
    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_view_web);

        mWebView = (WebView) findViewById(R.id.search_book);
        String ebookUrl = getIntent().getStringExtra("EBOOK_URL");
        if (ebookUrl == null) {
            ToastUtils.showLong("对不起，该书不提供试读");
        } else {
            //https://read.douban.com/ebook/1162265/
            //https://read.douban.com/reader/ebook/1162265/
            String str = ebookUrl.substring(24);
            String readURL = "https://read.douban.com/reader/";
            mWebView.loadUrl(readURL+str);
            //启用JS
            mWebView.getSettings().setJavaScriptEnabled(true);
            //设置是否支持变焦
            mWebView.getSettings().setSupportZoom(true);
            //启用缓存
            mWebView.getSettings().setAppCacheEnabled(true);
            //设置缓存模式
            mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

            //设置不用系统浏览器打开,直接显示在当前Webview
            mWebView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });


        }

    }
}

