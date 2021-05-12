package com.example.capstoneprojectfinalize.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

import static com.example.capstoneprojectfinalize.Model.ProductModelTable.COLUMN_PRODUCT_DATE;
import static com.example.capstoneprojectfinalize.Model.ProductModelTable.COLUMN_PRODUCT_ID;
import static com.example.capstoneprojectfinalize.Model.ProductModelTable.COLUMN_PRODUCT_NAME;
import static com.example.capstoneprojectfinalize.Model.ProductModelTable.COLUMN_PRODUCT_RESERVED;
import static com.example.capstoneprojectfinalize.Model.ProductModelTable.COLUMN_PRODUCT_TYPE;
import static com.example.capstoneprojectfinalize.Model.ProductModelTable.COLUMN_PRODUCT_USER_CREATED;
import static com.example.capstoneprojectfinalize.Model.ProductModelTable.COLUMN_PRODUCT_USER_RESERVATION;
import static com.example.capstoneprojectfinalize.Model.ProductModelTable.PRODUCT_TABLE;

public class DataSourceProduct {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    SQLiteOpenHelper mDbHelper;
    private String[] allColumns = {COLUMN_PRODUCT_ID, COLUMN_PRODUCT_NAME, COLUMN_PRODUCT_DATE,
            COLUMN_PRODUCT_TYPE, COLUMN_PRODUCT_USER_CREATED, COLUMN_PRODUCT_RESERVED,
            COLUMN_PRODUCT_USER_RESERVATION};

    public DataSourceProduct(Context context) {
        this.mContext = context;
        mDbHelper = new DataBaseHelper(mContext);
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void open() {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public void createProduct(ProductItem productItem) {
        ContentValues values = productItem.toValues();
        open();
        mDatabase.insert(PRODUCT_TABLE, null, values);
        close();
    }

    public void productDatabaseUpdateNameType(ProductItem productItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUCT_NAME, productItem.getProductName());
        contentValues.put(COLUMN_PRODUCT_TYPE, productItem.getProductType());
        contentValues.put(COLUMN_PRODUCT_RESERVED, productItem.getProductReversed());
        contentValues.put(COLUMN_PRODUCT_USER_RESERVATION, productItem.getProductUserReservation());
        mDatabase.update(PRODUCT_TABLE, contentValues, COLUMN_PRODUCT_ID + " = " +
                productItem.getProductId(), null);
        close();
    }

    public void reserveKayak(ProductItem productItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PRODUCT_ID,productItem.getProductId());
        contentValues.put(COLUMN_PRODUCT_RESERVED,productItem.getProductReversed());
        contentValues.put(COLUMN_PRODUCT_USER_RESERVATION,productItem.getProductUserReservation());
        mDatabase.update(PRODUCT_TABLE, contentValues, COLUMN_PRODUCT_ID + " = " + productItem.getProductId().toString(),null);
        close();
    }

    public void deleteProduct(Integer productId){
        mDatabase.delete(PRODUCT_TABLE, COLUMN_PRODUCT_ID + " = " + productId, null);
        close();
    }


    public List<ProductItem> getAllProducts() {
        open();
        List<ProductItem> product = new ArrayList<ProductItem>();
        Cursor cursor = mDatabase.query(PRODUCT_TABLE, allColumns, null,
                null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ProductItem productModelTable = cursorToProduct(cursor);
            product.add(productModelTable);
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return product;
    }

    public List<ProductItem> getAllLocalReservedProducts(Integer userId) {
        List<ProductItem> product = new ArrayList<ProductItem>();
        Cursor cursor = mDatabase.query(PRODUCT_TABLE, allColumns, null,
                null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ProductItem productItem = cursorToProduct(cursor);

            if(productItem.getProductUserReservation() == userId) {
                if(productItem.getProductReversed() == 1) {
                    product.add(productItem);
                }
            }
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return product;
    }

    public List<ProductItem> getAllCheckedOutProducts() {
        List<ProductItem> product = new ArrayList<ProductItem>();
        Cursor cursor = mDatabase.query(PRODUCT_TABLE, allColumns, null, null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ProductItem productItem = cursorToProduct(cursor);
            if(productItem.getProductReversed() == 2) {
                product.add(productItem);
            }
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return product;
    }

    public List<ProductItem> getAllReservedProducts() {
        List<ProductItem> product = new ArrayList<ProductItem>();
        Cursor cursor = mDatabase.query(PRODUCT_TABLE, allColumns, null, null,
                null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ProductItem productItem = cursorToProduct(cursor);
            if(productItem.getProductReversed() == 1) {
                product.add(productItem);
            }
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return product;
    }

    public List<ProductItem> getAllAvailableProducts() {
        List<ProductItem> product = new ArrayList<ProductItem>();
        Cursor cursor = mDatabase.query(PRODUCT_TABLE, allColumns, null, null,
                null, null , null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ProductItem productItem = cursorToProduct(cursor);
            if(productItem.getProductReversed() == 0) {
                product.add(productItem);
            }
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return product;
    }

    public List<ProductItem> getAllSitOnProducts(Integer userId) {
        List<ProductItem> product = new ArrayList<ProductItem>();
        Cursor cursor = mDatabase.query(PRODUCT_TABLE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ProductItem productItem = cursorToProduct(cursor);
            if (productItem.getProductReversed() == 0){
                if (productItem.getProductType() == 1) {
                    product.add(productItem);
                }
            }
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return product;
    }

    public List<ProductItem> getAllSitInProducts(Integer userId) {
        List<ProductItem> product = new ArrayList<ProductItem>();
        Cursor cursor = mDatabase.query(PRODUCT_TABLE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            ProductItem productItem = cursorToProduct(cursor);
            if (productItem.getProductReversed() == 0){
                if (productItem.getProductType() == 0) {
                    product.add(productItem);
                }
            }
            cursor.moveToNext();
        }
        cursor.close();
        close();
        return product;
    }

    private ProductItem cursorToProduct(Cursor cursor) {
        ProductItem productItem = new ProductItem();
        productItem.setProductId(cursor.getInt(0));
        productItem.setProductName(cursor.getString(1));
        productItem.setProductDate(cursor.getString(2));
        productItem.setProductType(cursor.getInt(3));
        productItem.setProductCreatedBy(cursor.getString(4));
        productItem.setProductReversed(cursor.getInt(5));
        productItem.setProductUserReservation(cursor.getInt(6));
        return productItem;
    }
}
