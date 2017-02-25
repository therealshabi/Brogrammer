package com.technolifestyle.therealshabi.brogrammar.DataSynchronization;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.technolifestyle.therealshabi.brogrammar.Models.MessageModel;
import com.technolifestyle.therealshabi.brogrammar.Models.UserModel;

import java.util.ArrayList;

import static android.database.DatabaseUtils.sqlEscapeString;

/**
 * Created by shahbaz on 25/2/17.
 */

public class LocalDataSync extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SlackDatabase";
    private static final int VERSION = 1;

    private static final String USER_TABLE_NAME = "UserTable";
    private static final String USER_TABLE_USER_NAME = "UserName";
    private static final String USER_TABLE_USER_ID = "UserId";

    private static final String MESSAGE_TABLE_NAME = "MessageTable";
    private static final String MESSAGE_TABLE_USER_ID = "UserId";
    private static final String MESSAGE_TABLE_MESSAGE = "Message";
    private static final String MESSAGE_TABLE_TIME_STAMP = "MessageTimeStamp";

    public LocalDataSync(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String userAdd = "CREATE TABLE " + USER_TABLE_NAME + " (" +
                USER_TABLE_USER_ID + " VARCHAR PRIMARY KEY, " +
                USER_TABLE_USER_NAME + " VARCHAR " +
                ");";

        String messageAdd = "CREATE TABLE " + MESSAGE_TABLE_NAME + " (" +
                MESSAGE_TABLE_USER_ID + " VARCHAR , " +
                MESSAGE_TABLE_MESSAGE + " VARCHAR ," +
                MESSAGE_TABLE_TIME_STAMP + " VARCHAR ," + " PRIMARY KEY (" + MESSAGE_TABLE_USER_ID + "," + MESSAGE_TABLE_MESSAGE + "," + MESSAGE_TABLE_TIME_STAMP + ")" +
                ");";
        db.execSQL(userAdd);
        db.execSQL(messageAdd);

    }

    public void insertUserDetails(UserModel user) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_TABLE_USER_ID, user.getId());
        values.put(USER_TABLE_USER_NAME, user.getName());

        database.insert(USER_TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean getUser(String id) {
        SQLiteDatabase database = getReadableDatabase();
        if (id == null) {
            return true;
        }
        String sql = "Select * from " + USER_TABLE_NAME + " where " + USER_TABLE_USER_ID + "='" + id + "';";
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public void insertMessageDetails(MessageModel message) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MESSAGE_TABLE_USER_ID, message.getUserId());
        values.put(MESSAGE_TABLE_MESSAGE, message.getMessage().replaceAll("\"", "'"));
        values.put(MESSAGE_TABLE_TIME_STAMP, message.getTimeStamp());

        if (this.getMessage(message) != true)
        database.insert(MESSAGE_TABLE_NAME, null, values);
    }

    public boolean getMessage(MessageModel message) {
        SQLiteDatabase database = getReadableDatabase();
        String sql = "Select * from " + MESSAGE_TABLE_NAME + " where " + MESSAGE_TABLE_USER_ID + "=\"" + message.getUserId() + "\" AND " + MESSAGE_TABLE_MESSAGE + "=\"" + message.getMessage().replaceAll("\"", "'") + "\" AND " + MESSAGE_TABLE_TIME_STAMP + "=\"" + message.getTimeStamp() + "\";";
        ;
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }

    public ArrayList<MessageModel> getMessagesList() {
        ArrayList<MessageModel> messages = new ArrayList<>();

        String sql = "Select * from " + MESSAGE_TABLE_NAME + ";";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        while (cursor.moveToNext()) {
            MessageModel message = new MessageModel();
            message.setUserId(cursor.getString(cursor.getColumnIndex(MESSAGE_TABLE_USER_ID)));
            message.setMessage(cursor.getString(cursor.getColumnIndex(MESSAGE_TABLE_MESSAGE)));
            message.setTimeStamp(cursor.getString(cursor.getColumnIndex(MESSAGE_TABLE_TIME_STAMP)));
            messages.add(message);
        }
        cursor.close();

        return messages;
    }

    public UserModel getUserData(String id) {

        String sql = "Select * from " + USER_TABLE_NAME + " WHERE " + USER_TABLE_USER_ID + " = '" + id + "';";
        Cursor cursor = getReadableDatabase().rawQuery(sql, null);
        UserModel user = new UserModel();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            user.setId(cursor.getString(cursor.getColumnIndex(USER_TABLE_USER_ID)));
            user.setName(cursor.getString(cursor.getColumnIndex(USER_TABLE_USER_NAME)));
        }
        cursor.close();

        return user;
    }

    public void clearMessages() {
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("Delete from " + MESSAGE_TABLE_NAME + ";");
    }
}
