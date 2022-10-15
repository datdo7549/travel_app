package com.example.travel_app.feature.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travel_app.core.platform.BaseViewModel;
import com.example.travel_app.feature.profile.model.UserPost;

import java.util.ArrayList;

public class ProfileFragmentViewModel extends BaseViewModel {
    private static final String img_user_post_img_cover_sample = "https://images.unsplash.com/photo-1665501340269-5ced49f91a92?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1074&q=80";
    private MutableLiveData<ArrayList<UserPost>> _userPosts = new MutableLiveData<>();
    public LiveData<ArrayList<UserPost>> userPosts = _userPosts;

    public ProfileFragmentViewModel() {
        ArrayList<UserPost> dump = new ArrayList<>();
        dump.add(new UserPost("1", "Post 1" ,img_user_post_img_cover_sample));
        dump.add(new UserPost("2", "Post 2" ,img_user_post_img_cover_sample));

        _userPosts.postValue(dump);
    }
}
