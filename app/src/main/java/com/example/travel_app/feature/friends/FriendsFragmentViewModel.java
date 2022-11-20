package com.example.travel_app.feature.friends;

import static com.example.travel_app.feature.explore.ExploreFragment.userProfile;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travel_app.core.platform.BaseViewModel;
import com.example.travel_app.feature.model.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class FriendsFragmentViewModel extends BaseViewModel {
    private MutableLiveData<ArrayList<UserProfile>> _friends = new MutableLiveData<>();
    public LiveData<ArrayList<UserProfile>> friends = _friends;

    private MutableLiveData<ArrayList<String>> _onlineUsers = new MutableLiveData<>();
    public LiveData<ArrayList<String>> onlineUsers = _onlineUsers;

    public FriendsFragmentViewModel() {

    }

    public void getFriends() {
        try {
            FirebaseDatabase.getInstance().getReference("UserProfile").child(userProfile.uuid).child("friends").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<UserProfile> friendList = new ArrayList<>();
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            UserProfile user = postSnapshot.getValue(UserProfile.class);
                            friendList.add(user);
                        }
                        _friends.postValue(friendList);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {

        }
    }

    public void getStatusOfFriendList() {
        try {
            FirebaseDatabase.getInstance().getReference("UserOnline").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    ArrayList<String> userOnlineList = new ArrayList<>();
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            String onlineUserID = Objects.requireNonNull(postSnapshot.getValue()).toString();
                            userOnlineList.add(onlineUserID);
                        }
                        _onlineUsers.postValue(userOnlineList);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {

        }
    }
}
