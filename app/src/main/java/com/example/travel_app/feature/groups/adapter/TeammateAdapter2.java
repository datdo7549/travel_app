package com.example.travel_app.feature.groups.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travel_app.R;
import com.example.travel_app.core.listeners.ItemFriendClickListener;
import com.example.travel_app.feature.model.UserProfile;
import com.example.travel_app.feature.model.UserProfileLite;

import java.util.ArrayList;

public class TeammateAdapter2 extends RecyclerView.Adapter<TeammateAdapter2.ViewHolder> {
    private ArrayList<UserProfileLite> members = new ArrayList<>();

    public TeammateAdapter2(ArrayList<UserProfileLite> members) {
        this.members = members;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load("https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80")
                .circleCrop()
                .into(holder.avatar);

        holder.name.setText(members.get(position).name);
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView avatar, status;
        private TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.img_user_avatar);
            status = itemView.findViewById(R.id.img_status);
            name = itemView.findViewById(R.id.text_view_friend_name);
        }
    }
}
