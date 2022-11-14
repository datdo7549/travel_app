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

import com.bumptech.glide.Glide;
import com.example.travel_app.R;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.core.views.CommentBottomSheetDialog;
import com.example.travel_app.databinding.FragmentPostDetailBinding;
import com.example.travel_app.feature.model.CommentModel;
import com.example.travel_app.feature.model.UserPost;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class PostDetailFragment extends BaseFragment<FragmentPostDetailBinding, PostDetailFragmentViewModel> {
    private String postId;
    private NavController navController;
    private static final String img_user_post_img_cover_sample = "https://images.unsplash.com/photo-1665501340269-5ced49f91a92?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1074&q=80";

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
        navController = Navigation.findNavController(view);
        viewBinding.btnCreateComment.setOnClickListener(v -> {
            viewModel.createComment(viewBinding.editCreateComment.getText().toString(), viewModel.postDetail.getValue());
        });
        initViewModel();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(PostDetailFragmentViewModel.class);

        viewModel.getPostDetail(postId);
        viewModel.postDetail.observe(getViewLifecycleOwner(), postDetail -> {
            Glide.with(getView())
                    .load(img_user_post_img_cover_sample)
                    .centerCrop()
                    .into(viewBinding.imgPostCover);
            viewBinding.btnBack.setOnClickListener(v -> navController.navigateUp());
            if (postDetail.getComments() == null) {
                viewBinding.textViewCommentsCount.setText("0 comment");
                viewBinding.textViewCommentsCount.setEnabled(false);
            } else {
                viewBinding.textViewCommentsCount.setText(postDetail.getComments().size() + " comment");
                viewBinding.textViewCommentsCount.setEnabled(true);
            }
            viewBinding.textViewCommentsCount.setOnClickListener(v -> {
                CommentBottomSheetDialog commentBottomSheetDialog = new CommentBottomSheetDialog(postDetail.getComments());
                commentBottomSheetDialog.show(getChildFragmentManager(), CommentBottomSheetDialog.class.getSimpleName());
            });


            viewBinding.textViewPostLocation.setText(postDetail.getTitle());
            viewBinding.textViewPostDescription.setText(postDetail.getDescription());
            viewBinding.textViewPostLocationMap.setText(postDetail.getLocation());

            String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date(postDetail.getCreateDate()));
            viewBinding.textViewCreateDate.setText(dateString);
        });
    }
}