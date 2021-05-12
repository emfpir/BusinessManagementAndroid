package com.example.capstoneprojectfinalize.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectfinalize.EmployProductEditActivity;
import com.example.capstoneprojectfinalize.EmployUserEditActivity;
import com.example.capstoneprojectfinalize.MainActivity;
import com.example.capstoneprojectfinalize.Model.DataSourceProduct;
import com.example.capstoneprojectfinalize.Model.ProductItem;
import com.example.capstoneprojectfinalize.Model.UserItem;
import com.example.capstoneprojectfinalize.R;

import java.util.List;

public class ModifyUserActivityAdapter extends RecyclerView.Adapter<ModifyUserActivityAdapter.ViewHolder> {

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

    private String mLocationActivity;
    private UserItem mUserItem;
    private List<UserItem> mUserItemList;
    private Context mContext;

    public ModifyUserActivityAdapter(Context context, List<UserItem> userItemList, UserItem userItem, String locationActivity) {
        mContext = context;
        mUserItemList = userItemList;
        mUserItem = userItem;
        mLocationActivity = locationActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.list_customer_user, parent, false);
        ModifyUserActivityAdapter.ViewHolder viewHolder = new ModifyUserActivityAdapter.ViewHolder(itemView);
//        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ModifyUserActivityAdapter.ViewHolder holder, int position) {
        final UserItem item = mUserItemList.get(position);
        mUserItem = mUserItemList.get(position);
        mUserItemList.get(position);

        holder.userName.setText(mUserItemList.get(position).getUserName());
        holder.userEmail.setText(mUserItemList.get(position).getUserEmail());

        holder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EmployUserEditActivity.class);
                intent.putExtra(CUSTOMER_UPDATE_IDENTITY,item.getUserId());
                intent.putExtra(CUSTOMER_UPDATE_USERNAME,item.getUserName());
                intent.putExtra(CUSTOMER_UPDATE_EMAIL,item.getUserEmail());
                intent.putExtra(USER_IDENTITY,mUserItem.getUserId());
                intent.putExtra(USER_NAME, mUserItem.getUserName());
                intent.putExtra(USER_ACCESS, mUserItem.getUserAccess());
                intent.putExtra(USER_EMAIL, mUserItem.getUserEmail());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {return mUserItemList.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public TextView userEmail;
        public View mView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.usernameEmployCustomerModifyText);
            userEmail = itemView.findViewById(R.id.emailEmployCustomerModifyText);
            mView = itemView;
        }
    }


}
