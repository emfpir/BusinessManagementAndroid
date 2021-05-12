package com.example.capstoneprojectfinalize.Model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import static com.example.capstoneprojectfinalize.Model.UserModelTable.COLUMN_USER_ACCESS;
import static com.example.capstoneprojectfinalize.Model.UserModelTable.COLUMN_USER_EMAIL;
import static com.example.capstoneprojectfinalize.Model.UserModelTable.COLUMN_USER_ID;
import static com.example.capstoneprojectfinalize.Model.UserModelTable.COLUMN_USER_NAME;

public class UserItem implements Parcelable {

    private Integer userId;
    private String userName;
    private Integer userAccess;
    private String userEmail;


    //constructors
    public UserItem() {  }

    public UserItem(Integer userId, String userName, Integer userAccess, String userEmail) {
        this.userId = userId;
        this.userName = userName;
        this.userAccess = userAccess;
        this.userEmail = userEmail;
    }



   //getter and settings
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAccess() {
        return userAccess;
    }

    public void setUserAccess(Integer userAccess) {
        this.userAccess = userAccess;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public ContentValues toUserValues() {
        ContentValues values = new ContentValues (4);

        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_USER_NAME, userName);
        values.put(COLUMN_USER_ACCESS, userAccess);
        values.put(COLUMN_USER_EMAIL, userEmail);

        return values;
    }

    @Override
    public String toString() {
        return userId + " " + userName + " " + userAccess + " " + userEmail;
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.userId);
        dest.writeString(this.userName);
        dest.writeInt(this.userAccess);
        dest.writeString(this.userEmail);
    }

    protected UserItem(Parcel in) {
        this.userId = in.readInt();
        this.userName = in.readString();
        this.userAccess = in.readInt();
        this.userEmail = in.readString();
    }

    public static final Creator<UserItem> CREATOR = new Creator<UserItem>() {
        @Override
        public UserItem createFromParcel(Parcel source) {
            return new UserItem(source);
        }

        @Override
        public UserItem[] newArray(int size) {
            return new UserItem[size];
        }
    };
}
