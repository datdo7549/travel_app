package com.example.travel_app.feature.explore;

import static com.example.travel_app.feature.login.model.RegisterAccountStatus.CREATE_POST_FAIL;
import static com.example.travel_app.feature.login.model.RegisterAccountStatus.CREATE_POST_SUCCESS;

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

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ExploreFragmentViewModel extends BaseViewModel {
    private MutableLiveData<ArrayList<UserPost>> _posts = new MutableLiveData<>();
    public LiveData<ArrayList<UserPost>> posts = _posts;

    private MutableLiveData<UserProfile> _userProfile = new MutableLiveData<>();
    public LiveData<UserProfile> userProfile = _userProfile;

    private MutableLiveData<UserPost> _likePostResponse = new MutableLiveData<>();
    public LiveData<UserPost> likePostResponse = _likePostResponse;

    @Inject
    public ExploreFragmentViewModel() {
    }

    public void getAllUserPost() {
        _posts.postValue(new ArrayList<>());
        ArrayList<UserPost> temp = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("UserPosts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                temp.clear();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        UserPost user = postSnapshot.getValue(UserPost.class);
                        temp.add(user);
                    }
                    _posts.postValue(temp);
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
}
