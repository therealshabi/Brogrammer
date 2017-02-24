package com.technolifestyle.therealshabi.brogrammar;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.technolifestyle.therealshabi.brogrammar.RequestUtils.Requests;
import com.technolifestyle.therealshabi.brogrammar.RequestUtils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

import static com.technolifestyle.therealshabi.brogrammar.RequestUtils.StringUtils.setSlackCodeParameter;

public class MainActivity extends AppCompatActivity {
    Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.button);



        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WebViewActivity.class));
            }
        });

    }


}
