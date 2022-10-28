package com.example.travel_app.feature.explore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.travel_app.R;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.core.views.CommentBottomSheetDialog;
import com.example.travel_app.databinding.FragmentPostDetailBinding;
import com.example.travel_app.feature.explore.model.Comment;

import java.util.ArrayList;

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
        viewBinding.btnBack.setOnClickListener(v -> navController.navigateUp());
        viewBinding.textViewCommentsCount.setOnClickListener(v -> {
            ArrayList<Comment> arrayList = new ArrayList<>();
            arrayList.add(new Comment("1", "22", getResources().getString(R.string.img_avatar_sample), "Comment 1", 121323L));
            arrayList.add(new Comment("2", "22", getResources().getString(R.string.img_avatar_sample), "Comment 2", 121323L));
            arrayList.add(new Comment("3", "22", getResources().getString(R.string.img_avatar_sample), "Comment 3", 121323L));

            CommentBottomSheetDialog commentBottomSheetDialog = new CommentBottomSheetDialog(arrayList);
            commentBottomSheetDialog.show(getChildFragmentManager(), CommentBottomSheetDialog.class.getSimpleName());
        });
    }
}