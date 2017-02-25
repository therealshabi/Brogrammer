package com.technolifestyle.therealshabi.brogrammar.RequestUtils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.technolifestyle.therealshabi.brogrammar.Models.MessageModel;
import com.technolifestyle.therealshabi.brogrammar.Models.UserModel;
import com.technolifestyle.therealshabi.brogrammar.SharedPreferenceUtils.SharedPreferenceStorage;
import com.technolifestyle.therealshabi.brogrammar.StringUtility.Slack_Client;
import com.technolifestyle.therealshabi.brogrammar.StringUtility.StringUtils;
import com.technolifestyle.therealshabi.brogrammar.DataSynchronization.LocalDataSync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by shahbaz on 24/2/17.
 */

public class Requests implements Slack_Client {

    private String USERS_LIST_URL = "https://slack.com/api/users.list?token=";
    private String MESSAGE_HISTORY_URL = "https://slack.com/api/channels.history?token=";

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

    public void getUsersList(final Context context) {
        USERS_LIST_URL += SharedPreferenceStorage.getAccessCode(context);
        Log.d("URL", USERS_LIST_URL);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, USERS_LIST_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray usersArray = response.getJSONArray("members");
                    if (usersArray != null) {
                        for (int i = 0; i < usersArray.length(); i++) {
                            JSONObject temp = usersArray.getJSONObject(i);
                            UserModel user = new UserModel();
                            if (!temp.getString("id").equals("U3WCGPZ71")) {
                                user.setId(temp.getString("id"));
                                user.setName(temp.getString("real_name"));

                                //Local Database Insertion
                                LocalDataSync data = new LocalDataSync(context);
                                if (data.getUser(user.getId()) != true)
                                    data.insertUserDetails(user);
                            }
                        }
                    } else {
                        Toast.makeText(context, "NULL ARRAY", LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "An Error has occurred", LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

    public void getChannelMessages(final Context context) {
        MESSAGE_HISTORY_URL += SharedPreferenceStorage.getAccessCode(context) + "&channel=" + GENERAL_CHANNEL_ID;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, MESSAGE_HISTORY_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray messageArray = response.getJSONArray("messages");
                    for (int i = 0; i < messageArray.length(); i++) {
                        JSONObject temp = messageArray.getJSONObject(i);
                        MessageModel message = new MessageModel();
                        message.setUserId(temp.getString("user"));
                        String m = temp.getString("text");
                        if (m.matches("[<]{1}.*[>]{1}.*")) {
                            String t[] = m.split(">");
                            String l = t[0].substring(1, t[0].length() - 1);
                            l = l.substring(1, 9);
                            UserModel model = new LocalDataSync(context).getUserData(l);
                            m = model.getName() + t[1];
                        }
                        message.setMessage(m);
                        message.setTimeStamp(temp.getString("ts"));

                        //Local Database Insertion
                        LocalDataSync data = new LocalDataSync(context);
                        if (data.getMessage(message) != true)
                            data.insertMessageDetails(message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "An Error has been Encountered", LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(context).add(jsonObjectRequest);
    }

}
