package com.technolifestyle.therealshabi.brogrammar

import android.content.Intent
import android.os.Bundle
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity

import com.technolifestyle.therealshabi.brogrammar.sharedPreferenceUtils.SharedPreferenceStorage

class MainActivity : AppCompatActivity() {
    private lateinit var mButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (SharedPreferenceStorage.getSharedPreferenceMainActivity(baseContext)) {
            startActivity(Intent(this@MainActivity, SlackActivity::class.java))
        }

        mButton = findViewById(R.id.button)


        mButton.setOnClickListener { startActivity(Intent(this@MainActivity, WebViewActivity::class.java)) }

    }

    override fun onResume() {
        if (SharedPreferenceStorage.getSharedPreferenceMainActivity(baseContext)) {
            startActivity(Intent(this@MainActivity, SlackActivity::class.java))
        }
        super.onResume()
    }
}
