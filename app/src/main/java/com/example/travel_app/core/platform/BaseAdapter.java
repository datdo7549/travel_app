package com.example.travel_app.core.platform;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;

public abstract class BaseAdapter<Item, VB extends ViewBinding, VH extends BaseViewHolder<VB, Item>> extends RecyclerView.Adapter<VH> {
    public VB viewBinding;
    public VH viewHolder;

    public ArrayList<Item> list = new ArrayList<>();

    public abstract VH createViewHolder(@NonNull ViewGroup parent);

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewHolder = createViewHolder(parent);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        viewHolder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void submitList(ArrayList<Item> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
