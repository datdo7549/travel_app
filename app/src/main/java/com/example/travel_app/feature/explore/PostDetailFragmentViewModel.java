package com.example.travel_app.feature.explore;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travel_app.core.platform.BaseViewModel;
import com.example.travel_app.feature.model.CommentModel;
import com.example.travel_app.feature.model.UserPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

import dagger.hilt.android.lifecycle.HiltViewModel;

public class PostDetailFragmentViewModel extends BaseViewModel {
    private MutableLiveData<UserPost> _postDetail = new MutableLiveData<>();
    public LiveData<UserPost> postDetail = _postDetail;

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

    public void createComment(String comment, UserPost userPost) {
        CommentModel commentModel = new CommentModel(
                UUID.randomUUID().toString(),
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                "Dat",
                comment,
                System.currentTimeMillis()
        );
        ArrayList<CommentModel> comments = new ArrayList<>();
        if (userPost.getComments() != null) {
            comments.clear();
            comments.addAll(userPost.getComments());
        }
        comments.add(commentModel);
        userPost.setComments(comments);
        FirebaseDatabase.getInstance().getReference("UserPosts").child(userPost.getId()).setValue(userPost).addOnCompleteListener(task_2 -> {

        });
    }
}
