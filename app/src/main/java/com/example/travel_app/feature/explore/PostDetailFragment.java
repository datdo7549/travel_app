package com.example.travel_app.feature.explore;

import static com.example.travel_app.feature.explore.ExploreFragment.userProfile;

import android.annotation.SuppressLint;
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

import com.bumptech.glide.Glide;
import com.example.travel_app.R;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.core.views.CommentBottomSheetDialog;
import com.example.travel_app.core.views.LikedBottomSheetDialog;
import com.example.travel_app.databinding.FragmentPostDetailBinding;
import com.example.travel_app.feature.model.CommentModel;
import com.example.travel_app.feature.model.UserPost;
import com.example.travel_app.feature.model.UserProfile;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
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
        initView();
        initViewModel();
    }

    private void initView() {
        viewBinding.btnPostComment.setOnClickListener(v -> {
            if (viewBinding.editCreateComment.getText().toString().length() > 0) {
                //Post comment here
                String commentId = UUID.randomUUID().toString();
                String userId = userProfile.uuid;
                String userName = userProfile.name;
                String content = viewBinding.editCreateComment.getText().toString();
                CommentModel commentModel = new CommentModel(commentId, userId, userName, content, System.currentTimeMillis());
                ArrayList<CommentModel> newCommentList = new ArrayList<>();

                if (Objects.requireNonNull(viewModel.postDetail.getValue()).getComments() != null && viewModel.postDetail.getValue().getComments().size() > 0) {
                    newCommentList.addAll(viewModel.postDetail.getValue().getComments());
                }
                newCommentList.add(commentModel);
                viewModel.postComment(newCommentList, postId);
            }
        });

        viewBinding.textViewLikeCount.setOnClickListener(v -> {
            LikedBottomSheetDialog likedBottomSheetDialog = new LikedBottomSheetDialog(viewModel.userLikedList.getValue());
            likedBottomSheetDialog.show(getChildFragmentManager(), LikedBottomSheetDialog.class.getSimpleName());
        });

        viewBinding.textViewCommentsCount.setOnClickListener(v -> {
            CommentBottomSheetDialog commentBottomSheetDialog = new CommentBottomSheetDialog(Objects.requireNonNull(viewModel.postDetail.getValue()).getComments());
            commentBottomSheetDialog.show(getChildFragmentManager(), CommentBottomSheetDialog.class.getSimpleName());
        });

        viewBinding.btnLike.setOnClickListener(v -> {
            ArrayList<String> likedList = new ArrayList<>();
            UserPost post = viewModel.postDetail.getValue();
            assert post != null;
            if (isLikedPost) {
                //Do unlike post
                likedList = post.getLikes();
                likedList.remove(userProfile.uuid);
            } else {
                //Do like post
                if (post.getLikes() != null && post.getLikes().size() > 0) {
                    likedList = viewModel.postDetail.getValue().getLikes();
                }
                likedList.add(FirebaseAuth.getInstance().getUid());
            }
            post.setLikes(likedList);
            viewModel.likePost(post);
        });
    }

    private Boolean isLikedPost = false;

    @SuppressLint("SetTextI18n")
    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(PostDetailFragmentViewModel.class);
        viewModel.getPostDetail(postId);

        viewModel.postDetail.observe(getViewLifecycleOwner(), postDetail -> {
            Glide.with(requireView())
                    .load(img_user_post_img_cover_sample)
                    .centerCrop()
                    .into(viewBinding.imgPostCover);
            viewBinding.btnBack.setOnClickListener(v -> navController.navigateUp());

            if (postDetail.getLikes() != null && postDetail.getLikes().size() > 0) {
                viewBinding.textViewLikeCount.setText(postDetail.getLikes().size() + " likes");
                viewBinding.textViewLikeCount.setEnabled(true);
            } else {
                viewBinding.textViewLikeCount.setText("0 likes");
                viewBinding.textViewLikeCount.setEnabled(false);
            }


            if (postDetail.getComments() != null && postDetail.getComments().size() > 0) {
                viewBinding.textViewCommentsCount.setText(postDetail.getComments().size() + " comments");
                viewBinding.textViewCommentsCount.setEnabled(true);
            } else {
                viewBinding.textViewCommentsCount.setText("0 comments");
                viewBinding.textViewCommentsCount.setEnabled(false);
            }

            viewBinding.textViewPostLocation.setText(postDetail.getTitle());
            viewBinding.textViewPostDescription.setText(postDetail.getDescription());
            viewBinding.textViewPostLocationMap.setText(postDetail.getLocation());

            @SuppressLint("SimpleDateFormat") String dateString = new SimpleDateFormat("dd/MM/yyyy").format(new Date(postDetail.getCreateDate()));
            viewBinding.textViewCreateDate.setText(dateString);

            if (postDetail.getLikes() != null) {
                viewModel.getListUserLiked(postDetail.getLikes());
                isLikedPost = false;
                for (String id : postDetail.getLikes()) {
                    if (id.equals(userProfile.uuid)) {
                        isLikedPost = true;
                        break;
                    }
                }
            } else {
                isLikedPost = false;
            }

            if (isLikedPost) {
                viewBinding.btnLike.setImageResource(R.drawable.ic_favorite_red);
            } else {
                viewBinding.btnLike.setImageResource(R.drawable.ic_favorite_grey);
            }
        });

        viewModel.postCommentResponse.observe(getViewLifecycleOwner(), response -> {
            viewBinding.editCreateComment.clearFocus();
            viewBinding.editCreateComment.setText("");
        });
    }
}