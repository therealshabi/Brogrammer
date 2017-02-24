package com.technolifestyle.therealshabi.brogrammar.RequestUtils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shahbaz on 24/2/17.
 */

public class Requests implements Slack_Client {

    public void getSlackCode(Context context, String GET_ACCESS_TOKEN_URL) throws JSONException {
        /*jsonObject.put("client_id", CLIENT_ID);
        jsonObject.put("client_secret", CLIENT_SECRET);*/
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GET_ACCESS_TOKEN_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String accessToken = response.getString("access_token");
                    if (accessToken != null) {
                        StringUtils.setAccessToken(accessToken);
                        Log.d("Access Token", accessToken);
                    }
                } catch (JSONException e) {
                    Log.d("JSON EXCEPTION", e.toString());
                    Log.d("Response", response.toString());
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });

        Volley.newRequestQueue(context).add(jsonObjectRequest);
        Log.d("Request", jsonObjectRequest.toString());

    }


}
