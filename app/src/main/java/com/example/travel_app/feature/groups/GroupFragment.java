package com.example.travel_app.feature.groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentGroupBinding;
import com.example.travel_app.feature.groups.adapter.TeammateAdapter;

import dagger.hilt.android.AndroidEntryPoint;

public class GroupFragment extends BaseFragment<FragmentGroupBinding, GroupFragmentViewModel> {
    private TeammateAdapter teammateAdapter;

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
        teammateAdapter = new TeammateAdapter(null);
        viewBinding.rvListTeammate.setAdapter(teammateAdapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(GroupFragmentViewModel.class);
        viewModel.teammate.observe(getViewLifecycleOwner(), teammates -> teammateAdapter.submitList(teammates));
    }
}