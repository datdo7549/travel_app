package com.example.travel_app.feature.explore;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travel_app.core.Const;
import com.example.travel_app.core.platform.BaseViewModel;
import com.example.travel_app.feature.model.UserPost;
import com.example.travel_app.feature.model.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ExploreFragmentViewModel extends BaseViewModel {
    private final MutableLiveData<ArrayList<UserPost>> _posts = new MutableLiveData<>();
    public LiveData<ArrayList<UserPost>> posts = _posts;

    private final MutableLiveData<UserProfile> _userProfile = new MutableLiveData<>();
    public LiveData<UserProfile> userProfile = _userProfile;

    private final MutableLiveData<UserPost> _likePostResponse = new MutableLiveData<>();
    public LiveData<UserPost> likePostResponse = _likePostResponse;


    private final MutableLiveData<Boolean> _isHide = new MutableLiveData<>();
    public LiveData<Boolean> isHide = _isHide;

    public boolean isHavePost = false;
    public boolean isHaveUserProfile = false;

    public void setIsHavePost() {
        isHavePost = true;
        _isHide.postValue(isHavePost && isHaveUserProfile);
    }

    public void setIsHaveUserProfile() {
        isHaveUserProfile = true;
        _isHide.postValue(isHavePost && isHaveUserProfile);
    }

    @Inject
    public ExploreFragmentViewModel() {
    }

    public void getAllUserPost() {
        ArrayList<UserPost> users = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("UserPosts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    users.clear();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        UserPost user = postSnapshot.getValue(UserPost.class);
                        users.add(user);
                    }
                    _posts.postValue(users);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getUserProfile() {
        String uuid = firebaseAuth.getUid();
        assert uuid != null;
        firebaseDatabase.child(Const.USER_PROFILE).child(uuid).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                _userProfile.postValue(task.getResult().getValue(UserProfile.class));
            } else {
                _userProfile.postValue(null);
            }
        });
    }

    public void likePost(UserPost post) {
        firebaseDatabase.child(Const.USER_POST).child(post.getId()).setValue(post).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                _likePostResponse.postValue(post);
            }
        });
    }

    public void setOnline() {
        firebaseDatabase.child(Const.USER_ONLINE).child(Objects.requireNonNull(userProfile.getValue()).uuid).setValue(userProfile.getValue().uuid).addOnCompleteListener(task -> {

        });
    }
}
