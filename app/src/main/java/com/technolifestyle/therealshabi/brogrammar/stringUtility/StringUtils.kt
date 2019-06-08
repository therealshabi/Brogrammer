package com.technolifestyle.therealshabi.brogrammar.stringUtility

/**
 * Created by shahbaz on 24/2/17.
 */

object StringUtils {
    var slackCodeParameter: String? = null
    var statusCode: Int = 0
    var currentURL: String? = null
    var accessToken: String? = null
    var teamName: String? = null
    private var USER_ID: String? = null

    fun setUserId(userId: String) {
        USER_ID = userId
    }
}
