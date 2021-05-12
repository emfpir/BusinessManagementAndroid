package com.example.capstoneprojectfinalize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.Toast;

import com.example.capstoneprojectfinalize.Adapter.ProductCustomerActivityAdapter;
import com.example.capstoneprojectfinalize.Adapter.ProductCustomerReservationActivityAdapter;
import com.example.capstoneprojectfinalize.Model.DataSourceProduct;
import com.example.capstoneprojectfinalize.Model.DataSourceUser;
import com.example.capstoneprojectfinalize.Model.ProductItem;
import com.example.capstoneprojectfinalize.Model.UserItem;

import java.util.List;

public class CustomerReservationActivity extends AppCompatActivity {

   Button buttonCustomerReservationExit, buttonCustomerReservationLogOff;

   ProductItem mProductItem;
   UserItem mUserItem;
   DataSourceProduct mDataSourceProduct;

//   DataSourceUser mDataSourceUser;

//   List<ProductItem> mProductItems;
   ProductCustomerReservationActivityAdapter mProductCustomerReservationActivityAdapter;
   RecyclerView mRecyclerView;
//   CursorAdapter mCursorAdapter;

    public static final String USER_IDENTITY = "user_id_key";
    public static final String USER_NAME = "user_name_key";
    public static final String USER_ACCESS = "user_access_key";
    public static final String USER_EMAIL = "user_email_key";
//    public static final String PRODUCT_ID_KEY =  "product_id_key";
//    public static final String PRODUCT_NAME_KEY =  "product_name_key";
//    public static final String PRODUCT_DATE_KEY =  "product_date_key";
//    public static final String PRODUCT_TYPE_KEY =  "product_type_key";
//    public static final String PRODUCT_USER_CREATED_KEY =  "product_user_created_key";
//    public static final String PRODUCT_RESERVED_KEY =  "product_reserved_key";
//    public static final String PRODUCT_USER_RESERVATION_KEY =  "product_user_reservation_key";
    public static final String CUSTOMER_RESERVATION_ACTIVITY = "customerReservationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reservation);

        buttonCustomerReservationExit = findViewById(R.id.button17);
        buttonCustomerReservationLogOff = findViewById(R.id.button16);

        Intent i = getIntent();
        final Integer currentId = i.getIntExtra( USER_IDENTITY,0);
        final String currentName = i.getStringExtra(USER_NAME);
        final Integer currentAccess = i.getIntExtra(USER_ACCESS,0);
        final String currentEmail = i.getStringExtra(USER_EMAIL);

        mUserItem = new UserItem(currentId,currentName,currentAccess,currentEmail);

        mDataSourceProduct = new DataSourceProduct(this);
        mDataSourceProduct.open();
        final List<ProductItem> productItemList = mDataSourceProduct.getAllLocalReservedProducts(mUserItem.getUserId());
        mProductCustomerReservationActivityAdapter = new ProductCustomerReservationActivityAdapter(CustomerReservationActivity.this, productItemList, mUserItem, CUSTOMER_RESERVATION_ACTIVITY);

        mRecyclerView = findViewById(R.id.listViewCustomerReservation);
        mRecyclerView.setAdapter(mProductCustomerReservationActivityAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonCustomerReservationExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerReservationActivity.this, CustomerActivity.class);
                intent.putExtra(USER_IDENTITY,currentId);
                intent.putExtra(USER_NAME,currentName);
                intent.putExtra(USER_ACCESS,currentAccess);
                intent.putExtra(USER_EMAIL,currentEmail);

                startActivity(intent);
            }
        });

        buttonCustomerReservationLogOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View x) {
                Intent intent = new Intent(CustomerReservationActivity.this, MainActivity.class);

                startActivity(intent);
            }
        });
    }
}