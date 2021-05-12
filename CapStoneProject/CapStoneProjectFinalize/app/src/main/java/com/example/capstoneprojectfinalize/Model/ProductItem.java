package com.example.capstoneprojectfinalize.Model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.capstoneprojectfinalize.Adapter.ProductCustomerActivityAdapter;

import static com.example.capstoneprojectfinalize.Model.ProductModelTable.COLUMN_PRODUCT_DATE;
import static com.example.capstoneprojectfinalize.Model.ProductModelTable.COLUMN_PRODUCT_ID;
import static com.example.capstoneprojectfinalize.Model.ProductModelTable.COLUMN_PRODUCT_NAME;
import static com.example.capstoneprojectfinalize.Model.ProductModelTable.COLUMN_PRODUCT_RESERVED;
import static com.example.capstoneprojectfinalize.Model.ProductModelTable.COLUMN_PRODUCT_TYPE;
import static com.example.capstoneprojectfinalize.Model.ProductModelTable.COLUMN_PRODUCT_USER_CREATED;
import static com.example.capstoneprojectfinalize.Model.ProductModelTable.COLUMN_PRODUCT_USER_RESERVATION;

public class ProductItem implements Parcelable {

    private Integer productId;
    private String productName;
    private String productDate;
    private Integer productType;
    private String productCreatedBy;
    private Integer productReversed;
    private Integer productUserReservation;


    public ProductItem(Integer productId, String productName, String productDate, Integer productType, String productCreatedBy, Integer productReversed, Integer productUserReservation) {
        this.productId = productId;
        this.productName = productName;
        this.productDate = productDate;
        this.productType = productType;
        this.productCreatedBy = productCreatedBy;
        this.productReversed = productReversed;
        this.productUserReservation = productUserReservation;
    }

    public ProductItem() {
    }

    @Override
    public String toString() {
        return "ProductItem{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productDate='" + productDate + '\'' +
                ", productType='" + productType + '\'' +
                ", productCreatedBy='" + productCreatedBy + '\'' +
                ", productReversed='" + productReversed + '\'' +
                ", productUserReservation='" + productUserReservation + '\'' +
                '}';
    }

    public ContentValues toValues() {
        ContentValues values = new ContentValues(7);

        values.put(COLUMN_PRODUCT_ID, productId);
        values.put(COLUMN_PRODUCT_NAME, productName);
        values.put(COLUMN_PRODUCT_DATE, productDate);
        values.put(COLUMN_PRODUCT_TYPE, productType);
        values.put(COLUMN_PRODUCT_USER_CREATED, productCreatedBy);
        values.put(COLUMN_PRODUCT_RESERVED, productReversed);
        values.put(COLUMN_PRODUCT_USER_RESERVATION, productUserReservation);

        return values;
    }


    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDate() {
        return productDate;
    }

    public void setProductDate(String productDate) {
        this.productDate = productDate;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public String getProductCreatedBy() {
        return productCreatedBy;
    }

    public void setProductCreatedBy(String productCreatedBy) {
        this.productCreatedBy = productCreatedBy;
    }

    public Integer getProductReversed() {
        return productReversed;
    }

    public void setProductReversed(Integer productReversed) {
        this.productReversed = productReversed;
    }

    public Integer getProductUserReservation() {
        return productUserReservation;
    }

    public void setProductUserReservation(Integer productUserReservation) {
        this.productUserReservation = productUserReservation;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.productId);
        dest.writeString(this.productName);
        dest.writeString(this.productDate);
        dest.writeInt(this.productType);
        dest.writeString(this.productCreatedBy);
        dest.writeInt(this.productReversed);
        dest.writeInt(this.productUserReservation);
    }

    protected ProductItem(Parcel in) {
        this.productId = in.readInt();
        this.productName = in.readString();
        this.productDate = in.readString();
        this.productType = in.readInt();
        this.productCreatedBy = in.readString();
        this.productReversed = in.readInt();
        this.productUserReservation = in.readInt();
    }

    public static final Creator<ProductItem> CREATOR = new Creator<ProductItem>() {
        @Override
        public ProductItem createFromParcel(Parcel source) { return new ProductItem(source); }

        @Override
        public ProductItem[] newArray(int size) { return new ProductItem[size];}
    };
}
