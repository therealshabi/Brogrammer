package com.technolifestyle.therealshabi.brogrammar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.technolifestyle.therealshabi.brogrammar.SharedPreferenceUtils.SharedPreferenceStorage;

public class MainActivity extends AppCompatActivity {
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (SharedPreferenceStorage.getSharedPreferenceMainActivity(getBaseContext())) {
            startActivity(new Intent(MainActivity.this, SlackActivity.class));
        }

        mButton = findViewById(R.id.button);


        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WebViewActivity.class));
            }
        });

    }

    @Override
    protected void onResume() {
        if (SharedPreferenceStorage.getSharedPreferenceMainActivity(getBaseContext())) {
            startActivity(new Intent(MainActivity.this, SlackActivity.class));
        }
        super.onResume();
    }
}
