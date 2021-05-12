package com.example.capstoneprojectfinalize;

import androidx.annotation.NonNull;
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
import com.example.capstoneprojectfinalize.Model.DataSourceProduct;
import com.example.capstoneprojectfinalize.Model.DataSourceUser;
import com.example.capstoneprojectfinalize.Model.ProductItem;
import com.example.capstoneprojectfinalize.Model.UserItem;

import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    Button buttonCustomerContactUs, buttonCustomerCart, buttonCustomerLogOut, buttonCustomerReportAll, buttonCustomerReportSitIn, buttonCustomerReportSitOn;

    UserItem mUserItem;
    DataSourceProduct mDataSourceProduct;

    List<ProductItem> mProductItemList;
    ProductCustomerActivityAdapter mProductCustomerActivityAdapter;
    RecyclerView mRecyclerView;

    public static final String USER_IDENTITY = "user_id_key";
    public static final String USER_NAME = "user_name_key";
    public static final String USER_ACCESS = "user_access_key";
    public static final String USER_EMAIL = "user_email_key";
    public static final String CUSTOMER_ACTIVITY = "customerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        buttonCustomerContactUs = findViewById(R.id.button10);
        buttonCustomerCart = findViewById(R.id.button11);
        buttonCustomerLogOut = findViewById(R.id.button12);
        buttonCustomerReportAll = findViewById(R.id.button13);
        buttonCustomerReportSitIn = findViewById(R.id.button14);
        buttonCustomerReportSitOn = findViewById(R.id.button15);

        Intent i = getIntent();
        final Integer currentId = i.getIntExtra(USER_IDENTITY,0);
        final String currentName = i.getStringExtra(USER_NAME);
        final Integer currentAccess = i.getIntExtra(USER_ACCESS,0);
        final String currentEmail = i.getStringExtra(USER_EMAIL);

        mUserItem = new UserItem(currentId,currentName,currentAccess,currentEmail);

        //recycle view function
        makeProductList();

        buttonCustomerContactUs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View x){
                Intent intent = new Intent(CustomerActivity.this, ReportBugActivity.class);
                intent.putExtra(USER_IDENTITY,currentId);
                intent.putExtra(USER_NAME,currentName);
                intent.putExtra(USER_ACCESS,currentAccess);
                intent.putExtra(USER_EMAIL,currentEmail);
                startActivity(intent);
            }
        });

        buttonCustomerCart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(CustomerActivity.this, CustomerReservationActivity.class);
                intent.putExtra(USER_IDENTITY,currentId);
                intent.putExtra(USER_NAME,currentName);
                intent.putExtra(USER_ACCESS,currentAccess);
                intent.putExtra(USER_EMAIL,currentEmail);
                startActivity(intent);
            }
        });

        buttonCustomerLogOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View x){
                Intent intent = new Intent(CustomerActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonCustomerReportAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                mDataSourceProduct.open();
                mProductItemList = mDataSourceProduct.getAllAvailableProducts();
                mProductCustomerActivityAdapter = new ProductCustomerActivityAdapter(CustomerActivity.this, mProductItemList, mUserItem, CUSTOMER_ACTIVITY);
                mRecyclerView = findViewById(R.id.listViewCustomerProduct);
                mRecyclerView.setAdapter(mProductCustomerActivityAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(CustomerActivity.this));
            }
        });

        buttonCustomerReportSitIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View x){
                mDataSourceProduct.open();
                mProductItemList = mDataSourceProduct.getAllSitInProducts(mUserItem.getUserId());
                mProductCustomerActivityAdapter = new ProductCustomerActivityAdapter(CustomerActivity.this, mProductItemList, mUserItem, CUSTOMER_ACTIVITY);
                mRecyclerView = findViewById(R.id.listViewCustomerProduct);
                mRecyclerView.setAdapter(mProductCustomerActivityAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(CustomerActivity.this));
            }
        });

        buttonCustomerReportSitOn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View x){
                mDataSourceProduct.open();
                mProductItemList = mDataSourceProduct.getAllSitOnProducts(mUserItem.getUserId());
                mProductCustomerActivityAdapter = new ProductCustomerActivityAdapter(CustomerActivity.this, mProductItemList, mUserItem, CUSTOMER_ACTIVITY);
                mRecyclerView = findViewById(R.id.listViewCustomerProduct);
                mRecyclerView.setAdapter(mProductCustomerActivityAdapter);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(CustomerActivity.this));
            }
        });
    }

//    public void setKayakReserved(ProductItem productItem){
//        mDataSourceProduct.open();
//        mDataSourceProduct.reserveKayak(productItem);
//
//        mDataSourceProduct.open();
//        final List<ProductItem> productItemList = mDataSourceProduct.getAllAvailableProducts();
//        mProductCustomerActivityAdapter = new ProductCustomerActivityAdapter(CustomerActivity.this, productItemList, mUserItem, CUSTOMER_ACTIVITY);
//
//        mRecyclerView = findViewById(R.id.listViewCustomerProduct);
//        mRecyclerView.setAdapter(mProductCustomerActivityAdapter);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//    }

    public void makeProductList(){
        mDataSourceProduct = new DataSourceProduct(this);
        mDataSourceProduct.open();
        final List<ProductItem> productItemList = mDataSourceProduct.getAllAvailableProducts();
        mProductCustomerActivityAdapter = new ProductCustomerActivityAdapter(CustomerActivity.this, productItemList, mUserItem, CUSTOMER_ACTIVITY);

        mRecyclerView = findViewById(R.id.listViewCustomerProduct);
        mRecyclerView.setAdapter(mProductCustomerActivityAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }



}