package com.example.capstoneprojectfinalize.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectfinalize.CustomerReservationActivity;
import com.example.capstoneprojectfinalize.Model.DataSourceProduct;
import com.example.capstoneprojectfinalize.Model.ProductItem;
import com.example.capstoneprojectfinalize.Model.UserItem;
import com.example.capstoneprojectfinalize.R;
import java.util.List;

public class ProductCustomerActivityAdapter extends RecyclerView.Adapter<ProductCustomerActivityAdapter.ViewHolder> {

    public static final String USER_IDENTITY = "user_id_key";
    public static final String USER_NAME = "user_name_key";
    public static final String USER_ACCESS = "user_access_key";
    public static final String USER_EMAIL = "user_email_key";

    private String mLocationActivity;
    private UserItem mUserItem;
    private ProductItem mProductItem;
    private List<ProductItem> mProductItemList;
    private Context mContext;

    public ProductCustomerActivityAdapter(Context context, List<ProductItem> productItems, UserItem userItem, String locationActivity) {
        mContext = context;
        mProductItemList = productItems;
        mUserItem = userItem;
        mLocationActivity = locationActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final ProductItem item = mProductItemList.get(position);
        mProductItem = mProductItemList.get(position);
        mProductItemList.get(position);

        holder.productName.setText(mProductItemList.get(position).getProductName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductItem reserveItem = new ProductItem();
                reserveItem.setProductId(item.getProductId());
                Integer userReservedKayak = mUserItem.getUserId();
                reserveItem.setProductUserReservation(userReservedKayak);
                reserveItem.setProductReversed(1);

                DataSourceProduct dataSourceProduct = new DataSourceProduct(mContext);
                dataSourceProduct.open();
                dataSourceProduct.reserveKayak(reserveItem);

                Intent intent = new Intent(mContext, CustomerReservationActivity.class);
                intent.putExtra(USER_IDENTITY,mUserItem.getUserId());
                intent.putExtra(USER_NAME,mUserItem.getUserName());
                intent.putExtra(USER_ACCESS,mUserItem.getUserAccess());
                intent.putExtra(USER_EMAIL,mUserItem.getUserEmail());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {return mProductItemList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView productName;
        public View mView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productDetailViewName);
            mView = itemView;
        }
    }
}
