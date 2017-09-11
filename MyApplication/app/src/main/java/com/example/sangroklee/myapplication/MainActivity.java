package com.example.sangroklee.myapplication;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by sangroklee on 2017. 9. 7..
 */

public class MainActivity extends AppCompatActivity{
    private WebView mWebView;
    private BackPressHandler backPressHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = (WebView)findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setTextZoom(100);
        mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);

        mWebView.loadUrl("http://www.timeforpoetry.com");
        backPressHandler = new BackPressHandler(this);
    }

    private class MyWebViewClient extends WebViewClient {
        public static final String INTENT_PROTOCOL_START = "intent:";
        public static final String INTENT_PROTOCOL_INTENT = "#Intent;";
        public static final String INTENT_PROTOCOL_END = ";end;";
        public static final String GOOGLE_PLAY_STORE_PREFIX = "market://details?id=";

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
            return handleUri(view, request.getUrl().toString());
        }

        @SuppressWarnings("deprecation")
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return handleUri(view, url);
        }

        private boolean handleUri(WebView view,final String url){

            if (url.startsWith(INTENT_PROTOCOL_START)) {
                final int customUrlStartIndex = INTENT_PROTOCOL_START.length();
                final int customUrlEndIndex = url.indexOf(INTENT_PROTOCOL_INTENT);
                if (customUrlEndIndex < 0) {
                    return false;
                } else {
                    final String customUrl = url.substring(customUrlStartIndex, customUrlEndIndex);
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(customUrl)));
                    } catch (ActivityNotFoundException e) {
                        final int packageStartIndex = customUrlEndIndex + INTENT_PROTOCOL_INTENT.length();
                        final int packageEndIndex = url.indexOf(INTENT_PROTOCOL_END);

                        final String packageName = url.substring(packageStartIndex, packageEndIndex < 0 ? url.length() : packageEndIndex);
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_STORE_PREFIX + packageName)));
                    }
                    return true;
                }
            }

            else{
                view.loadUrl(url);
                return true;
            }

        }
    }

    @Override
    public void onBackPressed(){
        if(mWebView.canGoBack()){
            mWebView.goBack();
        }
        else if(!mWebView.canGoBack()){
            backPressHandler.onBackPressed();
        }
    }
}
