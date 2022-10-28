package com.example.travel_app.feature.explore.view_holder;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.travel_app.core.platform.BaseViewHolder;
import com.example.travel_app.databinding.ItemCommentLayoutBinding;
import com.example.travel_app.feature.explore.model.Comment;

public class CommentViewHolder extends BaseViewHolder<ItemCommentLayoutBinding, Comment> {
    public CommentViewHolder(@NonNull ItemCommentLayoutBinding itemView) {
        super(itemView);
    }

    @Override
    public void bindData(Comment comment) {
        Glide.with(itemView)
                .load(comment.getUser_avatar())
                .circleCrop()
                .into(viewBinding.imgUserAvatar);

        viewBinding.textViewComment.setText(comment.getComment());
    }
}
