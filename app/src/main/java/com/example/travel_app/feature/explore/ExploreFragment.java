package com.example.travel_app.feature.explore;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
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
import com.example.travel_app.feature.model.MyLatLong;
import com.example.travel_app.feature.model.UserPost;
import com.example.travel_app.feature.model.UserProfile;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExploreFragment extends BaseFragment<FragmentExploreBinding, ExploreFragmentViewModel> {
    private PostAdapter postAdapter;
    private NavController navController;

    private ArrayList<UserPost> posts = new ArrayList<>();

    public static UserProfile userProfile;

    private ProgressDialog dialog;

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
        dialog = new ProgressDialog(requireContext());
        dialog.setTitle("Loading");
        dialog.setMessage("Please wait");
        dialog.setCancelable(false);
        dialog.show();
        postAdapter = new PostAdapter(posts, new ItemPostClickListener() {
            @Override
            public void onItemClick(String postId) {
                //Navigate to  Post detail
                ExploreFragmentDirections.ActionExploreFragmentToPostDetailFragment action = ExploreFragmentDirections.actionExploreFragmentToPostDetailFragment();
                action.setPostId(postId);
                navController.navigate(action);
            }

            @Override
            public void onItemFavoriteClick(UserPost post, boolean isLiked) {
                ArrayList<String> likedList = new ArrayList<>();
                if (isLiked) {
                    //Do unlike post
                    likedList = post.getLikes();
                    likedList.remove(userProfile.uuid);
                } else {
                    //Do like post
                    if (post.getLikes() != null && post.getLikes().size() > 0) {
                        likedList = post.getLikes();
                    }
                    likedList.add(FirebaseAuth.getInstance().getUid());
                    post.setLikes(likedList);
                }
                viewModel.likePost(post);
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
            viewModel.setIsHavePost();
            posts.clear();
            posts.addAll(postList);
            postAdapter.notifyDataSetChanged();
        });

        viewModel.userProfile.observe(getViewLifecycleOwner(), profile -> {
            viewModel.setIsHaveUserProfile();
            userProfile = profile;
            viewModel.setOnline();
        });

        viewModel.isHide.observe(getViewLifecycleOwner(), isHide -> {
            if (isHide) {
                getDeviceLocation();
                dialog.dismiss();
            }
        });

    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            Task<Location> locationResult = LocationServices.getFusedLocationProviderClient(requireContext()).getLastLocation();
            locationResult.addOnCompleteListener(requireActivity(), task -> {
                if (task.isSuccessful()) {
                    // Set the map's camera position to the current location of the device.
                    if (task.getResult() != null) {
                        MyLatLong myLatLong = new MyLatLong();
                        myLatLong.uuid = userProfile.uuid;
                        myLatLong.name = userProfile.name;
                        myLatLong.lat = task.getResult().getLatitude();
                        myLatLong.longitude = task.getResult().getLongitude();

                        FirebaseDatabase.getInstance().getReference("LatLong").child(userProfile.uuid).setValue(myLatLong);
                    }
                } else {
                    LatLng vietnam = new LatLng(14.0583, 108.2772);
                }
            });
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }
}