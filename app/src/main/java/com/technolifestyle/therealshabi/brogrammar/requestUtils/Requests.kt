package com.technolifestyle.therealshabi.brogrammar.requestUtils

import android.content.Context
import android.util.Log
import android.widget.Toast

import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.technolifestyle.therealshabi.brogrammar.dataSynchronization.LocalDataSync
import com.technolifestyle.therealshabi.brogrammar.models.MessageModel
import com.technolifestyle.therealshabi.brogrammar.models.UserModel
import com.technolifestyle.therealshabi.brogrammar.sharedPreferenceUtils.SharedPreferenceStorage
import com.technolifestyle.therealshabi.brogrammar.stringUtility.SlackClient
import com.technolifestyle.therealshabi.brogrammar.stringUtility.StringUtils

import org.json.JSONException

import android.widget.Toast.LENGTH_SHORT

/**
 * Created by shahbaz on 24/2/17.
 */

class Requests : SlackClient {

    private var USERS_LIST_URL = "https://slack.com/api/users.list?token="
    private var MESSAGE_HISTORY_URL = "https://slack.com/api/channels.history?token="

    fun getSlackCode(context: Context, GET_ACCESS_TOKEN_URL: String) {
        /*jsonObject.put("client_id", CLIENT_ID);
        jsonObject.put("client_secret", CLIENT_SECRET);*/
        val jsonObjectRequest = JsonObjectRequest(
                GET_ACCESS_TOKEN_URL, null, Response.Listener { response ->
            try {
                val accessToken = response.getString("access_token")
                val teamName = response.getString("team_name")
                val userId = response.getString("user_id")
                if (accessToken != null) {
                    StringUtils.accessToken = accessToken
                    StringUtils.teamName = teamName
                    StringUtils.setUserId(userId)
                    SharedPreferenceStorage.setAccessCode(context, accessToken)
                    SharedPreferenceStorage.setUserId(context, userId)
                    SharedPreferenceStorage.setTeamName(context, teamName)
                    Log.d("Access Token", accessToken)
                }
            } catch (e: JSONException) {
                Log.d("JSON EXCEPTION", e.toString())
                Log.d("Response", response.toString())
            }
        }, Response.ErrorListener { error -> Log.d("Error", error.toString()) })

        Volley.newRequestQueue(context).add(jsonObjectRequest)
        Log.d("Request", jsonObjectRequest.toString())

    }

    fun getUsersList(context: Context) {
        USERS_LIST_URL += SharedPreferenceStorage.getAccessCode(context)
        Log.d("URL", USERS_LIST_URL)

        val jsonObjectRequest = JsonObjectRequest(USERS_LIST_URL, null, Response.Listener { response ->
            try {
                val usersArray = response.getJSONArray("members")
                if (usersArray != null) {
                    for (i in 0 until usersArray.length()) {
                        val temp = usersArray.getJSONObject(i)
                        val user = UserModel()
                        if (temp.getString("id") != "U3WCGPZ71") {
                            user.id = temp.getString("id")
                            user.name = temp.getString("real_name")

                            //Local Database Insertion
                            val data = LocalDataSync(context)
                            if (data.getUser(user.id) != true)
                                data.insertUserDetails(user)
                        }
                    }
                } else {
                    Toast.makeText(context, "NULL ARRAY", LENGTH_SHORT).show()
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { Toast.makeText(context, "An Error has occurred", LENGTH_SHORT).show() })

        Volley.newRequestQueue(context).add(jsonObjectRequest)
    }

    fun getChannelMessages(context: Context) {
        MESSAGE_HISTORY_URL += SharedPreferenceStorage.getAccessCode(context) + "&channel=" + SlackClient.GENERAL_CHANNEL_ID

        val jsonObjectRequest = JsonObjectRequest(
                MESSAGE_HISTORY_URL, null, Response.Listener { response ->
            try {
                val messageArray = response.getJSONArray("messages")
                for (i in 0 until messageArray.length()) {
                    val temp = messageArray.getJSONObject(i)
                    val message = MessageModel()
                    message.userId = temp.getString("user")
                    var m = temp.getString("text")
                    if (m.matches("[<]{1}.*[>]{1}.*".toRegex())) {
                        val t = m.split(">".toRegex()).dropLastWhile({ it.isEmpty() }).toTypedArray()
                        var l = t[0].substring(1, t[0].length - 1)
                        l = l.substring(1, 9)
                        val (name) = LocalDataSync(context).getUserData(l)
                        m = name + t[1]
                    }
                    message.message = m
                    message.timeStamp = temp.getString("ts")

                    //Local Database Insertion
                    val data = LocalDataSync(context)
                    if (!data.getMessage(message))
                        data.insertMessageDetails(message)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }, Response.ErrorListener { Toast.makeText(context, "An Error has been Encountered", LENGTH_SHORT).show() })

        Volley.newRequestQueue(context).add(jsonObjectRequest)
    }

}
