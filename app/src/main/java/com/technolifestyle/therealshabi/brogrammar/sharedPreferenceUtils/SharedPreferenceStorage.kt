package com.technolifestyle.therealshabi.brogrammar.sharedPreferenceUtils

import android.content.Context
import android.content.Context.MODE_PRIVATE

/**
 * Created by shahbaz on 25/2/17.
 */

object SharedPreferenceStorage {
    private const val MY_SHARED_PREFERENCES = "my_shared_preferences"
    private const val MAIN_ACTIVITY_FLAG = "main_activity_flag"
    private const val ACCESS_CODE = "access_code"
    private const val USER_ID = "user_id"
    private const val TEAM_NAME = "team_name"

    fun setSharedPreferenceMainActivityFlag(context: Context, flag: Boolean) {
        val sharedPreferences = context.getSharedPreferences(
                MY_SHARED_PREFERENCES, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(MAIN_ACTIVITY_FLAG, flag)
        editor.apply()
    }

    fun getSharedPreferenceMainActivity(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(
                MY_SHARED_PREFERENCES, MODE_PRIVATE)
        return sharedPreferences.getBoolean(MAIN_ACTIVITY_FLAG, false)
    }

    fun setAccessCode(context: Context, access_code: String) {
        val sharedPreferences = context.getSharedPreferences(
                MY_SHARED_PREFERENCES, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(ACCESS_CODE, "")
        editor.putString(ACCESS_CODE, access_code)
        editor.apply()
    }

    fun getAccessCode(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(
                MY_SHARED_PREFERENCES, MODE_PRIVATE)
        return sharedPreferences.getString(ACCESS_CODE, null)
    }

    fun setUserId(context: Context, user_id: String) {
        val sharedPreferences = context.getSharedPreferences(
                MY_SHARED_PREFERENCES, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(USER_ID, user_id)
        editor.apply()
    }

    fun getUserId(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(
                MY_SHARED_PREFERENCES, MODE_PRIVATE)
        return sharedPreferences.getString(USER_ID, null)
    }

    fun setTeamName(context: Context, teamName: String) {
        val sharedPreferences = context.getSharedPreferences(
                MY_SHARED_PREFERENCES, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(TEAM_NAME, teamName)
        editor.apply()
    }

    fun getTeamName(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(
                MY_SHARED_PREFERENCES, MODE_PRIVATE)
        return sharedPreferences.getString(TEAM_NAME, null)
    }
}
