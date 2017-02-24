package com.technolifestyle.therealshabi.brogrammar.RequestUtils;

import java.util.Map;

/**
 * Created by shahbaz on 24/2/17.
 */

public class StringUtils {
    public static String SLACK_CODE_PARAMETER;
    public static int statusCode;
    public static String currentURL="";
    public static String ACCESS_TOKEN;

    public static void setSlackCodeParameter(String slackCodeParameter) {
        SLACK_CODE_PARAMETER = slackCodeParameter;
    }

    public static String getSlackCodeParameter() {
        return SLACK_CODE_PARAMETER;
    }

    public static int getStatusCode() {
        return statusCode;
    }

    public static void setStatusCode(int statusCode) {
        StringUtils.statusCode = statusCode;
    }

    public static void setCurrentURL(String currentURL) {
        StringUtils.currentURL = currentURL;
    }

    public static String getCurrentURL() {
        return currentURL;
    }

    public static String getAccessToken() {
        return ACCESS_TOKEN;
    }

    public static void setAccessToken(String accessToken) {
        ACCESS_TOKEN = accessToken;
    }
}
