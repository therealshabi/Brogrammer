package com.technolifestyle.therealshabi.brogrammar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.technolifestyle.therealshabi.brogrammar.RequestUtils.Requests;
import com.technolifestyle.therealshabi.brogrammar.SharedPreferenceUtils.SharedPreferenceStorage;
import com.technolifestyle.therealshabi.brogrammar.StringUtility.StringUtils;

import static com.technolifestyle.therealshabi.brogrammar.StringUtility.StringUtils.setSlackCodeParameter;

public class WebViewActivity extends AppCompatActivity {

    public static final String CODE_PARAMETER_AUTHORIZATION_URL = "https://slack.com/oauth/pick?scope=users%3Aread+channels%3Ahistory&client_id=128465399559.144229293015";
    public static final String GET_ACCESS_TOKEN_URL = "https://slack.com/api/oauth.access?client_secret=f711ebf28f95d762bb49a8b1b2ad6c9e&client_id=128465399559.144229293015&code=";
    ProgressBar loadingProgressBar;
    Toolbar toolbar;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        WebView mWebView = findViewById(R.id.webview);

        toolbar = findViewById(R.id.activity_web_view_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        }

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDisplayZoomControls(true);
        webSettings.setBuiltInZoomControls(true);

        mWebView.loadUrl(CODE_PARAMETER_AUTHORIZATION_URL);
        mWebView.setWebViewClient(new MyWebViewClient());

        loadingProgressBar = findViewById(R.id.activity_web_view_progressbar);

        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                super.onProgressChanged(view, newProgress);
                loadingProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    loadingProgressBar.setVisibility(View.GONE);

                } else {
                    loadingProgressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            sendRequests();
            SharedPreferenceStorage.setSharedPreferenceMainActivityFlag(getBaseContext(), true);
            startActivity(new Intent(WebViewActivity.this, SlackActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendRequests() {
        Requests req = new Requests();
        Uri uri = Uri.parse(StringUtils.getCurrentURL());
        String code = uri.getQueryParameter("code");
        setSlackCodeParameter(code);
        if (StringUtils.getSlackCodeParameter() != null)
            Log.d("Code", StringUtils.getSlackCodeParameter());
        req.getSlackCode(getBaseContext(), GET_ACCESS_TOKEN_URL + code);
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            StringUtils.setCurrentURL(url);
            view.loadUrl(url);
            return true;
        }
    }
}
