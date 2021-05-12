package com.example.capstoneprojectfinalize.Model;

import android.content.ContentValues;

public class ProductModelTable {

    public static final String PRODUCT_TABLE = "product";
    public static final String COLUMN_PRODUCT_ID = "productId";
    public static final String COLUMN_PRODUCT_NAME = "productName";
    public static final String COLUMN_PRODUCT_DATE = "productDate";
    public static final String COLUMN_PRODUCT_TYPE = "productType";
    public static final String COLUMN_PRODUCT_USER_CREATED = "productUserCreated";
    public static final String COLUMN_PRODUCT_RESERVED = "productReserved";
    public static final String COLUMN_PRODUCT_USER_RESERVATION = "productUserReservation";

    public static final String[] ALL_PRODUCT_COLUMNS = {COLUMN_PRODUCT_ID, COLUMN_PRODUCT_NAME,
            COLUMN_PRODUCT_DATE, COLUMN_PRODUCT_TYPE, COLUMN_PRODUCT_USER_CREATED, COLUMN_PRODUCT_RESERVED,
            COLUMN_PRODUCT_USER_RESERVATION};

    public static final String SQL_CREATE_PRODUCTS =
            "CREATE TABLE " + PRODUCT_TABLE + "(" +
                    COLUMN_PRODUCT_ID + " integer PRIMARY KEY autoincrement," +
                    COLUMN_PRODUCT_NAME + " TEXT," +
                    COLUMN_PRODUCT_DATE + " TEXT," +
                    COLUMN_PRODUCT_TYPE + " integer," +
                    COLUMN_PRODUCT_USER_CREATED + " TEXT," +
                    COLUMN_PRODUCT_RESERVED + " integer," +
                    COLUMN_PRODUCT_USER_RESERVATION + " integer" + ");";

    public static final String SQL_PRODUCT_DELETE = "DROP TABLE" + PRODUCT_TABLE;
}
