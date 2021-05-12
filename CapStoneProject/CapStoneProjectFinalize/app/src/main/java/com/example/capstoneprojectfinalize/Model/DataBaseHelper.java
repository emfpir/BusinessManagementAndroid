package com.example.capstoneprojectfinalize.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DB_FILE_NAME = "cdKayak.db";
    public static final int DB_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DB_FILE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserModelTable.SQL_CREATE_USERS);
        db.execSQL(ProductModelTable.SQL_CREATE_PRODUCTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserModelTable.SQL_USER_DELETE);
        db.execSQL(ProductModelTable.SQL_PRODUCT_DELETE);
        onCreate(db);
    }
}

