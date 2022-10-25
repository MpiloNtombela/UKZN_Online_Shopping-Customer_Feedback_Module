package com.example.onlineshoppingstore.feedback.repo;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.onlineshoppingstore.feedback.model.ServicesRating;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBServicesRating extends DBHelper {
    public static String TABLE_NAME = "services_rating";
    public static String COLUMN_ID = "id";
    public static String COLUMN_RATER_ID = "rater_id";
    public static String COLUMN_PRICING = "pricing";
    public static String COLUMN_DELIVERY = "delivery";
    public static String COLUMN_QUALITY = "quality";
    public static String COLUMN_CUSTOMER_SERVICE = "customer_service";
    public static String COLUMN_PRODUCT_SELECTION = "product_selection";
    public static String COLUMN_DATE = "date";

    public static String VIEW_NAME = "vw_services_rating";

    public static DBServicesRating instance;

    public static DBServicesRating getInstance(Context context) {
        if (instance == null) {
            instance = new DBServicesRating(context);
        }
        return instance;
    }

    public DBServicesRating(Context context) {
        super(context);
    }

    // insert services_rating
    public Boolean addServicesRating(ServicesRating servicesRating) {
        OffsetDateTime date = OffsetDateTime.now();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RATER_ID, servicesRating.getRaterId());
        contentValues.put(COLUMN_PRICING, servicesRating.getPricing());
        contentValues.put(COLUMN_DELIVERY, servicesRating.getDelivery());
        contentValues.put(COLUMN_QUALITY, servicesRating.getQuality());
        contentValues.put(COLUMN_CUSTOMER_SERVICE, servicesRating.getCustomerService());
        contentValues.put(COLUMN_PRODUCT_SELECTION, servicesRating.getProductSelection());
        contentValues.put(COLUMN_DATE, date.toString());
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // get services_rating by rate_id
    @SuppressLint("Range")
    public ServicesRating getServicesRatingByRaterId(String raterId) {
        SQLiteDatabase db = this.getReadableDatabase();
        ServicesRating servicesRating = new ServicesRating();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_RATER_ID + " = ?", new String[]{raterId});
        if (cursor.moveToFirst()) {
            servicesRating.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            servicesRating.setRaterId(cursor.getString(cursor.getColumnIndex(COLUMN_RATER_ID)));
            servicesRating.setPricing(cursor.getInt(cursor.getColumnIndex(COLUMN_PRICING)));
            servicesRating.setDelivery(cursor.getInt(cursor.getColumnIndex(COLUMN_DELIVERY)));
            servicesRating.setQuality(cursor.getInt(cursor.getColumnIndex(COLUMN_QUALITY)));
            servicesRating.setCustomerService(cursor.getInt(cursor.getColumnIndex(COLUMN_CUSTOMER_SERVICE)));
            servicesRating.setProductSelection(cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_SELECTION)));
        }
        cursor.close();
        return servicesRating;
    }

    // get services_rating by services_rating_id
    @SuppressLint("Range")
    public ServicesRating getServicesRating(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ServicesRating servicesRating = new ServicesRating();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            servicesRating.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            servicesRating.setRaterId(cursor.getString(cursor.getColumnIndex(COLUMN_RATER_ID)));
            servicesRating.setPricing(cursor.getInt(cursor.getColumnIndex(COLUMN_PRICING)));
            servicesRating.setDelivery(cursor.getInt(cursor.getColumnIndex(COLUMN_DELIVERY)));
            servicesRating.setQuality(cursor.getInt(cursor.getColumnIndex(COLUMN_QUALITY)));
            servicesRating.setCustomerService(cursor.getInt(cursor.getColumnIndex(COLUMN_CUSTOMER_SERVICE)));
            servicesRating.setProductSelection(cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_SELECTION)));
        }
        cursor.close();
        return servicesRating;
    }

    // update services_rating
    public Boolean updateServicesRating(ServicesRating servicesRating) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RATER_ID, servicesRating.getRaterId());
        contentValues.put(COLUMN_PRICING, servicesRating.getPricing());
        contentValues.put(COLUMN_DELIVERY, servicesRating.getDelivery());
        contentValues.put(COLUMN_QUALITY, servicesRating.getQuality());
        contentValues.put(COLUMN_CUSTOMER_SERVICE, servicesRating.getCustomerService());
        contentValues.put(COLUMN_PRODUCT_SELECTION, servicesRating.getProductSelection());
        long result = db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(servicesRating.getId())});
        return result != -1;
    }

    // delete services_rating
    public Boolean deleteServicesRating(ServicesRating servicesRating) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(servicesRating.getId())});
        return result != -1;
    }

    // get all services_rating
    @SuppressLint("Range")
    public List<ServicesRating> getAllServicesRating() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ServicesRating> servicesRatingList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                ServicesRating servicesRating = new ServicesRating();
                servicesRating.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                servicesRating.setRaterId(cursor.getString(cursor.getColumnIndex(COLUMN_RATER_ID)));
                servicesRating.setPricing(cursor.getInt(cursor.getColumnIndex(COLUMN_PRICING)));
                servicesRating.setDelivery(cursor.getInt(cursor.getColumnIndex(COLUMN_DELIVERY)));
                servicesRating.setQuality(cursor.getInt(cursor.getColumnIndex(COLUMN_QUALITY)));
                servicesRating.setCustomerService(cursor.getInt(cursor.getColumnIndex(COLUMN_CUSTOMER_SERVICE)));
                servicesRating.setProductSelection(cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_SELECTION)));
                servicesRatingList.add(servicesRating);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return servicesRatingList;
    }

    // get all services_rating by rater_id
    @SuppressLint("Range")
    public List<ServicesRating> getAllServicesRatingByRaterId(String raterId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<ServicesRating> servicesRatingList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_RATER_ID + " = ?", new String[]{raterId});
        if (cursor.moveToFirst()) {
            do {
                ServicesRating servicesRating = new ServicesRating();
                servicesRating.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                servicesRating.setRaterId(cursor.getString(cursor.getColumnIndex(COLUMN_RATER_ID)));
                servicesRating.setPricing(cursor.getInt(cursor.getColumnIndex(COLUMN_PRICING)));
                servicesRating.setDelivery(cursor.getInt(cursor.getColumnIndex(COLUMN_DELIVERY)));
                servicesRating.setQuality(cursor.getInt(cursor.getColumnIndex(COLUMN_QUALITY)));
                servicesRating.setCustomerService(cursor.getInt(cursor.getColumnIndex(COLUMN_CUSTOMER_SERVICE)));
                servicesRating.setProductSelection(cursor.getInt(cursor.getColumnIndex(COLUMN_PRODUCT_SELECTION)));
                servicesRatingList.add(servicesRating);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return servicesRatingList;
    }

    // get average services_rating by field (PRICING, DELIVERY, QUALITY, CUSTOMER_SERVICE, PRODUCT_SELECTION)
    @SuppressLint("Range")
    public double getAverageServicesRatingByField(String field) {
        SQLiteDatabase db = this.getReadableDatabase();
        double average = 0;
        Cursor cursor = db.rawQuery("SELECT AVG(" + field + ") FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            average = cursor.getDouble(0);
        }
        cursor.close();
        // round to 1 decimal place
        return Math.round(average * 10.0) / 10.0;
    }

    // check if rater has rated. Limit query to 1
    public boolean hasRated(String raterId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_RATER_ID + " = ? LIMIT 1", new String[]{raterId});
        boolean isRaterRated = cursor.moveToFirst();
        cursor.close();
        return isRaterRated;
    }

    public static List<String> getRatingFields() {
        List<String> ratingFields = new ArrayList<>();
        ratingFields.add(COLUMN_PRICING);
        ratingFields.add(COLUMN_DELIVERY);
        ratingFields.add(COLUMN_QUALITY);
        ratingFields.add(COLUMN_CUSTOMER_SERVICE);
        ratingFields.add(COLUMN_PRODUCT_SELECTION);
        return ratingFields;
    }

}
