package com.technolifestyle.therealshabi.brogrammar;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.technolifestyle.therealshabi.brogrammar.RequestUtils.Requests;
import com.technolifestyle.therealshabi.brogrammar.RequestUtils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.technolifestyle.therealshabi.brogrammar.RequestUtils.StringUtils.setSlackCodeParameter;

public class WebViewActivity extends AppCompatActivity {

    private WebView mWebView;

    public static final String CodeParameterAuthorizationURL = "https://slack.com/oauth/pick?scope=channels%3Ahistory&client_id=128465399559.144229293015";
    public static final String GET_ACCESS_TOKEN_URL = "https://slack.com/api/oauth.access?client_secret=f711ebf28f95d762bb49a8b1b2ad6c9e&client_id=128465399559.144229293015&code=";


    ProgressBar loadingProgressBar, loadingTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mWebView = (WebView) findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDisplayZoomControls(true);
        webSettings.setBuiltInZoomControls(true);

        mWebView.loadUrl(CodeParameterAuthorizationURL);
        mWebView.setWebViewClient(new MyWebViewClient());

        loadingProgressBar = (ProgressBar) findViewById(R.id.activity_web_view_progressbar);

        mWebView.setWebChromeClient(new WebChromeClient() {

            // this will be called on page loading progress

            @Override

            public void onProgressChanged(WebView view, int newProgress) {

                super.onProgressChanged(view, newProgress);


                loadingProgressBar.setProgress(newProgress);
                //            loadingTitle.setProgress(newProgress);
                // hide the progress bar if the loading is complete

                if (newProgress == 100) {
                    loadingProgressBar.setVisibility(View.GONE);

                } else {
                    loadingProgressBar.setVisibility(View.VISIBLE);

                }

            }

        });

        //Set up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_web_view_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        }
    }

    private class MyWebViewClient extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            StringUtils.setCurrentURL(url);
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                try {
                    sendRequests();
                } catch (JSONException e) {
                    Log.d("JSON Exception", e.toString());
                }
                startActivity(new Intent(WebViewActivity.this,MainActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendRequests() throws JSONException {
        Requests req = new Requests();
        Uri uri = Uri.parse(StringUtils.getCurrentURL());
        String code = uri.getQueryParameter("code");
        setSlackCodeParameter(code);
        if(StringUtils.getSlackCodeParameter()!=null)
        Log.d("Code",StringUtils.getSlackCodeParameter());
        req.getSlackCode(getBaseContext(), GET_ACCESS_TOKEN_URL+code);
    }
}
