package com.example.travel_app.feature.explore;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travel_app.R;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentPostDetailBinding;

public class PostDetailFragment extends BaseFragment<FragmentPostDetailBinding, PostDetailFragmentViewModel> {
    private String postId;
    @Override
    public FragmentPostDetailBinding onCreateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentPostDetailBinding.inflate(inflater, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postId = PostDetailFragmentArgs.fromBundle(getArguments()).getPostId();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}