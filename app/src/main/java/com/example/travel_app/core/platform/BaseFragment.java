package com.example.travel_app.core.platform;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<VB extends ViewBinding, VM extends ViewModel> extends Fragment {
    public VB viewBinding;

    public VM viewModel;

    public abstract VB onCreateViewBinding(LayoutInflater inflater, ViewGroup container);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBinding = onCreateViewBinding(inflater, container);
        return viewBinding.getRoot();
    }
}
