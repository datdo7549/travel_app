package com.example.travel_app.feature.explore.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.travel_app.core.listeners.ItemPostClickListener;
import com.example.travel_app.core.platform.BaseAdapter;
import com.example.travel_app.databinding.ItemPostLayoutBinding;
import com.example.travel_app.feature.explore.view_holder.PostViewHolder;
import com.example.travel_app.feature.profile.model.UserPost;

public class PostAdapter extends BaseAdapter<UserPost, ItemPostLayoutBinding, PostViewHolder> {
    private ItemPostClickListener listener;
    public PostAdapter(ItemPostClickListener itemPostClickListener) {
        this.listener = itemPostClickListener;
    }
    @Override
    public PostViewHolder createViewHolder(@NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new PostViewHolder(this.listener, ItemPostLayoutBinding.inflate(layoutInflater, parent, false));
    }
}
