package com.example.onlineshoppingstore.feedback.repo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.onlineshoppingstore.feedback.model.User;

import java.util.UUID;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Local.db";
    public static final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String uQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" + COLUMN_ID + " TEXT PRIMARY KEY, " + COLUMN_USERNAME + " TEXT UNIQUE, " + COLUMN_PASSWORD + " TEXT)";
        String fbQuery = "CREATE TABLE IF NOT EXISTS " + DBFeedback.TABLE_NAME + " (" + DBFeedback.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DBFeedback.COLUMN_AUTHOR_ID + " TEXT, " + DBFeedback.COLUMN_FEEDBACK + " TEXT, " + DBFeedback.COLUMN_OVERALL + " INTEGER, " + DBFeedback.COLUMN_DATE + " TEXT, " + DBFeedback.COLUMN_UPDATED_AT + " TEXT)";
        String srQuery = "CREATE TABLE IF NOT EXISTS " + DBServicesRating.TABLE_NAME + " (" + DBServicesRating.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + DBServicesRating.COLUMN_RATER_ID + " TEXT, " + DBServicesRating.COLUMN_PRICING + " INTEGER, " + DBServicesRating.COLUMN_DELIVERY + " INTEGER, " + DBServicesRating.COLUMN_QUALITY + " INTEGER, " + DBServicesRating.COLUMN_CUSTOMER_SERVICE + " INTEGER, " + DBServicesRating.COLUMN_PRODUCT_SELECTION + " INTEGER," + DBServicesRating.COLUMN_DATE + " TEXT)";

        // create views for feedback and services rating
        String fbViewQuery = "CREATE VIEW IF NOT EXISTS " + DBFeedback.VIEW_NAME + " AS SELECT " + DBFeedback.TABLE_NAME + ".*, " + TABLE_NAME + "." + COLUMN_USERNAME + " FROM " + DBFeedback.TABLE_NAME + " INNER JOIN " + TABLE_NAME + " ON " + DBFeedback.TABLE_NAME + "." + DBFeedback.COLUMN_AUTHOR_ID + " = " + TABLE_NAME + "." + COLUMN_ID;
        String srViewQuery = "CREATE VIEW IF NOT EXISTS " + DBServicesRating.VIEW_NAME + " AS SELECT " + DBServicesRating.TABLE_NAME + ".*, " + TABLE_NAME + "." + COLUMN_USERNAME + " FROM " + DBServicesRating.TABLE_NAME + " INNER JOIN " + TABLE_NAME + " ON " + DBServicesRating.TABLE_NAME + "." + DBServicesRating.COLUMN_RATER_ID + " = " + TABLE_NAME + "." + COLUMN_ID;

        db.execSQL(uQuery);
        db.execSQL(fbQuery);
        db.execSQL(srQuery);
        db.execSQL(fbViewQuery);
        db.execSQL(srViewQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBFeedback.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DBServicesRating.TABLE_NAME);
        onCreate(db);
    }

    public Boolean insertData(UUID id, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, id.toString());
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Boolean updatepassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PASSWORD, password);
        long result = db.update(TABLE_NAME, contentValues, COLUMN_USERNAME + " = ?", new String[]{username});
        return result != -1;

    }

    public Boolean checkusername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = ?", new String[]{username});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public Boolean checkusernameandPassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{username, password});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    @SuppressLint("Range")
    public User getUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?", new String[]{username, password});
        User user = new User();
        if (cursor.moveToFirst()) {
             String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
            String name = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
            cursor.close();
            user.setId(UUID.fromString(id));
            user.setUsername(name);
        }
        return user;
    }
}
