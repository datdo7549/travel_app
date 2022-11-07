package com.example.travel_app.feature.groups.adapter.view_holder;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.travel_app.core.listeners.ItemFriendClickListener;
import com.example.travel_app.core.platform.BaseViewHolder;
import com.example.travel_app.databinding.ItemFriendLayoutBinding;
import com.example.travel_app.feature.groups.model.Friend;

public class TeammateViewHolder extends BaseViewHolder<ItemFriendLayoutBinding, Friend> {
    public TeammateViewHolder(@NonNull ItemFriendLayoutBinding itemView, ItemFriendClickListener itemFriendClickListener) {
        super(itemView);
        this.itemFriendClickListener = itemFriendClickListener;
    }
    private ItemFriendClickListener itemFriendClickListener;
    @Override
    public void bindData(Friend friend) {
        Glide.with(itemView)
                .load(friend.getAvatar())
                .circleCrop()
                .into(viewBinding.imgUserAvatar);

        viewBinding.textViewFriendName.setText(friend.getName());

        viewBinding.getRoot().setOnClickListener(v -> {
            itemFriendClickListener.onItemClick(friend.getUuid());
        });
    }
}
