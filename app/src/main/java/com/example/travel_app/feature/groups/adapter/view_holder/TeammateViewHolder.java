package com.example.travel_app.feature.groups.adapter.view_holder;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.travel_app.core.platform.BaseViewHolder;
import com.example.travel_app.databinding.ItemFriendLayoutBinding;
import com.example.travel_app.feature.groups.model.Friend;

public class TeammateViewHolder extends BaseViewHolder<ItemFriendLayoutBinding, Friend> {
    public TeammateViewHolder(@NonNull ItemFriendLayoutBinding itemView) {
        super(itemView);
    }

    @Override
    public void bindData(Friend friend) {
        Glide.with(itemView)
                .load(friend.getAvatar())
                .circleCrop()
                .into(viewBinding.imgUserAvatar);

        viewBinding.textViewFriendName.setText(friend.getName());
    }
}
