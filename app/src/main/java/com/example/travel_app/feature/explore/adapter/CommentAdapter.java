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
import com.example.travel_app.databinding.ItemCommentLayoutBinding;
import com.example.travel_app.feature.model.CommentModel;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private ArrayList<CommentModel> comments;
    private Context context;

    public CommentAdapter(ArrayList<CommentModel> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder = new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_layout, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(holder.itemView)
                .load("https://images.unsplash.com/photo-1668182618034-a67cba9a9fc1?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=715&q=80")
                .circleCrop()
                .into(holder.avatar);

        holder.content.setText(comments.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView avatar;
        private TextView content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.img_user_avatar);
            content = itemView.findViewById(R.id.text_view_comment);
        }
    }
}
