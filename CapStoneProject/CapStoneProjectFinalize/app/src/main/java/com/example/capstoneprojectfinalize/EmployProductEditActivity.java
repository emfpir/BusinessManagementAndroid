package com.example.capstoneprojectfinalize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Transition;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprojectfinalize.Model.DataSourceProduct;
import com.example.capstoneprojectfinalize.Model.DataSourceUser;
import com.example.capstoneprojectfinalize.Model.ProductItem;
import com.example.capstoneprojectfinalize.Model.ProductModelTable;
import com.example.capstoneprojectfinalize.Model.UserItem;

import java.util.List;

public class EmployProductEditActivity extends AppCompatActivity {

    Button buttonProductEditSave, buttonProductEditDelete, buttonProductEditCancel;
    EditText editProductNameText;
    TextView textView7, textView9, userReservation, userReservationName;
    RadioButton sitInProductEditRadio, sitOnProductEditRadio, availableStatusRadio, reservedStatusRadio, checkedOutStatusRadio;
    RadioGroup typeEditRadioGroup, reservationStatusRadioGroup;

    DataSourceProduct mDataSourceProduct;
    DataSourceUser mDataSourceUser;
    ProductItem mProductItem;
    UserItem mUserItem;

    public static final String PRODUCT_ID_KEY =  "product_id_key";
    public static final String PRODUCT_NAME_KEY =  "product_name_key";
    public static final String PRODUCT_DATE_KEY =  "product_date_key";
    public static final String PRODUCT_TYPE_KEY =  "product_type_key";
    public static final String PRODUCT_USER_CREATED_KEY =  "product_user_created_key";
    public static final String PRODUCT_RESERVED_KEY =  "product_reserved_key";
    public static final String PRODUCT_USER_RESERVATION_KEY =  "product_user_reservation_key";
    public static final String USER_IDENTITY = "user_id_key";
    public static final String USER_NAME = "user_name_key";
    public static final String USER_ACCESS = "user_access_key";
    public static final String USER_EMAIL = "user_email_key";
//    public static final String EMPLOY_PRODUCT_EDIT_ACTIVITY = "employProductEditActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employ_product_edit);

        textView7 = findViewById(R.id.textView7);
        textView9 = findViewById(R.id.textView9);
        userReservation = findViewById(R.id.userReservation);
        userReservationName = findViewById(R.id.userReservationName);
        buttonProductEditCancel = findViewById(R.id.button9);
        buttonProductEditDelete = findViewById(R.id.button8);
        buttonProductEditSave = findViewById(R.id.button7);
        editProductNameText = findViewById(R.id.editTextTextPersonName2);
        sitInProductEditRadio = findViewById(R.id.radioButton3);
        sitOnProductEditRadio = findViewById(R.id.radioButton4);
        availableStatusRadio = findViewById(R.id.radioButton5);
        reservedStatusRadio = findViewById(R.id.radioButton6);
        checkedOutStatusRadio = findViewById(R.id.radioButton7);
        typeEditRadioGroup = findViewById(R.id.typeEditRadioGroup);
        reservationStatusRadioGroup = findViewById(R.id.reservationStatusRadioGroup);
        editProductNameText.setFilters(new InputFilter[]{
                new InputFilter() {
                    @Override
                    public CharSequence filter(CharSequence charSequence, int start, int end, Spanned dest, int etStart, int etEnd) {
                        if (charSequence.equals("")) {
                            return charSequence;
                        }
                        if (charSequence.toString().matches("[a-zA-Z 0123456789]+")) {
                            return charSequence;
                        }
                        editProductNameText.setText("");
                        Toast.makeText(EmployProductEditActivity.this, "The product name input must only be a-z, A-Z, and 0-9.", Toast.LENGTH_LONG).show();

                        return "";
                    }
                }
        });

        Intent i = getIntent();
        final Integer currentId = i.getIntExtra( USER_IDENTITY,0);
        final String currentName = i.getStringExtra(USER_NAME);
        final Integer currentAccess = i.getIntExtra(USER_ACCESS,0);
        final String currentEmail = i.getStringExtra(USER_EMAIL);
        final Integer currentProductId = i.getIntExtra(PRODUCT_ID_KEY,0);
        final String currentProductName = i.getStringExtra(PRODUCT_NAME_KEY);
        final String currentProductDate = i.getStringExtra(PRODUCT_DATE_KEY);
        final Integer currentProductType = i.getIntExtra(PRODUCT_TYPE_KEY,0);
        final String currentProductUserCreated = i.getStringExtra(PRODUCT_USER_CREATED_KEY);
        final Integer currentProductReserved = i.getIntExtra(PRODUCT_RESERVED_KEY,0);
        final Integer currentProductUserReservation = i.getIntExtra(PRODUCT_USER_RESERVATION_KEY,0);

