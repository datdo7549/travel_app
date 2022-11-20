package com.example.travel_app.feature.explore.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travel_app.R;
import com.example.travel_app.core.listeners.ItemLikeCommentClick;
import com.example.travel_app.feature.model.CommentModel;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private final ArrayList<CommentModel> comments;
    public CommentAdapter(ArrayList<CommentModel> comments) {
        this.comments = comments;
    }

    private ItemLikeCommentClick listener;

    public void setListener(ItemLikeCommentClick listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load("https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80")
                .circleCrop()
                .into(holder.avatar);

        holder.actorName.setText(comments.get(position).getUserName());
        holder.comment.setText(comments.get(position).getContent());

        holder.actorName.setOnClickListener(v -> {
            this.listener.onAvatarClick(comments.get(position).getUserId(), comments.get(position).getUserName());
        });

        holder.avatar.setOnClickListener(v -> {
            this.listener.onAvatarClick(comments.get(position).getUserId(), comments.get(position).getUserName());
        });
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final AppCompatImageView avatar;
        private final TextView actorName, comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            avatar = itemView.findViewById(R.id.img_user_avatar);
            actorName = itemView.findViewById(R.id.text_view_actor_name);
            comment = itemView.findViewById(R.id.text_view_comment);
        }
    }
}
