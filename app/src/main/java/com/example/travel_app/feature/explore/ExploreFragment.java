package com.example.travel_app.feature.explore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.travel_app.core.listeners.ItemPostClickListener;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentExploreBinding;
import com.example.travel_app.feature.explore.adapter.PostAdapter;
import com.example.travel_app.feature.profile.ProfileFragmentViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExploreFragment extends BaseFragment<FragmentExploreBinding, ExploreFragmentViewModel> {
    private PostAdapter postAdapter;
    private NavController navController;
    @Override
    public FragmentExploreBinding onCreateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentExploreBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        initView();
        initViewModel();
    }

    private void initView() {
        postAdapter = new PostAdapter(postId -> {
            ExploreFragmentDirections.ActionExploreFragmentToPostDetailFragment action = ExploreFragmentDirections.actionExploreFragmentToPostDetailFragment();
            action.setPostId(postId);
            navController.navigate(action);
        });
        viewBinding.recyclerViewPosts.setAdapter(postAdapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ExploreFragmentViewModel.class);
        viewModel.getUserPost();

        viewModel.posts.observe(getViewLifecycleOwner(), posts -> {
            postAdapter.submitList(posts);
        });
    }
}