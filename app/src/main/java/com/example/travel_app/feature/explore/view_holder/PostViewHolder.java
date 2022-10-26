package com.example.travel_app.feature.explore.view_holder;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.travel_app.core.listeners.ItemPostClickListener;
import com.example.travel_app.core.platform.BaseViewHolder;
import com.example.travel_app.databinding.ItemPostLayoutBinding;
import com.example.travel_app.feature.profile.model.UserPost;

public class PostViewHolder extends BaseViewHolder<ItemPostLayoutBinding, UserPost> {
    private ItemPostClickListener listener;
    public PostViewHolder(ItemPostClickListener itemPostClickListener,  @NonNull ItemPostLayoutBinding itemView) {
        super(itemView);
        this.listener = itemPostClickListener;
    }

    @Override
    public void bindData(UserPost userPost) {
        viewBinding.textViewPostActor.setText(userPost.getActor());
        Glide.with(itemView)
                .load(userPost.getPostCover())
                .centerCrop()
                .into(viewBinding.imgPostCover);
        viewBinding.textViewPostAddress.setText(userPost.getPostTitle());
        viewBinding.textViewPostDescription.setText(userPost.getDescription());
        viewBinding.getRoot().setOnClickListener(v -> this.listener.onItemClick(userPost.getPostId()));
    }
}
