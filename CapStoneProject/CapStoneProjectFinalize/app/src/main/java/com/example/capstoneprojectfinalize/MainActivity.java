package com.example.capstoneprojectfinalize;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstoneprojectfinalize.Adapter.ProductCustomerActivityAdapter;
import com.example.capstoneprojectfinalize.Model.DataBaseHelper;
import com.example.capstoneprojectfinalize.Model.DataSourceProduct;
import com.example.capstoneprojectfinalize.Model.DataSourceUser;
import com.example.capstoneprojectfinalize.Model.ProductItem;
import com.example.capstoneprojectfinalize.Model.UserItem;
import com.example.capstoneprojectfinalize.Model.UserModelTable;

import java.util.List;

import static com.example.capstoneprojectfinalize.Model.UserModelTable.SQL_USER_DELETE;
import static com.example.capstoneprojectfinalize.Model.UserModelTable.USER_TABLE;

public class MainActivity extends AppCompatActivity  {

    //references to buttons and other controls on the layout
    Button submitButton;
    EditText userNameText, userEmailText;

    DataSourceUser dataSourceUser;
//    DataSourceProduct dataSourceProduct;
    SQLiteDatabase database;
//    List<ProductItem> mProductItemList;
//    List<UserItem> mUserItemList;
//
//    private UserItem userItemKey;
//    private ProductItem productItem;
//    private Context mainContext;
//    private DataBaseHelper mDataBaseHelper;
    public static final String USER_IDENTITY = "user_id_key";
    public static final String USER_NAME = "user_name_key";
    public static final String USER_ACCESS = "user_access_key";
    public static final String USER_EMAIL = "user_email_key";

    @Override
    //remove 'Final' behind Bundle. no, clue what the purpose is.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteOpenHelper dbHelper = new DataBaseHelper(this);
        database = dbHelper.getWritableDatabase();
        database.close();


        dataSourceUser =new DataSourceUser(this);
        dataSourceUser.open();
        long longNumberUser = dataSourceUser.getUserItemsCount();
        int databaseUserCount = (int) longNumberUser;
        if(databaseUserCount <= 1) {
            dataSourceUser.createUserLogData();
        }


        submitButton = findViewById(R.id.SubmitButton);
        userNameText = findViewById(R.id.editTextTextUserName);
        userEmailText = findViewById(R.id.editTextTextEmailAddress);

        userNameText.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence charSequence, int start, int end, Spanned dest, int etStart, int etEnd) {
                        if (charSequence.equals("")) {
                            return charSequence;
                        }
                        if (charSequence.toString().matches("[a-zA-Z]+")) {
                            return charSequence;
                        }
                        userNameText.setText("");
                        Toast.makeText(MainActivity.this, "The username input must only be a-z or A-Z with no spaces.", Toast.LENGTH_LONG).show();

                        return "";
                    }
                }
        });

        submitButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                submitButton(userNameText.getText().toString());
            }
        });
    }

    public void submitButton(String userName){
//        String theWorstThing = userNameText.getText().toString();
        UserItem userTableModelActive = checkUserLogIn(userName);

        if(userTableModelActive.getUserAccess()==-1){
        }
        else {
            String email = userEmailText.getText().toString();
            if(isEmailValid(email)){
                DataSourceUser dataSourceUser = new DataSourceUser(MainActivity.this);
                dataSourceUser.open();
                userTableModelActive.setUserEmail(email);
                dataSourceUser.userDatabaseUpdateEmail(userTableModelActive);
            }
            if(userTableModelActive.getUserAccess() == 0){
                Intent intent = new Intent(MainActivity.this, EmployActivity.class);

                intent.putExtra(USER_IDENTITY,userTableModelActive.getUserId());
                intent.putExtra(USER_NAME,userTableModelActive.getUserName());
                intent.putExtra(USER_ACCESS,userTableModelActive.getUserAccess());
                intent.putExtra(USER_EMAIL,userTableModelActive.getUserEmail());
                startActivity(intent);
            }
            else if(userTableModelActive.getUserAccess() == 1){
                Intent intent = new Intent(MainActivity.this, CustomerActivity.class);
                intent.putExtra(USER_IDENTITY,userTableModelActive.getUserId());
                intent.putExtra(USER_NAME,userTableModelActive.getUserName());
                intent.putExtra(USER_ACCESS,userTableModelActive.getUserAccess());
                intent.putExtra(USER_EMAIL,userTableModelActive.getUserEmail());
                startActivity(intent);
            }
            else {
                Toast.makeText(MainActivity.this, "The username, " + userNameText.getText().toString() + ", created input errors.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public UserItem checkUserLogIn(String userName){
        UserItem userItem = new UserItem();
        userItem = dataSourceUser.getAuthorizedUserData(userName);
        return userItem;
    }

    public boolean isEmailValid(CharSequence email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}