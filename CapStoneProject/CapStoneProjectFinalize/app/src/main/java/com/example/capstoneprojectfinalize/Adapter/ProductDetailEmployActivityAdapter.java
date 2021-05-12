package com.example.capstoneprojectfinalize.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectfinalize.EmployProductEditActivity;
import com.example.capstoneprojectfinalize.Model.DataSourceProduct;
import com.example.capstoneprojectfinalize.Model.ProductItem;
import com.example.capstoneprojectfinalize.Model.UserItem;
import com.example.capstoneprojectfinalize.R;

import org.w3c.dom.Text;

import java.util.List;

public class ProductDetailEmployActivityAdapter extends RecyclerView.Adapter<ProductDetailEmployActivityAdapter.ViewHolder> {

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
    public static final String EMPLOY_ACTIVITY = "employActivity";
    public static final String CUSTOMER_ACTIVITY = "customerActivity";
    public static final String CUSTOMER_RESERVATION_ACTIVITY = "customerReservationActivity";

    private String mLocationActivity;
    private UserItem mUserItem;
    private ProductItem mProductItem;
    private List<ProductItem> mProductItemList;
    private Context mContext;
    DataSourceProduct dataSourceProduct;

    public ProductDetailEmployActivityAdapter(Context context, List<ProductItem> productItems, UserItem userItem,
                                              String locationActivity) {
        mContext = context;
        mProductItemList = productItems;
        mUserItem = userItem;
        mLocationActivity = locationActivity;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_detail_product, parent, false);
        ProductDetailEmployActivityAdapter.ViewHolder viewHolder = new ProductDetailEmployActivityAdapter.ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductDetailEmployActivityAdapter.ViewHolder holder, int position) {
        final ProductItem item = mProductItemList.get(position);
        mProductItem = mProductItemList.get(position);
        mProductItemList.get(position);

        holder.kayakName.setText(mProductItemList.get(position).getProductName());
        if(mProductItemList.get(position).getProductType() == 1){

            holder.kayakType.setText("Sit On Top");
        }
        else {holder.kayakType.setText("Sit in");}

        if(mProductItemList.get(position).getProductReversed() == 0) {
            holder.kayakReserved.setText("Available");
        }
        else if (mProductItemList.get(position).getProductReversed() == 1) {
            holder.kayakReserved.setText("Reserved");
        }
        else {holder.kayakReserved.setText("Checked Out");}
        //holder.kayakReserved.setText(mProductItemList.get(position).getProductName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EmployProductEditActivity.class);
                intent.putExtra(PRODUCT_ID_KEY, item.getProductId());
                intent.putExtra(PRODUCT_NAME_KEY, item.getProductName());
                intent.putExtra(PRODUCT_DATE_KEY, item.getProductDate());
                intent.putExtra(PRODUCT_TYPE_KEY, item.getProductType());
                intent.putExtra(PRODUCT_USER_CREATED_KEY, item.getProductCreatedBy());
                intent.putExtra(PRODUCT_RESERVED_KEY, item.getProductReversed());
                intent.putExtra(PRODUCT_USER_RESERVATION_KEY, item.getProductUserReservation());
                intent.putExtra(USER_IDENTITY,mUserItem.getUserId());
                intent.putExtra(USER_NAME, mUserItem.getUserName());
                intent.putExtra(USER_ACCESS, mUserItem.getUserAccess());
                intent.putExtra(USER_EMAIL, mUserItem.getUserEmail());
                mContext.startActivity(intent);
            }





        });
    }

    @Override
    public int getItemCount() {return mProductItemList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView kayakName;
        public TextView kayakType;
        public TextView kayakReserved;
        public View mView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kayakName = itemView.findViewById(R.id.productDetailViewName);
            kayakType = itemView.findViewById(R.id.productDetailViewtype);
            kayakReserved = itemView.findViewById(R.id.productDetailViewReserved);
            mView = itemView;
        }
    }
}
