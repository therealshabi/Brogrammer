package com.technolifestyle.therealshabi.brogrammar.StringUtility;

/**
 * Created by shahbaz on 24/2/17.
 */

public class StringUtils {
    public static String SLACK_CODE_PARAMETER;
    public static int STATUS_CODE;
    public static String CURRENT_URL;
    public static String ACCESS_TOKEN;
    public static String TEAM_NAME;
    public static String USER_ID;

    public static String getSlackCodeParameter() {
        return SLACK_CODE_PARAMETER;
    }

    public static void setSlackCodeParameter(String slackCodeParameter) {
        SLACK_CODE_PARAMETER = slackCodeParameter;
    }

    public static int getStatusCode() {
        return STATUS_CODE;
    }

    public static void setStatusCode(int statusCode) {
        StringUtils.STATUS_CODE = statusCode;
    }

    public static String getCurrentURL() {
        return CURRENT_URL;
    }

    public static void setCurrentURL(String currentURL) {
        StringUtils.CURRENT_URL = currentURL;
    }

    public static String getAccessToken() {
        return ACCESS_TOKEN;
    }

    public static void setAccessToken(String accessToken) {
        ACCESS_TOKEN = accessToken;
    }

    public static String getTeamName() {
        return TEAM_NAME;
    }

    public static void setTeamName(String teamName) {
        TEAM_NAME = teamName;
    }

    public static void setUserId(String userId) {
        USER_ID = userId;
    }
}
