package com.example.travel_app.feature.explore.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travel_app.R;
import com.example.travel_app.core.listeners.ItemPostClickListener;
import com.example.travel_app.databinding.ItemPostLayoutBinding;
import com.example.travel_app.feature.model.UserPost;

import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private ArrayList<UserPost> posts;
    private String userId;

    private ItemPostClickListener listener;

    boolean isLiked = false;


    public PostAdapter(ArrayList<UserPost> posts, ItemPostClickListener listener, String userId) {
        this.posts = posts;
        this.listener = listener;
        this.userId = userId;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewHolder viewHolder = new ViewHolder(ItemPostLayoutBinding.inflate(inflater, parent, false).getRoot());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.actor.setText(posts.get(position).getActorName());

        Glide.with(holder.itemView)
                .load("https://images.unsplash.com/photo-1668194645745-45709d2272d1?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80")
                .centerCrop()
                .into(holder.cover);

        holder.address.setText(posts.get(position).getTitle());
        holder.description.setText(posts.get(position).getDescription());

        try {
            isLiked = false;
            for (String userLike: posts.get(position).getLikes()) {
                if (Objects.equals(userLike, userId)) {
                    isLiked = true;
                    break;
                }
            }
        } catch (NullPointerException e) {
            isLiked = false;
        }

        if (isLiked) {
            holder.btnLike.setImageResource(R.drawable.ic_favorite_red);
        } else {
            holder.btnLike.setImageResource(R.drawable.ic_favorite_white);
        }

        holder.itemView.setOnClickListener(v -> {
            listener.onItemClick(posts.get(position).getId());
        });

        holder.btnLike.setOnClickListener(v -> {
            listener.onItemFavoriteClick(posts.get(position), isLiked);
        });
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView actor, address, description;
        AppCompatImageView cover, btnLike;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            actor = itemView.findViewById(R.id.text_view_post_actor);
            cover = itemView.findViewById(R.id.img_post_cover);
            address = itemView.findViewById(R.id.text_view_post_address);
            description = itemView.findViewById(R.id.text_view_post_description);
            btnLike = itemView.findViewById(R.id.btnLike);
        }
    }
}
