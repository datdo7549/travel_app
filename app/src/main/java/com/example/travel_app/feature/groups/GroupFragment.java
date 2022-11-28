package com.example.travel_app.feature.groups;

import static com.example.travel_app.feature.explore.ExploreFragment.userProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.travel_app.R;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentGroupBinding;
import com.example.travel_app.feature.groups.adapter.GroupAdapter;
import com.example.travel_app.feature.model.GroupModel;

import java.util.ArrayList;

public class GroupFragment extends BaseFragment<FragmentGroupBinding, GroupFragmentViewModel> {
    private GroupAdapter groupAdapter;
    private ArrayList<GroupModel> groups = new ArrayList<>();

    @Override
    public FragmentGroupBinding onCreateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentGroupBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initViewModel();
    }

    private void initView(View view) {
        try {
            if (userProfile.myCreatedGroup != null && !userProfile.myCreatedGroup.equals("")) {
                viewBinding.btnCreateGroup.setVisibility(View.GONE);
            } else {
                viewBinding.btnCreateGroup.setVisibility(View.VISIBLE);
                viewBinding.btnCreateGroup.setOnClickListener(v -> {
                    viewModel.createGroup();
                });
            }

            groupAdapter = new GroupAdapter(groups, (id, name) -> {
                GroupFragmentDirections.ActionGroupFragmentToGroupDetailFragment action = GroupFragmentDirections.actionGroupFragmentToGroupDetailFragment();
                action.setGroupId(id);
                action.setGroupName(name);
                Navigation.findNavController(view).navigate(action);
            });
            viewBinding.rvListGroup.setAdapter(groupAdapter);
        } catch (Exception e) {}

    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(GroupFragmentViewModel.class);

        viewModel.createGroupResponse.observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess) {
                viewBinding.btnCreateGroup.setVisibility(View.GONE);
            }
        });

        viewModel.groups.observe(getViewLifecycleOwner(), groupsResponse -> {
            groups.clear();
            groups.addAll(groupsResponse);
            groupAdapter.notifyDataSetChanged();
        });
    }
}