package com.example.capstoneprojectfinalize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.capstoneprojectfinalize.Adapter.ModifyUserActivityAdapter;
import com.example.capstoneprojectfinalize.Model.DataSourceUser;
import com.example.capstoneprojectfinalize.Model.UserItem;

import java.util.List;

public class EmployUserActivity extends AppCompatActivity {

    Button logoffButtonModify, productButtonModify, newCustomerButtonModify, clearButtonModify;
    EditText usernameTextModify, emailTextModify;

    DataSourceUser mDataSourceUser;
//    CursorAdapter mCursorAdapter;
    UserItem mUserItem;

    RecyclerView mRecyclerView;
    ModifyUserActivityAdapter modifyUserActivityAdapter;

    public static final String USER_IDENTITY = "user_id_key";
    public static final String USER_NAME = "user_name_key";
    public static final String USER_ACCESS = "user_access_key";
    public static final String USER_EMAIL = "user_email_key";
    public static final String EMPLOY_ACTIVITY = "employActivity";
    public static final String CUSTOMER_ACTIVITY = "customerActivity";
    public static final String CUSTOMER_UPDATE_IDENTITY = "customer_id_key";
    public static final String CUSTOMER_UPDATE_USERNAME = "customer_username_key";
    public static final String CUSTOMER_UPDATE_ACCESS = "customer_access_key";
    public static final String CUSTOMER_UPDATE_EMAIL = "customer_email_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employ_user);

        logoffButtonModify = findViewById(R.id.modifyLogOffButton);
        productButtonModify = findViewById(R.id.modifyProductButton);
        newCustomerButtonModify = findViewById(R.id.modifyNewCustomerButton);
        clearButtonModify = findViewById(R.id.modifyClearButton);
        usernameTextModify = findViewById(R.id.modifyEditTextUsername);
        emailTextModify = findViewById(R.id.modifyEditTextEmailAddress);
        usernameTextModify.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence charSequence, int start, int end, Spanned dest, int etStart, int etEnd) {
                        if (charSequence.equals("")) {
                            return charSequence;
                        }
                        if (charSequence.toString().matches("[a-zA-Z]+")) {
                            return charSequence;
                        }
                        usernameTextModify.setText("");
                        Toast.makeText(EmployUserActivity.this, "The username input must only be a-z or A-Z with no spaces.", Toast.LENGTH_LONG).show();

                        return "";
                    }
                }
        });

        Intent i = getIntent();
        final Integer currentId = i.getIntExtra( "user_id_key",0);
        final String currentName = i.getStringExtra("user_name_key");
        final Integer currentAccess = i.getIntExtra("user_access_key",0);
        final String currentEmail = i.getStringExtra("user_email_key");

        mUserItem = new UserItem(currentId,currentName,currentAccess,currentEmail);

        mDataSourceUser = new DataSourceUser(this);
        mDataSourceUser.open();
        final List<UserItem> userItemList = mDataSourceUser.getAllCustomerUserData();
        modifyUserActivityAdapter = new ModifyUserActivityAdapter(EmployUserActivity.this, userItemList, mUserItem, EMPLOY_ACTIVITY);

        mRecyclerView = findViewById(R.id.modifyRecyclerView);
        mRecyclerView.setAdapter(modifyUserActivityAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        logoffButtonModify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployUserActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        productButtonModify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(EmployUserActivity.this, EmployActivity.class);
                intent.putExtra(USER_IDENTITY,currentId);
                intent.putExtra(USER_NAME,currentName);
                intent.putExtra(USER_ACCESS,currentAccess);
                intent.putExtra(USER_EMAIL,currentEmail);
                startActivity(intent);
            }
        });

        newCustomerButtonModify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(usernameTextModify.getText().toString().equals("")) {
                    Toast.makeText(EmployUserActivity.this, "The username needs to be filled out to continue.", Toast.LENGTH_LONG).show();
                }
                else if (mDataSourceUser.testUniqueCustomer(usernameTextModify.getText().toString())){
                    Toast.makeText(EmployUserActivity.this, "The username needs to be unique.", Toast.LENGTH_LONG).show();
                }
                else {
                    if (isEmailValid(emailTextModify.getText().toString())) {
                        UserItem userItem = new UserItem();
                        userItem.setUserName(usernameTextModify.getText().toString());
                        userItem.setUserEmail(emailTextModify.getText().toString());
                        userItem.setUserAccess(1);
                        mDataSourceUser.addNewCustomer(userItem);
                        Intent intent = new Intent(EmployUserActivity.this, EmployUserActivity.class);
                        intent.putExtra(USER_IDENTITY, currentId);
                        intent.putExtra(USER_NAME, currentName);
                        intent.putExtra(USER_ACCESS, currentAccess);
                        intent.putExtra(USER_EMAIL, currentEmail);
                        startActivity(intent);
                    }
                    Toast.makeText(EmployUserActivity.this, "The email address entered is valid.", Toast.LENGTH_LONG).show();
                }

            }
        });

        clearButtonModify.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                usernameTextModify.setText("");
                emailTextModify.setText("");
            }
        });
    }

    public boolean isEmailValid(CharSequence email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}