package com.technolifestyle.therealshabi.brogrammar.dataSynchronization

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import com.technolifestyle.therealshabi.brogrammar.models.MessageModel
import com.technolifestyle.therealshabi.brogrammar.models.UserModel

import java.util.ArrayList

/**
 * Created by shahbaz on 25/2/17.
 */

class LocalDataSync(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {

    val messagesList: ArrayList<MessageModel>
        get() {
            val messages = ArrayList<MessageModel>()

            val sql = "Select * from $MESSAGE_TABLE_NAME;"
            val cursor = readableDatabase.rawQuery(sql, null)
            while (cursor.moveToNext()) {
                val message = MessageModel()
                message.userId = cursor.getString(cursor.getColumnIndex(MESSAGE_TABLE_USER_ID))
                message.message = cursor.getString(cursor.getColumnIndex(MESSAGE_TABLE_MESSAGE))
                message.timeStamp = cursor.getString(cursor.getColumnIndex(MESSAGE_TABLE_TIME_STAMP))
                messages.add(message)
            }
            cursor.close()

            return messages
        }

    override fun onCreate(db: SQLiteDatabase) {

        val userAdd = "CREATE TABLE " + USER_TABLE_NAME + " (" +
                USER_TABLE_USER_ID + " VARCHAR PRIMARY KEY, " +
                USER_TABLE_USER_NAME + " VARCHAR " +
                ");"

        val messageAdd = "CREATE TABLE " + MESSAGE_TABLE_NAME + " (" +
                MESSAGE_TABLE_USER_ID + " VARCHAR , " +
                MESSAGE_TABLE_MESSAGE + " VARCHAR ," +
                MESSAGE_TABLE_TIME_STAMP + " VARCHAR ," + " PRIMARY KEY (" + MESSAGE_TABLE_USER_ID + "," + MESSAGE_TABLE_MESSAGE + "," + MESSAGE_TABLE_TIME_STAMP + ")" +
                ");"
        db.execSQL(userAdd)
        db.execSQL(messageAdd)

    }

    fun insertUserDetails(user: UserModel) {
        val database = writableDatabase
        val values = ContentValues()
        values.put(USER_TABLE_USER_ID, user.id)
        values.put(USER_TABLE_USER_NAME, user.name)

        database.insert(USER_TABLE_NAME, null, values)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }

    fun getUser(id: String?): Boolean {
        val database = readableDatabase
        if (id == null) {
            return true
        }
        val sql = "Select * from $USER_TABLE_NAME where $USER_TABLE_USER_ID='$id';"
        val cursor = database.rawQuery(sql, null)
        if (cursor.count > 0) {
            cursor.close()
            return true
        }
        cursor.close()
        return false
    }

    fun insertMessageDetails(message: MessageModel) {
        val database = writableDatabase
        val values = ContentValues()
        values.put(MESSAGE_TABLE_USER_ID, message.userId)
        values.put(MESSAGE_TABLE_MESSAGE, message.message!!.replace("\"".toRegex(), "'"))
        values.put(MESSAGE_TABLE_TIME_STAMP, message.timeStamp)

        if (!this.getMessage(message))
            database.insert(MESSAGE_TABLE_NAME, null, values)
    }

    fun getMessage(message: MessageModel): Boolean {
        val database = readableDatabase
        val sql = "Select * from " + MESSAGE_TABLE_NAME + " where " + MESSAGE_TABLE_USER_ID + "=\"" + message.userId + "\" AND " + MESSAGE_TABLE_MESSAGE + "=\"" + message.message!!.replace("\"".toRegex(), "'") + "\" AND " + MESSAGE_TABLE_TIME_STAMP + "=\"" + message.timeStamp + "\";"
        val cursor = database.rawQuery(sql, null)
        if (cursor.count > 0) {
            cursor.close()
            return true
        }
        cursor.close()
        return false
    }

    fun getUserData(id: String): UserModel {

        val sql = "Select * from $USER_TABLE_NAME WHERE $USER_TABLE_USER_ID = '$id';"
        val cursor = readableDatabase.rawQuery(sql, null)
        val user = UserModel()
        if (cursor.count > 0) {
            cursor.moveToFirst()
            user.id = cursor.getString(cursor.getColumnIndex(USER_TABLE_USER_ID))
            user.name = cursor.getString(cursor.getColumnIndex(USER_TABLE_USER_NAME))
        }
        cursor.close()

        return user
    }

    fun clearMessages() {
        val db = writableDatabase
        db.execSQL("Delete from $MESSAGE_TABLE_NAME;")
    }

    companion object {

        private val DATABASE_NAME = "SlackDatabase"
        private val VERSION = 1

        private val USER_TABLE_NAME = "UserTable"
        private val USER_TABLE_USER_NAME = "UserName"
        private val USER_TABLE_USER_ID = "UserId"

        private val MESSAGE_TABLE_NAME = "MessageTable"
        private val MESSAGE_TABLE_USER_ID = "UserId"
        private val MESSAGE_TABLE_MESSAGE = "Message"
        private val MESSAGE_TABLE_TIME_STAMP = "MessageTimeStamp"
    }
}
