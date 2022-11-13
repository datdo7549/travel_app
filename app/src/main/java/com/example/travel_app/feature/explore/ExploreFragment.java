package com.example.travel_app.feature.explore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.travel_app.CreatePostActivity;
import com.example.travel_app.core.listeners.ItemPostClickListener;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentExploreBinding;
import com.example.travel_app.feature.explore.adapter.PostAdapter;
import com.example.travel_app.feature.model.UserPost;
import com.example.travel_app.feature.model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExploreFragment extends BaseFragment<FragmentExploreBinding, ExploreFragmentViewModel> {
    private PostAdapter postAdapter;
    private NavController navController;

    private ArrayList<UserPost> posts = new ArrayList<>();

    public static UserProfile userProfile;

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

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initView() {
        postAdapter = new PostAdapter(posts, requireContext(), new ItemPostClickListener() {
            @Override
            public void onItemClick(String postId) {
                ExploreFragmentDirections.ActionExploreFragmentToPostDetailFragment action = ExploreFragmentDirections.actionExploreFragmentToPostDetailFragment();
                action.setPostId(postId);
                navController.navigate(action);
            }

            @Override
            public void onItemFavoriteClick(UserPost post, boolean isLiked) {
                if (isLiked) {

                } else {
                    try {
                        ArrayList<String> likedList;
                        likedList = post.getLikes();
                        likedList.add(FirebaseAuth.getInstance().getUid());
                        post.setLikes(likedList);
                    } catch (NullPointerException e) {
                        ArrayList<String> likedList = new ArrayList<>();
                        likedList.add(FirebaseAuth.getInstance().getUid());
                        post.setLikes(likedList);
                    }

                    viewModel.likePost(post);
                }
            }
        }, FirebaseAuth.getInstance().getUid());
        viewBinding.recyclerViewPosts.setAdapter(postAdapter);

        viewBinding.btnCreatePost.setOnClickListener(v -> CreatePostActivity.start(requireContext()));
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ExploreFragmentViewModel.class);
        viewModel.getAllUserPost();
        viewModel.getUserProfile();

        viewModel.posts.observe(getViewLifecycleOwner(), postList -> {
            posts.clear();
            posts.addAll(postList);
            postAdapter.notifyDataSetChanged();
        });

        viewModel.userProfile.observe(getViewLifecycleOwner(), profile -> userProfile = profile);

        viewModel.likePostResponse.observe(getViewLifecycleOwner(), result -> {
            result.setLike(true);
            for (UserPost post : posts) {
                if (Objects.equals(post.getId(), result.getId())) {
                    post.setLike(true);
                }
            }
            postAdapter.notifyDataSetChanged();
        });
    }
}