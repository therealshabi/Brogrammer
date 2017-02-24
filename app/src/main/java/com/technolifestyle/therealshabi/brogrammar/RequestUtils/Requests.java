package com.technolifestyle.therealshabi.brogrammar.RequestUtils;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.technolifestyle.therealshabi.brogrammar.SharedPreferenceUtils.SharedPreferenceStorage;
import com.technolifestyle.therealshabi.brogrammar.StringUtility.Slack_Client;
import com.technolifestyle.therealshabi.brogrammar.StringUtility.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shahbaz on 24/2/17.
 */

public class Requests implements Slack_Client {

    public void getSlackCode(final Context context, String GET_ACCESS_TOKEN_URL) throws JSONException {
        /*jsonObject.put("client_id", CLIENT_ID);
        jsonObject.put("client_secret", CLIENT_SECRET);*/
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GET_ACCESS_TOKEN_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String accessToken = response.getString("access_token");
                    String teamName = response.getString("team_name");
                    String userId = response.getString("user_id");
                    if (accessToken != null) {
                        StringUtils.setAccessToken(accessToken);
                        StringUtils.setTeamName(teamName);
                        StringUtils.setUserId(userId);
                        SharedPreferenceStorage.setAccessCode(context, accessToken);
                        SharedPreferenceStorage.setUserId(context, userId);
                        SharedPreferenceStorage.setTeamName(context, teamName);
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
