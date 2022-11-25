package com.example.travel_app.feature.groups;

import static com.example.travel_app.feature.explore.ExploreFragment.userProfile;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travel_app.core.Const;
import com.example.travel_app.core.platform.BaseViewModel;
import com.example.travel_app.feature.groups.model.Friend;
import com.example.travel_app.feature.model.GroupModel;
import com.example.travel_app.feature.model.UserProfileLite;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupDetailVIewModel extends BaseViewModel {

    private MutableLiveData<ArrayList<UserProfileLite>> _member = new MutableLiveData<>();
    public LiveData<ArrayList<UserProfileLite>> member = _member;

    public GroupDetailVIewModel() {

    }

    void getListMember(String groupId) {
        FirebaseDatabase.getInstance().getReference().child("GroupList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        GroupModel groupModel = postSnapshot.getValue(GroupModel.class);
                        if (groupModel.getGroupID().equals(groupId)) {
                            _member.postValue(groupModel.getMemberList());
                            break;
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
