package com.technolifestyle.therealshabi.brogrammar.Models;

/**
 * Created by shahbaz on 25/2/17.
 */

public class MessageModel {
    String message;
    String userId;
    String timeStamp;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
