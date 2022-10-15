package com.example.travel_app.feature.profile.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.travel_app.core.platform.BaseAdapter;
import com.example.travel_app.databinding.ItemUserPostLayoutBinding;
import com.example.travel_app.feature.profile.model.UserPost;
import com.example.travel_app.feature.profile.view_holder.UserPostViewHolder;

public class UserPostAdapter extends BaseAdapter<UserPost, ItemUserPostLayoutBinding, UserPostViewHolder> {
    @Override
    public UserPostViewHolder createViewHolder(@NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new UserPostViewHolder(ItemUserPostLayoutBinding.inflate(layoutInflater, parent, false));
    }
}
