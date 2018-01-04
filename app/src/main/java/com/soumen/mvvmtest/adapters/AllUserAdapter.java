package com.soumen.mvvmtest.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.soumen.mvvmtest.R;
import com.soumen.mvvmtest.roomdbops.entities.UserEntity;
import java.util.List;

/**
 * Created by Soumen on 03-01-2018.
 */
public class AllUserAdapter extends RecyclerView.Adapter<AllUserAdapter.UserViewHolder> {

    private Context context;
    private List<UserEntity> userEntityList;

    public AllUserAdapter(Context context, List<UserEntity> userEntityList) {
        this.context = context;
        this.userEntityList = userEntityList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false));
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        UserEntity userEntity = userEntityList.get(position);
        holder.txtUserId.setText(userEntity.getUserId());
        holder.txtUserName.setText(userEntity.getFname() + " " + userEntity.getLname());
    }

    @Override
    public int getItemCount() {
        return userEntityList == null ? 0 : userEntityList.size();
    }

    /**
     * Sets the new and changed arraylist as data source to the list
     * @param userEntityList
     */
    public void addItems(List<UserEntity> userEntityList) {
        this.userEntityList = userEntityList;
        this.notifyDataSetChanged();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        TextView txtUserId, txtUserName;
        public UserViewHolder(View itemView) {
            super(itemView);
            txtUserId = itemView.findViewById(R.id.txtUserId);
            txtUserName = itemView.findViewById(R.id.txtUserName);
        }
    }
}