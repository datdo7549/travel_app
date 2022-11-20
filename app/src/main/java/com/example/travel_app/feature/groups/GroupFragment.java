package com.example.travel_app.feature.groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentGroupBinding;

public class GroupFragment extends BaseFragment<FragmentGroupBinding, GroupFragmentViewModel> {

    @Override
    public FragmentGroupBinding onCreateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentGroupBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewModel();
    }

    private void initView() {

    }

    private void initViewModel() {

    }
}