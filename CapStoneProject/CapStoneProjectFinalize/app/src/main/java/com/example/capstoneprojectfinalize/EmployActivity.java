package com.example.capstoneprojectfinalize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.capstoneprojectfinalize.Adapter.ProductDetailEmployActivityAdapter;
import com.example.capstoneprojectfinalize.Adapter.ProductEmployActivityAdapter;
import com.example.capstoneprojectfinalize.Model.DataSourceProduct;
import com.example.capstoneprojectfinalize.Model.DataSourceUser;
import com.example.capstoneprojectfinalize.Model.ProductItem;
import com.example.capstoneprojectfinalize.Model.UserItem;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class EmployActivity extends AppCompatActivity {

    Button productSaveButton, productContactUsButton, productLogOutButton, productReportAvailableButton,
            productReportReservedButton, productReportViewAllRecyclerDetailButton, productReportCheckOutButton, employeeCustomerAccessButton;
    EditText productSaveNameEditText;
    RadioButton productTypeSitInRadio, productTypeSitOnRadio;
    RadioGroup typeAddRadioGroup;

//    Calendar calendar = Calendar.getInstance();
//    String currentDate = DateFormat.getDateInstance().format(calendar.getTime());

    RecyclerView mRecyclerView;
//    DataSourceUser mDataSourceUser;
    DataSourceProduct mDataSourceProduct;
//    CursorAdapter mCursorAdapter;
//    ProductItem mProductItem;
    UserItem mUserItem;
    ProductEmployActivityAdapter mProductEmployActivityAdapter;
    ProductDetailEmployActivityAdapter mProductDetailEmployActivityAdapter;
//    List<UserItem> mUserItemList;
    List<ProductItem> mProductItemList;

    public static final String USER_IDENTITY = "user_id_key";
    public static final String USER_NAME = "user_name_key";
    public static final String USER_ACCESS = "user_access_key";
    public static final String USER_EMAIL = "user_email_key";
    public static final String EMPLOY_ACTIVITY = "employActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employ);

        productSaveButton = findViewById(R.id.button);
        productContactUsButton  = findViewById(R.id.button2);
        productLogOutButton  = findViewById(R.id.button3);
        productReportAvailableButton = findViewById(R.id.button4);
        productReportReservedButton = findViewById(R.id.button5);
        productReportCheckOutButton = findViewById(R.id.button20);
        productReportViewAllRecyclerDetailButton = findViewById(R.id.button6);
        productSaveNameEditText = findViewById(R.id.editTextTextProductName);
        productTypeSitInRadio = findViewById(R.id.radioButton);
        productTypeSitOnRadio = findViewById(R.id.radioButton2);
        typeAddRadioGroup = findViewById(R.id.typeAddRadioGroup);
        employeeCustomerAccessButton = findViewById(R.id.CustomerUpdateButton);
        productSaveNameEditText.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence charSequence, int start, int end, Spanned dest, int etStart, int etEnd) {
                        if (charSequence.equals("")) {
                            return charSequence;
                        }
                        if (charSequence.toString().matches("[a-zA-Z 0123456789]+")) {
                            return charSequence;
                        }
                        productSaveNameEditText.setText("");
                        Toast.makeText(EmployActivity.this, "The product name input must only be a-z, A-Z, and 0-9.", Toast.LENGTH_LONG).show();

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

        mDataSourceProduct = new DataSourceProduct(this);
        mDataSourceProduct.open();
        final List<ProductItem> productItemList = mDataSourceProduct.getAllProducts();
        mProductEmployActivityAdapter = new ProductEmployActivityAdapter( EmployActivity.this,
                productItemList, mUserItem, EMPLOY_ACTIVITY );

        mRecyclerView = findViewById(R.id.productDisplayList);
        mRecyclerView.setAdapter(mProductEmployActivityAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add the Listener to the RadioGroup
        typeAddRadioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    // The flow will come here when
                    // any of the radio buttons in the radioGroup
                    // has been clicked
                    // Check which radio button has been clicked
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // Get the selected Radio Button
                        RadioButton radioButton = (RadioButton)group.findViewById(checkedId);
                    }
                });

        productSaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ProductItem productItem = new ProductItem();

                if (productSaveNameEditText.getText().toString().equals("")) {
                    Toast.makeText(EmployActivity.this, "No answer name has been entered. Please, enter name to save kayak", Toast.LENGTH_SHORT).show();
                }
                else {
                    productItem.setProductName(productSaveNameEditText.getText().toString());
                    productItem.setProductDate(getCurrentDate());
                    int selectedId = typeAddRadioGroup.getCheckedRadioButtonId();

                    RadioButton radioButton = (RadioButton) typeAddRadioGroup.findViewById(selectedId);

                    productItem.setProductType(Integer.parseInt(radioButton.getContentDescription().toString()));
                    productItem.setProductCreatedBy(mUserItem.getUserName());
                    productItem.setProductReversed(0);
                    productItem.setProductUserReservation(currentId);
                    mDataSourceProduct.createProduct(productItem);

                    List<ProductItem> productItemList = mDataSourceProduct.getAllProducts();
                    ProductEmployActivityAdapter adapter = new ProductEmployActivityAdapter(EmployActivity.this,
                            productItemList, mUserItem, EMPLOY_ACTIVITY);

                    RecyclerView recyclerView = findViewById(R.id.productDisplayList);
                    recyclerView.setAdapter(adapter);
                    productSaveNameEditText.getText().clear();
                    typeAddRadioGroup.clearFocus();
                }
            }
        });

        productContactUsButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View x){
                Intent intent = new Intent(EmployActivity.this, ReportBugActivity.class);
                intent.putExtra(USER_IDENTITY,currentId);
                intent.putExtra(USER_NAME,currentName);
                intent.putExtra(USER_ACCESS,currentAccess);
                intent.putExtra(USER_EMAIL,currentEmail);
                startActivity(intent);
            }
        });

        employeeCustomerAccessButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View x){
                Intent intent = new Intent(EmployActivity.this, EmployUserActivity.class);
                intent.putExtra(USER_IDENTITY,currentId);
                intent.putExtra(USER_NAME,currentName);
                intent.putExtra(USER_ACCESS,currentAccess);
                intent.putExtra(USER_EMAIL,currentEmail);
                startActivity(intent);
            }
        });


        productLogOutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(EmployActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        productReportAvailableButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View x){
                mDataSourceProduct.open();
                mProductItemList = mDataSourceProduct.getAllAvailableProducts();
                mProductEmployActivityAdapter = new ProductEmployActivityAdapter(EmployActivity.this,
                        mProductItemList, mUserItem, EMPLOY_ACTIVITY);
                mRecyclerView = findViewById(R.id.productDisplayList);
                mRecyclerView.setAdapter(mProductEmployActivityAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(EmployActivity.this));
            }
        });

        productReportReservedButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mDataSourceProduct.open();
                mProductItemList = mDataSourceProduct.getAllReservedProducts();
                mProductEmployActivityAdapter = new ProductEmployActivityAdapter(EmployActivity.this,
                        mProductItemList, mUserItem, EMPLOY_ACTIVITY);
                mRecyclerView = findViewById(R.id.productDisplayList);
                mRecyclerView.setAdapter(mProductEmployActivityAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(EmployActivity.this));
            }
        });
        productReportCheckOutButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View x){
                mDataSourceProduct.open();
                mProductItemList = mDataSourceProduct.getAllCheckedOutProducts();
                mProductEmployActivityAdapter = new ProductEmployActivityAdapter(EmployActivity.this,
                        mProductItemList, mUserItem, EMPLOY_ACTIVITY);
                mRecyclerView = findViewById(R.id.productDisplayList);
                mRecyclerView.setAdapter(mProductEmployActivityAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(EmployActivity.this));
            }
        });
        productReportViewAllRecyclerDetailButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View x){
                mDataSourceProduct.open();
                mProductItemList = mDataSourceProduct.getAllProducts();
                mProductDetailEmployActivityAdapter = new ProductDetailEmployActivityAdapter(EmployActivity.this,
                        mProductItemList, mUserItem, EMPLOY_ACTIVITY);
                mRecyclerView = findViewById(R.id.productDisplayList);
                mRecyclerView.setAdapter(mProductDetailEmployActivityAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(EmployActivity.this));
            }
        });
    }

    public String getCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
        return  currentDate;
    }
}