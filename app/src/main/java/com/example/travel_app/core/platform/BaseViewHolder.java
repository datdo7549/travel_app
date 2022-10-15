package com.example.travel_app.core.platform;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public abstract class BaseViewHolder<VB extends ViewBinding, Item> extends RecyclerView.ViewHolder {
    public VB viewBinding;

    public BaseViewHolder(@NonNull VB itemView) {
        super(itemView.getRoot());
        this.viewBinding = itemView;
    }

    public abstract void bindData(Item item);

    public void bind(Item item) {
        bindData(item);
    }
}
