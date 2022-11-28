package com.example.travel_app.feature.groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.travel_app.TrackingActivity;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentGroupDetailBinding;
import com.example.travel_app.feature.groups.adapter.TeammateAdapter2;
import com.example.travel_app.feature.model.UserProfileLite;

import java.util.ArrayList;

public class GroupDetailFragment extends BaseFragment<FragmentGroupDetailBinding, GroupDetailVIewModel> {
    private TeammateAdapter2 teammateAdapter2;
    private ArrayList<UserProfileLite> members = new ArrayList<>();

    private String id, name;

    public static ArrayList<String> listMemberId = new ArrayList<>();
    @Override
    public FragmentGroupDetailBinding onCreateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentGroupDetailBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        id = GroupDetailFragmentArgs.fromBundle(getArguments()).getGroupId();
        name = GroupDetailFragmentArgs.fromBundle(getArguments()).getGroupName();
        listMemberId.clear();
        initView();
        initViewModel();

    }

    private void initView() {
        teammateAdapter2 = new TeammateAdapter2(members);
        viewBinding.rvListMember.setAdapter(teammateAdapter2);

        viewBinding.btnTracking.setOnClickListener(v -> {
            TrackingActivity.start(requireContext());
        });
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(GroupDetailVIewModel.class);

        viewModel.getListMember(id);
        viewModel.member.observe(getViewLifecycleOwner(), membersResponse -> {
            members.clear();
            members.addAll(membersResponse);
            teammateAdapter2.notifyDataSetChanged();
            listMemberId.clear();
            for (UserProfileLite userProfileLite : members) {
                listMemberId.add(userProfileLite.uuid);
            }
        });
    }

}

