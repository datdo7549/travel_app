package com.example.travel_app.feature.groups.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.travel_app.core.listeners.ItemFriendClickListener;
import com.example.travel_app.core.platform.BaseAdapter;
import com.example.travel_app.databinding.ItemFriendLayoutBinding;
import com.example.travel_app.feature.groups.adapter.view_holder.TeammateViewHolder;
import com.example.travel_app.feature.groups.model.Friend;

public class TeammateAdapter extends BaseAdapter<Friend, ItemFriendLayoutBinding, TeammateViewHolder> {
    private ItemFriendClickListener itemFriendClickListener;
    public TeammateAdapter(ItemFriendClickListener itemFriendClickListener) {
        this.itemFriendClickListener = itemFriendClickListener;
    }
    @Override
    public TeammateViewHolder createViewHolder(@NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TeammateViewHolder(ItemFriendLayoutBinding.inflate(inflater, parent, false), itemFriendClickListener);
    }
}
