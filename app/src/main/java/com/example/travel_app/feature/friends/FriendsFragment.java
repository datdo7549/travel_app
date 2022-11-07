package com.example.travel_app.feature.friends;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travel_app.R;
import com.example.travel_app.core.listeners.ItemFriendClickListener;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentFriendsBinding;
import com.example.travel_app.feature.groups.GroupFragmentViewModel;
import com.example.travel_app.feature.groups.adapter.TeammateAdapter;

public class FriendsFragment extends BaseFragment<FragmentFriendsBinding, FriendsFragmentViewModel> {
    private TeammateAdapter teammateAdapter;
    private NavController controller;
    @Override
    public FragmentFriendsBinding onCreateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentFriendsBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller = Navigation.findNavController(view);
        initView();
        initViewModel();
    }

    private void initView() {
        teammateAdapter = new TeammateAdapter(new ItemFriendClickListener() {
            @Override
            public void onItemClick(String id) {
                controller.navigate(R.id.action_friendsFragment_to_chatFragment);
            }
        });
        viewBinding.rvListTeammate.setAdapter(teammateAdapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(FriendsFragmentViewModel.class);
        viewModel.teammate.observe(getViewLifecycleOwner(), teammates -> teammateAdapter.submitList(teammates));
    }
}