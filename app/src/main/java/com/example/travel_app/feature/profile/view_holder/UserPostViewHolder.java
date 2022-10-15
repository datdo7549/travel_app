package com.example.travel_app.feature.profile.view_holder;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.travel_app.core.platform.BaseViewHolder;
import com.example.travel_app.databinding.ItemUserPostLayoutBinding;
import com.example.travel_app.feature.profile.model.UserPost;

public class UserPostViewHolder extends BaseViewHolder<ItemUserPostLayoutBinding, UserPost> {

    public UserPostViewHolder(@NonNull ItemUserPostLayoutBinding itemView) {
        super(itemView);
    }

    @Override
    public void bindData(UserPost userPost) {
        Glide.with(viewBinding.getRoot())
                .load(userPost.getPostCover())
                .centerCrop()
                .into(viewBinding.userPostImgCover);
    }
}
