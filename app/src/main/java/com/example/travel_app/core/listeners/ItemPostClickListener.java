package com.example.travel_app.core.listeners;

import com.example.travel_app.feature.model.UserPost;

public interface ItemPostClickListener {
    void onItemClick(String postId);

    void onItemFavoriteClick(UserPost post, boolean isLike);
}
