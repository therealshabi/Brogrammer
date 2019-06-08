package com.technolifestyle.therealshabi.brogrammar

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.technolifestyle.therealshabi.brogrammar.requestUtils.Requests
import com.technolifestyle.therealshabi.brogrammar.sharedPreferenceUtils.SharedPreferenceStorage
import com.technolifestyle.therealshabi.brogrammar.stringUtility.StringUtils
import com.technolifestyle.therealshabi.brogrammar.stringUtility.StringUtils.slackCodeParameter

class WebViewActivity : AppCompatActivity() {
    internal lateinit var loadingProgressBar: ProgressBar
    private lateinit var toolbar: Toolbar

    @SuppressLint("SetJavaScriptEnabled")
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val mWebView = findViewById<WebView>(R.id.webview)

        toolbar = findViewById(R.id.activity_web_view_toolbar)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp)
        }

        val webSettings = mWebView.settings
        webSettings.javaScriptEnabled = true
        webSettings.displayZoomControls = true
        webSettings.builtInZoomControls = true

        mWebView.loadUrl(CODE_PARAMETER_AUTHORIZATION_URL)
        mWebView.webViewClient = MyWebViewClient()

        loadingProgressBar = findViewById(R.id.activity_web_view_progressbar)

        mWebView.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView, newProgress: Int) {

                super.onProgressChanged(view, newProgress)
                loadingProgressBar.progress = newProgress
                if (newProgress == 100) {
                    loadingProgressBar.visibility = View.GONE

                } else {
                    loadingProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            sendRequests()
            SharedPreferenceStorage.setSharedPreferenceMainActivityFlag(baseContext, true)
            startActivity(Intent(this@WebViewActivity, SlackActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun sendRequests() {
        val req = Requests()
        val uri = Uri.parse(StringUtils.currentURL)
        val code = uri.getQueryParameter("code")
        slackCodeParameter = code
        if (slackCodeParameter != null)
            Log.d("Code", slackCodeParameter)
        req.getSlackCode(baseContext, GET_ACCESS_TOKEN_URL + code!!)
    }

    private inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            StringUtils.currentURL = url
            view.loadUrl(url)
            return true
        }
    }

    companion object {
        const val CODE_PARAMETER_AUTHORIZATION_URL = "https://slack.com/oauth/pick?scope=users%3Aread+channels%3Ahistory&client_id=128465399559.144229293015"
        const val GET_ACCESS_TOKEN_URL = "https://slack.com/api/oauth.access?client_secret=f711ebf28f95d762bb49a8b1b2ad6c9e&client_id=128465399559.144229293015&code="
    }
}