        mUserItem = new UserItem(currentId,currentName,currentAccess,currentEmail);
        mProductItem = new ProductItem(currentProductId, currentProductName, currentProductDate,
                currentProductType, currentProductUserCreated, currentProductReserved, currentProductUserReservation);



        editProductNameText.setText(currentProductName);
        textView7.setText(mProductItem.getProductCreatedBy());
        textView9.setText(mProductItem.getProductDate());

        mDataSourceUser = new DataSourceUser(EmployProductEditActivity.this);
        mDataSourceUser.open();
        UserItem reservationUser = mDataSourceUser.userIdToName(mProductItem.getProductUserReservation());
        userReservationName.setText(reservationUser.getUserName());

        if(mProductItem.getProductType()==0){
            sitInProductEditRadio.toggle();
        }
        else if(mProductItem.getProductType()==1){
            sitOnProductEditRadio.toggle();
        }
        if(mProductItem.getProductReversed()==0){
            availableStatusRadio.toggle();
        }
        else if(mProductItem.getProductReversed()==1){
            reservedStatusRadio.toggle();
        }
        else if(mProductItem.getProductReversed()==2){
            checkedOutStatusRadio.toggle();
        }

        buttonProductEditSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(editProductNameText.getText().toString().equals("")) {
                    Toast.makeText(EmployProductEditActivity.this, "The product name needs to be filled out to continue.", Toast.LENGTH_LONG).show();
                }
                else {
                    ProductItem productItem = new ProductItem();
                    productItem.setProductId(currentProductId);
                    productItem.setProductName(editProductNameText.getText().toString());
                    int selectedId = typeEditRadioGroup.getCheckedRadioButtonId();

                    RadioButton radioButton = (RadioButton) typeEditRadioGroup.findViewById(selectedId);
                    productItem.setProductType(Integer.parseInt(radioButton.getContentDescription().toString()));

                    int radioReservationStatus = reservationStatusRadioGroup.getCheckedRadioButtonId();
                    if (reservationStatusRadioGroup.getCheckedRadioButtonId()==0){
                        productItem.setProductUserReservation(currentId);
                    }
                    RadioButton reservationStatusRadioButton = (RadioButton) reservationStatusRadioGroup.findViewById(radioReservationStatus);
                    productItem.setProductReversed(Integer.parseInt(reservationStatusRadioButton.getContentDescription().toString()));

                    mDataSourceProduct = new DataSourceProduct(EmployProductEditActivity.this);
                    mDataSourceProduct.open();
                    mDataSourceProduct.productDatabaseUpdateNameType(productItem);
                    Intent intent = new Intent(EmployProductEditActivity.this, EmployActivity.class);
                    intent.putExtra(USER_IDENTITY, currentId);
                    intent.putExtra(USER_NAME, currentName);
                    intent.putExtra(USER_ACCESS, currentAccess);
                    intent.putExtra(USER_EMAIL, currentEmail);

                    startActivity(intent);
                }
            }
        });

        buttonProductEditDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View x){
                mDataSourceProduct = new DataSourceProduct(EmployProductEditActivity.this);
                mDataSourceProduct.open();
                mDataSourceProduct.deleteProduct(currentProductId);
                Intent intent = new Intent(EmployProductEditActivity.this, EmployActivity.class);
                intent.putExtra(USER_IDENTITY,currentId);
                intent.putExtra(USER_NAME,currentName);
                intent.putExtra(USER_ACCESS,currentAccess);
                intent.putExtra(USER_EMAIL,currentEmail);

                startActivity(intent);
            }
        });

        buttonProductEditCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(EmployProductEditActivity.this, EmployActivity.class);
                intent.putExtra(USER_IDENTITY,currentId);
                intent.putExtra(USER_NAME,currentName);
                intent.putExtra(USER_ACCESS,currentAccess);
                intent.putExtra(USER_EMAIL,currentEmail);

                startActivity(intent);
            }
        });
    }
}