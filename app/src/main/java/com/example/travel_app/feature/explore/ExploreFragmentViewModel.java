package com.example.travel_app.feature.explore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travel_app.core.Const;
import com.example.travel_app.core.platform.BaseViewModel;
import com.example.travel_app.feature.profile.model.UserPost;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ExploreFragmentViewModel extends BaseViewModel {
    private MutableLiveData<ArrayList<UserPost>> _posts = new MutableLiveData<>();
    public LiveData<ArrayList<UserPost>> posts = _posts;

    @Inject
    public ExploreFragmentViewModel() {}

    public void getUserPost() {
        ArrayList<UserPost> dumpPosts = new ArrayList<>();
        dumpPosts.add(new UserPost("1", "Rocky 1", "New york", Const.POST_COVER_SAMPLE, "About post"));
        dumpPosts.add(new UserPost("2", "Rocky 2", "New york", Const.POST_COVER_SAMPLE, "About post"));
        dumpPosts.add(new UserPost("3", "Rocky 3", "New york", Const.POST_COVER_SAMPLE, "About post"));
        dumpPosts.add(new UserPost("4", "Rocky 4", "New york", Const.POST_COVER_SAMPLE, "About post"));
        _posts.postValue(dumpPosts);
    }
}
