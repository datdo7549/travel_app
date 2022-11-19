package com.example.travel_app.feature.explore;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travel_app.core.Const;
import com.example.travel_app.core.platform.BaseViewModel;
import com.example.travel_app.feature.model.CommentModel;
import com.example.travel_app.feature.model.UserPost;
import com.example.travel_app.feature.model.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class PostDetailFragmentViewModel extends BaseViewModel {
    private MutableLiveData<UserPost> _postDetail = new MutableLiveData<>();
    public LiveData<UserPost> postDetail = _postDetail;

    private MutableLiveData<Boolean> _postCommentResponse = new MutableLiveData<>();
    public LiveData<Boolean> postCommentResponse = _postCommentResponse;

    private MutableLiveData<ArrayList<UserProfile>> _userLikedList = new MutableLiveData<>();
    public LiveData<ArrayList<UserProfile>> userLikedList = _userLikedList;

    public PostDetailFragmentViewModel() {
    }

    public void getPostDetail(String id) {
        FirebaseDatabase.getInstance().getReference("UserPosts").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    UserPost userPost = snapshot.getValue(UserPost.class);
                    _postDetail.postValue(userPost);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void postComment(ArrayList<CommentModel> comment, String postId) {
        FirebaseDatabase.getInstance().getReference("UserPosts").child(postId).child("comments").setValue(comment).addOnCompleteListener(task -> {
            _postCommentResponse.postValue(true);
        });
    }

    public void getListUserLiked(ArrayList<String> userIdList) {
        ArrayList<UserProfile> likedListUser = new ArrayList<>();

        FirebaseDatabase.getInstance().getReference("UserProfile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        UserProfile user = postSnapshot.getValue(UserProfile.class);
                        for (String id : userIdList) {
                            if (id.equals(user.uuid)) {
                                likedListUser.add(user);
                            }
                        }
                    }
                    _userLikedList.postValue(likedListUser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void likePost(UserPost post) {
        FirebaseDatabase.getInstance().getReference("UserPosts").child(post.getId()).setValue(post).addOnCompleteListener(task -> {

        });
    }
}
