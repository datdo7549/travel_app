package com.example.travel_app.feature.explore.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.travel_app.core.platform.BaseAdapter;
import com.example.travel_app.databinding.ItemCommentLayoutBinding;
import com.example.travel_app.feature.explore.model.Comment;
import com.example.travel_app.feature.explore.view_holder.CommentViewHolder;

public class CommentAdapter extends BaseAdapter<Comment, ItemCommentLayoutBinding, CommentViewHolder> {
    @Override
    public CommentViewHolder createViewHolder(@NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        return new CommentViewHolder(ItemCommentLayoutBinding.inflate(layoutInflater, parent, false));
    }
}
