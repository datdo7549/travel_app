package com.example.travel_app.feature.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travel_app.core.Const;
import com.example.travel_app.core.platform.BaseViewModel;
import com.example.travel_app.feature.model.UserPost;
import com.example.travel_app.feature.model.UserProfile;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProfileFragmentViewModel extends BaseViewModel {
    private static final String img_user_post_img_cover_sample = "https://images.unsplash.com/photo-1665501340269-5ced49f91a92?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1074&q=80";

    private MutableLiveData<UserProfile> _userProfile = new MutableLiveData<>();
    public LiveData<UserProfile> userProfile = _userProfile;

    @Inject
    public ProfileFragmentViewModel() {
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
}
