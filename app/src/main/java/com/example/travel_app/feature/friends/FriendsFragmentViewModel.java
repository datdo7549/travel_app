package com.example.travel_app.feature.friends;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travel_app.core.platform.BaseViewModel;
import com.example.travel_app.feature.groups.model.Friend;

import java.util.ArrayList;

public class FriendsFragmentViewModel extends BaseViewModel {
    private MutableLiveData<ArrayList<Friend>> _teammates = new MutableLiveData<>();
    public LiveData<ArrayList<Friend>> teammate = _teammates;

    public FriendsFragmentViewModel() {
        ArrayList<Friend> temp = new ArrayList<>();
        temp.add(new Friend("1", "Dat 1", "0", "https://images.unsplash.com/photo-1633332755192-727a05c4013d?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=880&amp;q=80"));
        temp.add(new Friend("2", "Dat 2", "0", "https://images.unsplash.com/photo-1633332755192-727a05c4013d?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=880&amp;q=80"));
        temp.add(new Friend("3", "Dat 3", "0", "https://images.unsplash.com/photo-1633332755192-727a05c4013d?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=880&amp;q=80"));
        temp.add(new Friend("4", "Dat 4", "0", "https://images.unsplash.com/photo-1633332755192-727a05c4013d?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=880&amp;q=80"));
        temp.add(new Friend("5", "Dat 5", "0", "https://images.unsplash.com/photo-1633332755192-727a05c4013d?ixlib=rb-1.2.1&amp;ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&amp;auto=format&amp;fit=crop&amp;w=880&amp;q=80"));

        _teammates.postValue(temp);
    }
}
