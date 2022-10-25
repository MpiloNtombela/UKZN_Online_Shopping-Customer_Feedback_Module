package com.example.onlineshoppingstore.feedback.repo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.onlineshoppingstore.feedback.model.Feedback;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBFeedback extends DBHelper {
    public static final String TABLE_NAME = "feedback";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_AUTHOR_ID = "author_id";
    public static final String COLUMN_FEEDBACK = "feedback";
    public static final String COLUMN_OVERALL = "overall";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_UPDATED_AT = "updated_at";
    public static final String VIEW_NAME = "vw_feedback";

    private static DBFeedback instance;
    private DBFeedback(Context context) {
        super(context);
    }

    public static DBFeedback getInstance(Context context) {
        if (instance == null) {
            instance = new DBFeedback(context);
        }
        return instance;
    }

    // insert feedback
    public Boolean addFeedback(Feedback feedback) {
        OffsetDateTime date = OffsetDateTime.now();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_AUTHOR_ID, feedback.getAuthorId());
        contentValues.put(COLUMN_FEEDBACK, feedback.getFeedback());
        contentValues.put(COLUMN_OVERALL, feedback.getOverall());
        contentValues.put(COLUMN_DATE, date.toString());
        contentValues.put(COLUMN_UPDATED_AT, date.toString());
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // update feedback
    public Boolean updateFeedback(Feedback feedback) {
        OffsetDateTime date = OffsetDateTime.now();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_AUTHOR_ID, feedback.getAuthorId());
        contentValues.put(COLUMN_FEEDBACK, feedback.getFeedback());
        contentValues.put(COLUMN_OVERALL, feedback.getOverall());
        contentValues.put(COLUMN_UPDATED_AT, date.toString());
        long result = db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(feedback.getId())});
        return result != -1;
    }

    // delete feedback
    public Boolean deleteFeedback(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("feedback", "id = ?", new String[]{String.valueOf(id)});
        return result != -1;
    }

    // get feedback by id
    @SuppressLint("Range")
    public Feedback getFeedback(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // use view
        Cursor cursor = db.rawQuery("SELECT * FROM " + VIEW_NAME + " WHERE " + COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            Feedback feedback = new Feedback();
            feedback.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            feedback.setAuthorId(cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_ID)));
            feedback.setAuthorName(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_USERNAME)));
            feedback.setFeedback(cursor.getString(cursor.getColumnIndex(COLUMN_FEEDBACK)));
            feedback.setOverall(cursor.getInt(cursor.getColumnIndex(COLUMN_OVERALL)));
            feedback.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
            feedback.setUpdatedAt(cursor.getString(cursor.getColumnIndex(COLUMN_UPDATED_AT)));
            cursor.close();
            return feedback;
        } else {
            cursor.close();
            return null;
        }
    }

    // get feedback by author id
    @SuppressLint("Range")
    public Feedback getFeedbackByAuthorId(String authorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Feedback feedback = new Feedback();
        // cursor to get the feedback using COLUMN_AUTHOR_ID
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_AUTHOR_ID + " = ?", new String[]{authorId});
        if (cursor.moveToFirst()) {
            feedback.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            feedback.setAuthorId(cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_ID)));
            feedback.setFeedback(cursor.getString(cursor.getColumnIndex(COLUMN_FEEDBACK)));
            feedback.setOverall(cursor.getInt(cursor.getColumnIndex(COLUMN_OVERALL)));
            feedback.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
            feedback.setUpdatedAt(cursor.getString(cursor.getColumnIndex(COLUMN_UPDATED_AT)));
        }
        cursor.close();
        return feedback;
    }

    // get all feedback
    @SuppressLint("Range")
    public List<Feedback> getAllFeedback() {
        SQLiteDatabase db = this.getWritableDatabase();
        // use view order by date desc
        Cursor cursor = db.rawQuery("SELECT * FROM " + VIEW_NAME + " ORDER BY " + COLUMN_DATE + " DESC", null);
        List<Feedback> feedbackList = new ArrayList<>();
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            feedbackList = new ArrayList<>();
            do {
                Feedback feedback = new Feedback();
                feedback.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                feedback.setAuthorId(cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_ID)));
                feedback.setAuthorName(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_USERNAME)));
                feedback.setFeedback(cursor.getString(cursor.getColumnIndex(COLUMN_FEEDBACK)));
                feedback.setOverall(cursor.getInt(cursor.getColumnIndex(COLUMN_OVERALL)));
                feedback.setDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                feedback.setUpdatedAt(cursor.getString(cursor.getColumnIndex(COLUMN_UPDATED_AT)));
                feedbackList.add(feedback);
            } while (cursor.moveToNext());
            // close cursor and return feedback list
        }
        cursor.close();
        return feedbackList;
    }


    // special operations
    // check if author has already given feedback
    public Boolean hasGivenFeedback(String authorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        // check if record exists using EXISTS keyword and LIMIT 1
        Cursor cursor = db.rawQuery("SELECT EXISTS(SELECT 1 FROM " + TABLE_NAME + " WHERE " + COLUMN_AUTHOR_ID + " = ? LIMIT 1)", new String[]{authorId});
        cursor.moveToFirst();
        // return true if record exists
        int result = cursor.getInt(0);
        cursor.close();
        return result == 1;
    }

    // get average overall
    public Double getAverageOverall() {
        SQLiteDatabase db = this.getWritableDatabase();
        // get average overall using AVG function
        Cursor cursor = db.rawQuery("SELECT AVG(" + COLUMN_OVERALL + ") FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        // return average overall
        double result = cursor.getDouble(0);
        cursor.close();
        return result;
    }

    // get feedback count
    public int getFeedbackCount() {
        SQLiteDatabase db = this.getWritableDatabase();
        // get feedback count using COUNT function
        Cursor cursor = db.rawQuery("SELECT COUNT(" + COLUMN_ID + ") FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        // return feedback count
        int result = cursor.getInt(0);
        cursor.close();
        return result;
    }

    // get feedback count by overall
    public int getFeedbackCountByOverall(int overall) {
        SQLiteDatabase db = this.getWritableDatabase();
        // get feedback count by overall using COUNT function
        Cursor cursor = db.rawQuery("SELECT COUNT(" + COLUMN_ID + ") FROM " + TABLE_NAME + " WHERE " + COLUMN_OVERALL + " = ?", new String[]{String.valueOf(overall)});
        cursor.moveToFirst();
        // return feedback count
        int result = cursor.getInt(0);
        cursor.close();
        return result;
    }
}
