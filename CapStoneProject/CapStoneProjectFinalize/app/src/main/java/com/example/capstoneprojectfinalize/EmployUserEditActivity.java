package com.example.capstoneprojectfinalize;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.capstoneprojectfinalize.Model.DataSourceUser;
import com.example.capstoneprojectfinalize.Model.UserItem;

public class EmployUserEditActivity extends AppCompatActivity {

    Button saveCustomerUpdateButton, cancelCustomerUpdateButton;
    EditText usernameCustomerUpdateEditText, emailCustomerUpdateEditText;

    DataSourceUser mDataSourceUser;
    UserItem mUserItem, mCustomerUpdateItem;


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
        setContentView(R.layout.activity_employ_user_edit);

        saveCustomerUpdateButton = findViewById(R.id.saveEmployUserEditButton);
        cancelCustomerUpdateButton = findViewById(R.id.CancelEmployUserEditButton);
        usernameCustomerUpdateEditText = findViewById(R.id.usernameEditTextEmployUserEdit);
        emailCustomerUpdateEditText = findViewById(R.id.emailEditTextEmployUserEdit);


        Intent i = getIntent();
        final Integer currentId = i.getIntExtra( "user_id_key",0);
        final String currentName = i.getStringExtra("user_name_key");
        final Integer currentAccess = i.getIntExtra("user_access_key",0);
        final String currentEmail = i.getStringExtra("user_email_key");

        final Integer customerId = i.getIntExtra("customer_id_key", 0);
        final String customerName = i.getStringExtra("customer_username_key");
        final String customerEmail = i.getStringExtra("customer_email_key");

        mUserItem = new UserItem(currentId,currentName,currentAccess,currentEmail);
        mCustomerUpdateItem = new UserItem(customerId,customerName,1,customerEmail);

        usernameCustomerUpdateEditText.setText(customerName);
        emailCustomerUpdateEditText.setText(customerEmail);

        usernameCustomerUpdateEditText.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence charSequence, int start, int end, Spanned dest, int etStart, int etEnd) {
                        if (charSequence.equals("")) {
                            return charSequence;
                        }
                        if (charSequence.toString().matches("[a-zA-Z]+")) {
                            return charSequence;
                        }
                        usernameCustomerUpdateEditText.setText("");
                        Toast.makeText(EmployUserEditActivity.this, "The username must only contain a-z or A-Z with no spaces.", Toast.LENGTH_LONG).show();

                        return "";
                    }
                }
        });

        saveCustomerUpdateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View x) {
                if (usernameCustomerUpdateEditText.getText().toString().equals("")) {
                    Toast.makeText(EmployUserEditActivity.this, "The username needs to be filled out to continue.", Toast.LENGTH_LONG).show();
                }
                else if (mDataSourceUser.testUniqueCustomer(usernameCustomerUpdateEditText.getText().toString())) {
                    Toast.makeText(EmployUserEditActivity.this, "The username needs to be unique.", Toast.LENGTH_LONG).show();
                }
                else {
                    UserItem userItem = new UserItem();
                    userItem.setUserId(customerId);
                    userItem.setUserName(usernameCustomerUpdateEditText.getText().toString());
                    userItem.setUserEmail(emailCustomerUpdateEditText.getText().toString());

                    if (isEmailValid(userItem.getUserEmail())) {
                        mDataSourceUser.open();
                        mDataSourceUser.updateCustomer(userItem);
                    } else {
                        mDataSourceUser.open();
                        mDataSourceUser.updateCustomerName(userItem);
                    }
                    Intent intent = new Intent(EmployUserEditActivity.this, EmployUserActivity.class);
                    intent.putExtra(USER_IDENTITY, currentId);
                    intent.putExtra(USER_NAME, currentName);
                    intent.putExtra(USER_ACCESS, currentAccess);
                    intent.putExtra(USER_EMAIL, currentEmail);
                    startActivity(intent);
                }
            }
        });
        cancelCustomerUpdateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View x){
                Intent intent = new Intent(EmployUserEditActivity.this, EmployUserActivity.class);
                intent.putExtra(USER_IDENTITY,currentId);
                intent.putExtra(USER_NAME,currentName);
                intent.putExtra(USER_ACCESS,currentAccess);
                intent.putExtra(USER_EMAIL,currentEmail);
                startActivity(intent);
            }
        });
    }

    public boolean isEmailValid(CharSequence email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}