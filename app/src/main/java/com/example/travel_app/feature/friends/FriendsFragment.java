package com.example.travel_app.feature.friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.travel_app.R;
import com.example.travel_app.core.listeners.ItemFriendClickListener;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentFriendsBinding;
import com.example.travel_app.feature.explore.ExploreFragmentDirections;
import com.example.travel_app.feature.groups.adapter.TeammateAdapter;
import com.example.travel_app.feature.model.UserProfile;

import java.util.ArrayList;

public class FriendsFragment extends BaseFragment<FragmentFriendsBinding, FriendsFragmentViewModel> {
    private TeammateAdapter teammateAdapter;
    private NavController controller;

    private ArrayList<UserProfile> friends = new ArrayList<>();

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
        teammateAdapter = new TeammateAdapter(friends, id -> {
            FriendsFragmentDirections.ActionFriendsFragmentToChatFragment action = FriendsFragmentDirections.actionFriendsFragmentToChatFragment();
            action.setReceiveId(id);
            controller.navigate(action);
        });
        viewBinding.rvListTeammate.setAdapter(teammateAdapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(FriendsFragmentViewModel.class);
        viewModel.getFriends();
        viewModel.getStatusOfFriendList();
        viewModel.friends.observe(getViewLifecycleOwner(), friendList -> {
            friends.clear();
            if (viewModel.onlineUsers.getValue() != null && viewModel.onlineUsers.getValue().size() > 0) {
                for (UserProfile userProfile : friendList) {
                    for (String onlineId : viewModel.onlineUsers.getValue()) {
                        if (userProfile.uuid.equals(onlineId)) {
                            userProfile.isOnline = true;
                            break;
                        }
                    }
                }
            }
            friends.addAll(friendList);

            teammateAdapter.notifyDataSetChanged();
        });

        viewModel.onlineUsers.observe(getViewLifecycleOwner(), onlineList -> {
            try {
                friends.clear();
                ArrayList<UserProfile> friendList = viewModel.friends.getValue();
                if (viewModel.friends.getValue() != null && viewModel.friends.getValue().size() > 0) {
                    for (UserProfile userProfile : friendList) {
                        for (String onlineId : onlineList) {
                            if (userProfile.uuid.equals(onlineId)) {
                                userProfile.isOnline = true;
                                break;
                            } else {
                                userProfile.isOnline = false;
                            }
                        }
                    }
                }
                if (friendList != null) {
                    friends.addAll(friendList);
                }

                teammateAdapter.notifyDataSetChanged();
            } catch (Exception e) {}

        });
    }
}