package com.technolifestyle.therealshabi.brogrammar.SharedPreferenceUtils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by shahbaz on 25/2/17.
 */

public class SharedPreferenceStorage {
    public static final String MY_SHARED_PREFERENCES = "my_shared_preferences";
    public static final String MAIN_ACTIVITY_FLAG = "main_activity_flag";
    public static final String ACCESS_CODE = "access_code";
    public static final String USER_ID = "user_id";
    public static final String TEAM_NAME = "team_name";

    public static void setSharedPreferenceMainActivityFlag(Context context, boolean flag) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(MAIN_ACTIVITY_FLAG, flag);
        editor.commit();
    }

    public static boolean getSharedPreferenceMainActivity(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(MAIN_ACTIVITY_FLAG, false);
    }

    public static void setAccessCode(Context context, String access_code) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putString(ACCESS_CODE, access_code);
        editor.commit();
    }

    public static String getAccessCode(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, context.MODE_PRIVATE);
        return sharedPreferences.getString(ACCESS_CODE, null);
    }

    public static void setUserId(Context context, String user_id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, user_id);
        editor.commit();
    }

    public static String getUserId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_ID, null);
    }

    public static void setTeamName(Context context, String teamName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TEAM_NAME, teamName);
        editor.commit();
    }

    public static String getTeamName(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, context.MODE_PRIVATE);
        return sharedPreferences.getString(TEAM_NAME, null);
    }
}
