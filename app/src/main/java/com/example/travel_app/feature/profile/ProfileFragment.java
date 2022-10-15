package com.example.travel_app.feature.profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.travel_app.R;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentProfileBinding;
import com.example.travel_app.feature.login.fragment.LoginFragmentViewModel;
import com.example.travel_app.feature.profile.adapter.UserPostAdapter;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileFragmentViewModel> {
    private UserPostAdapter userPostAdapter;
    @Override
    public FragmentProfileBinding onCreateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentProfileBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initViewModel();
    }

    private void initView() {
        Glide.with(this)
                .load(getResources().getString(R.string.img_cover_sample))
                .centerCrop()
                .into(viewBinding.imgCover);

        Glide.with(this)
                .load(getResources().getString(R.string.img_avatar_sample))
                .circleCrop()
                .into(viewBinding.imgAvatar);

        userPostAdapter = new UserPostAdapter();
        viewBinding.recyclerViewUserPosts.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        viewBinding.recyclerViewUserPosts.setAdapter(userPostAdapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ProfileFragmentViewModel.class);
        viewModel.userPosts.observe(getViewLifecycleOwner(), userPosts -> userPostAdapter.submitList(userPosts));
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}