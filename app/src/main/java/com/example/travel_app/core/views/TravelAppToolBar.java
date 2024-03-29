package com.example.travel_app.core.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.travel_app.R;
import com.example.travel_app.core.listeners.BackButtonListener;
import com.example.travel_app.databinding.ToolBarLayoutBinding;

public class TravelAppToolBar extends ConstraintLayout {
    private final ToolBarLayoutBinding binding;
    private BackButtonListener listener;


    public TravelAppToolBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        binding = ToolBarLayoutBinding.inflate(LayoutInflater.from(context), this, false);
        initView(attrs);
    }

    public TravelAppToolBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        binding = ToolBarLayoutBinding.inflate(LayoutInflater.from(context), this, false);
        initView(attrs);
    }

    private void initView(AttributeSet attrs) {
        addView(binding.getRoot());

        TypedArray typedArray = this.getContext().obtainStyledAttributes(attrs, R.styleable.TravelAppToolBar);
        try {
            String title = typedArray.getString(R.styleable.TravelAppToolBar_tool_bar_title);
            boolean isVisibleButtonBack = typedArray.getBoolean(R.styleable.TravelAppToolBar_tool_bar_is_visible_button_back, false);
            if (isVisibleButtonBack) {
                binding.btnBack.setVisibility(INVISIBLE);
            }
            binding.toolBarTitle.setText(title);
        } finally {
            typedArray.recycle();
        }

        binding.btnBack.setOnClickListener(v -> listener.onBack());
    }

    public void setButtonBackListener(BackButtonListener listener) {
        this.listener = listener;
    }

    public void setTitleToolBar(String title) {
        binding.toolBarTitle.setText(title);
    }

    public void visibilityBtnAddGroup(boolean visibility) {
        if (visibility) {
            binding.btnAddToGroup.setVisibility(VISIBLE);
        } else {
            binding.btnAddToGroup.setVisibility(GONE);
        }
    }

    public AppCompatImageView getBtnAddGroup() {
        return binding.btnAddToGroup;
    }
}
