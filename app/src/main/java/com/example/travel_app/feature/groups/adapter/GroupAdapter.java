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
import com.example.travel_app.core.listeners.ItemGroupClickListener;
import com.example.travel_app.feature.model.GroupModel;

import java.util.ArrayList;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private ArrayList<GroupModel> groups;

    private ItemGroupClickListener listener;

    public GroupAdapter(ArrayList<GroupModel> groups, ItemGroupClickListener listener) {
        this.groups = groups;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load("https://images.unsplash.com/photo-1531844251246-9a1bfaae09fc?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTB8fGdyb3VwfGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60")
                .circleCrop()
                .into(holder.avatar);

        holder.name.setText(groups.get(position).getGroupName());

        holder.status.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(groups.get(position).getGroupID(), groups.get(position).getGroupName());
        });
    }

    @Override
    public int getItemCount() {
        return groups.size();
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
