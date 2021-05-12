package com.example.capstoneprojectfinalize.Model;

public class UserModelTable  {

    public static final String USER_TABLE = "user";
    public static final String COLUMN_USER_ID = "userId";
    public static final String COLUMN_USER_NAME = "userName";
    public static final String COLUMN_USER_ACCESS = "userAccess";
    public static final String COLUMN_USER_EMAIL = "userEmail";

    public static final String[] ALL_USER_COLUMNS = {COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_ACCESS, COLUMN_USER_EMAIL};

    public static final String SQL_CREATE_USERS =
            "CREATE TABLE " + USER_TABLE + "(" +
                    COLUMN_USER_ID + " integer PRIMARY KEY autoincrement," +
                    COLUMN_USER_NAME + " TEXT," +
                    COLUMN_USER_ACCESS + " integer," +
                    COLUMN_USER_EMAIL + " TEXT" + ");";

    public static final String SQL_USER_DELETE = "DROP TABLE" + USER_TABLE;
}
