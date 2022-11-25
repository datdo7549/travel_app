package com.example.travel_app.feature.groups;

import static com.example.travel_app.feature.explore.ExploreFragment.userProfile;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travel_app.R;
import com.example.travel_app.core.Const;
import com.example.travel_app.core.platform.BaseViewModel;
import com.example.travel_app.feature.chat.model.MessageData;
import com.example.travel_app.feature.groups.model.Friend;
import com.example.travel_app.feature.model.GroupModel;
import com.example.travel_app.feature.model.UserProfile;
import com.example.travel_app.feature.model.UserProfileLite;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class GroupFragmentViewModel extends BaseViewModel {

    private MutableLiveData<ArrayList<GroupModel>> _groups = new MutableLiveData<>();
    public LiveData<ArrayList<GroupModel>> groups = _groups;

    private MutableLiveData<Boolean> _createGroupResponse = new MutableLiveData<>();
    public LiveData<Boolean> createGroupResponse = _createGroupResponse;


    ArrayList<GroupModel> currentGroupList = new ArrayList<>();

    ArrayList<GroupModel> allCurrentGroupList = new ArrayList<>();


    public GroupFragmentViewModel() {
        try {
            FirebaseDatabase.getInstance().getReference().child(Const.USER_PROFILE).child(userProfile.uuid).child("listGroup").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        currentGroupList = new ArrayList<>();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            GroupModel groupModel = postSnapshot.getValue(GroupModel.class);
                            currentGroupList.add(groupModel);
                        }
                        _groups.postValue(currentGroupList);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });



            FirebaseDatabase.getInstance().getReference().child("GroupList").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        allCurrentGroupList = new ArrayList<>();
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            GroupModel groupModel = postSnapshot.getValue(GroupModel.class);
                            allCurrentGroupList.add(groupModel);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } catch (Exception e) {}
    }

    public void createGroup() {
        //Create new Group:
        ArrayList<UserProfileLite> listMember = new ArrayList<>();
        listMember.add(new UserProfileLite(userProfile.uuid, userProfile.name, userProfile.email));
        GroupModel myGroupModel = new GroupModel();
        String groupId = UUID.randomUUID().toString();
        myGroupModel.setGroupID(groupId);
        myGroupModel.setGroupName(userProfile.name + "'s Group");
        myGroupModel.setMemberList(listMember);

        allCurrentGroupList.add(myGroupModel);
        currentGroupList.add(myGroupModel);

        userProfile.myCreatedGroup = groupId;

        ArrayList<GroupModel> tempListGroup = new ArrayList<>();
        if (userProfile.listGroup != null && userProfile.listGroup.size() > 0) {
            tempListGroup.addAll(userProfile.listGroup);
        }
        tempListGroup.add(myGroupModel);
        userProfile.setListGroup(tempListGroup);
        FirebaseDatabase.getInstance().getReference().child(Const.USER_PROFILE).child(userProfile.uuid).child("myCreatedGroup").setValue(userProfile.myCreatedGroup);
        FirebaseDatabase.getInstance().getReference().child(Const.USER_PROFILE).child(userProfile.uuid).child("listGroup").setValue(userProfile.listGroup);

        FirebaseDatabase.getInstance().getReference().child("GroupList").setValue(allCurrentGroupList).addOnCompleteListener(task -> {
            _createGroupResponse.postValue(true);
        });

    }

}
