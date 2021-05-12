package com.example.capstoneprojectfinalize.Model;

import android.content.ContentQueryMap;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import static com.example.capstoneprojectfinalize.Model.UserModelTable.ALL_USER_COLUMNS;
import static com.example.capstoneprojectfinalize.Model.UserModelTable.COLUMN_USER_ACCESS;
import static com.example.capstoneprojectfinalize.Model.UserModelTable.COLUMN_USER_EMAIL;
import static com.example.capstoneprojectfinalize.Model.UserModelTable.COLUMN_USER_ID;
import static com.example.capstoneprojectfinalize.Model.UserModelTable.COLUMN_USER_NAME;
import static com.example.capstoneprojectfinalize.Model.UserModelTable.USER_TABLE;

public class DataSourceUser {

    private Context mContext;
    private SQLiteDatabase mDatabase;
    SQLiteOpenHelper mDbHelper;
    private String[] allColumns = {COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_ACCESS, COLUMN_USER_EMAIL};



    public DataSourceUser(Context context) {
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


    public void createUserLogData(){
        deleteAllUsers();
        createEmployee();
        createCustomer();
        getAllUserDataToast();
        }

    public void deleteAllUsers() {
        mDatabase.delete(USER_TABLE, null, null);
    }

    public void createEmployee(){
        UserItem userItem = new UserItem();
        userItem.setUserName("ted");
        userItem.setUserAccess(0);
        userItem.setUserEmail("ted@ted.ted");
        ContentValues values = userItem.toUserValues();
        mDatabase.insert(USER_TABLE, null, values);
        mDatabase.close();
    }

    public void createCustomer() {
        open();
        UserItem userItem = new UserItem();
        userItem.setUserName("bear");
        userItem.setUserAccess(1);
        userItem.setUserEmail("bear@bear.bear");
        ContentValues values1 = userItem.toUserValues();
        mDatabase.insert(USER_TABLE, null, values1);
        mDatabase.close();
    }

    public void addNewCustomer(UserItem userItem) {
        open();
         ContentValues values = userItem.toUserValues();
         mDatabase.insert(USER_TABLE, null, values);
         mDatabase.close();
    }

    public void updateCustomer(UserItem userItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_ID, userItem.getUserId());
        contentValues.put(COLUMN_USER_NAME, userItem.getUserName());
        contentValues.put(COLUMN_USER_EMAIL, userItem.getUserEmail());
        mDatabase.update(USER_TABLE, contentValues, COLUMN_USER_ID + " = " + userItem.getUserId(), null);
        mDatabase.close();
    }

    public void updateCustomerName(UserItem userItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_ID, userItem.getUserId());
        contentValues.put(COLUMN_USER_NAME, userItem.getUserName());
        mDatabase.update(USER_TABLE, contentValues, COLUMN_USER_ID + " = " + userItem.getUserId(), null);
        mDatabase.close();
    }

    public String userDatabaseUpdateEmail(UserItem userItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USER_EMAIL,userItem.getUserEmail());
        mDatabase.update(USER_TABLE, contentValues, COLUMN_USER_ID + " = " +
                userItem.getUserId(), null);
        mDatabase.close();
        return userItem.getUserEmail();
    }

    public boolean testUniqueCustomer(String name) {
        open();
        Cursor cursor = mDatabase.query(USER_TABLE, allColumns, null,
                null, null, null, null);
        cursor.moveToFirst();
        while(cursor.isAfterLast()) {
            UserItem userItem = cursorToUser(cursor);
            if (userItem.getUserName().equals(name)) {
                cursor.close();
                mDatabase.close();
                return true;
            }
        }
        cursor.close();
        mDatabase.close();
        return false;
    }

    //displays user information when the user data is updated
    public void getAllUserDataToast(){
        open();
        Cursor cursor = mDatabase.query(USER_TABLE, allColumns, null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            UserItem userItem = cursorToUser(cursor);
            Toast.makeText(mContext, "The user name is ::" + userItem.getUserName(), Toast.LENGTH_LONG).show();
            cursor.moveToNext();
        }
        cursor.close();
        mDatabase.close();
    }

    public List<UserItem> getAllCustomerUserData(){
        open();
        List<UserItem> userItemList = new ArrayList<UserItem>();
        Cursor cursor = mDatabase.query(USER_TABLE, allColumns, null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            UserItem userItem = cursorToUser(cursor);
            if(userItem.getUserAccess()==1) {
                userItemList.add(userItem);
            }
                cursor.moveToNext();
        }
        cursor.close();
        mDatabase.close();
        return userItemList;
    }

    public UserItem userIdToName(Integer id){
        open();
        Cursor cursor = mDatabase.query(USER_TABLE, allColumns, null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            UserItem userItem = cursorToUser(cursor);
            if(userItem.getUserId().equals(id)){
                close();
                cursor.close();
                return userItem;
            }
            cursor.moveToNext();
        }
        close();
        cursor.close();
        UserItem noUserValid = new UserItem(-1, "none", -1, "no email");
        return noUserValid;
    }
    public UserItem getAuthorizedUserData(String userName) {
        open();
        Cursor cursor = mDatabase.query(USER_TABLE, allColumns, null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            UserItem userItem = cursorToUser(cursor);
            if(userItem.getUserName().equals(userName)){
                return userItem;
            }
            cursor.moveToNext();
        }
        cursor.close();
        mDatabase.close();
        UserItem noUserValid = new UserItem(-1, "none", -1, "no email");
        return noUserValid;
    }

    private UserItem cursorToUser(Cursor cursor) {
        UserItem userItem = new UserItem();
        userItem.setUserId(cursor.getInt(0));
        userItem.setUserName(cursor.getString(1));
        userItem.setUserAccess(cursor.getInt(2));
        userItem.setUserEmail(cursor.getString(3));
        //cursor.close();
        return userItem;
    }

    public long getUserItemsCount() {
        open();
        return DatabaseUtils.queryNumEntries(mDatabase, USER_TABLE);
    }
}
