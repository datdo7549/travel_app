package com.example.travel_app.feature.chat;

import static com.example.travel_app.feature.explore.ExploreFragment.userProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.travel_app.core.Const;
import com.example.travel_app.core.listeners.BackButtonListener;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentChatBinding;
import com.example.travel_app.feature.chat.adapter.MessageListAdapter;
import com.example.travel_app.feature.chat.model.MessageData;
import com.example.travel_app.feature.explore.PostDetailFragmentArgs;
import com.example.travel_app.feature.friends.FriendsFragmentViewModel;
import com.example.travel_app.feature.model.GroupModel;
import com.example.travel_app.feature.model.UserProfile;
import com.example.travel_app.feature.model.UserProfileLite;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends BaseFragment<FragmentChatBinding, ChatFragmentViewModel> {
    private MessageListAdapter messageListAdapter;
    private ArrayList<MessageData> messageData = new ArrayList<>();
    private NavController controller;

    private String receiverId = "";
    private String name = "";


    private GroupModel currentGroup;
    private ArrayList<GroupModel> currentGroupList = new ArrayList<>();

    private UserProfile userProfile2;

    @Override
    public FragmentChatBinding onCreateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentChatBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        messageListAdapter = new MessageListAdapter(messageData);
        viewBinding.listViewMessage.setLayoutManager(new LinearLayoutManager(requireContext()));
        viewBinding.listViewMessage.setAdapter(messageListAdapter);

        controller = Navigation.findNavController(view);

        receiverId = ChatFragmentArgs.fromBundle(getArguments()).getReceiveId();
        name = ChatFragmentArgs.fromBundle(getArguments()).getName();

        FirebaseDatabase.getInstance().getReference().child("GroupList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentGroupList.clear();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        GroupModel groupModel = postSnapshot.getValue(GroupModel.class);
                        currentGroupList.add(groupModel);
                        if (groupModel != null && userProfile.myCreatedGroup.equals(groupModel.getGroupID())) {
                           currentGroup = groupModel;
                       }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        FirebaseDatabase.getInstance().getReference().child(Const.USER_PROFILE).child(receiverId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userProfile2 = snapshot.getValue(UserProfile.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        initView();
        initViewModel();
    }

    private void initView() {
        viewBinding.toolBar.setTitleToolBar(name);

        if (userProfile.myCreatedGroup != null && !userProfile.myCreatedGroup.isEmpty()) {
            boolean flag = true;
            for (GroupModel groupModel : userProfile.listGroup) {
                if (groupModel.getGroupID().equals(userProfile.myCreatedGroup)) {
                    for (UserProfileLite userProfileLite : groupModel.getMemberList()) {
                        if (userProfileLite.uuid.equals(receiverId)) {
                            flag = false;
                            break;
                        }
                    }
                    break;
                }
            }


            viewBinding.toolBar.visibilityBtnAddGroup(flag);
            viewBinding.toolBar.getBtnAddGroup().setOnClickListener(v -> {
                ArrayList<UserProfileLite> listUserLite = currentGroup.getMemberList();
                if (listUserLite != null) {
                    listUserLite.add(new UserProfileLite(
                            receiverId,
                            name,
                            ""
                    ));
                }
                ArrayList<GroupModel> newGroupList = new ArrayList<>();
                for (GroupModel groupModel : currentGroupList) {
                    if (groupModel.getGroupID().equals(currentGroup.getGroupID())) {
                        newGroupList.add(currentGroup);
                    } else {
                        newGroupList.add(groupModel);
                    }
                }

                currentGroupList.clear();
                currentGroupList.addAll(newGroupList);

                currentGroup.setMemberList(listUserLite);

                FirebaseDatabase.getInstance().getReference().child("GroupList").setValue(currentGroupList);


                ArrayList<GroupModel> user1ListGroup = new ArrayList<>();
                for (GroupModel groupModel : userProfile.listGroup) {
                    if (groupModel.getGroupID().equals(userProfile.myCreatedGroup)) {
                        user1ListGroup.add(currentGroup);
                        break;
                    } else {
                        user1ListGroup.add(groupModel);
                    }
                }

                userProfile.setListGroup(user1ListGroup);

                FirebaseDatabase.getInstance().getReference().child(Const.USER_PROFILE).child(userProfile.uuid).setValue(userProfile);

                ArrayList<GroupModel> user2ListGroup = new ArrayList<>();
                if (userProfile2.listGroup != null && userProfile2.listGroup.size() > 0) {
                    user2ListGroup = userProfile2.listGroup;
                }

                user2ListGroup.add(currentGroup);

                userProfile2.setListGroup(user2ListGroup);

                FirebaseDatabase.getInstance().getReference().child(Const.USER_PROFILE).child(receiverId).setValue(userProfile2);

            });
        }
        viewBinding.toolBar.setButtonBackListener(() -> controller.navigateUp());

        viewBinding.buttonSend.setOnClickListener(v -> {
            MessageData messageData = new MessageData(
                    getMessageId(),
                    userProfile.uuid,
                    userProfile.name,
                    viewBinding.editMessage.getText().toString(),
                    System.currentTimeMillis()
            );
            viewModel.sentMessage(messageData);
        });
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ChatFragmentViewModel.class);

        viewModel.getListMessage(getMessageId());
        viewModel.listMessage.observe(getViewLifecycleOwner(), listMessage -> {
            messageData.clear();
            messageData.addAll(listMessage);
            messageListAdapter.notifyDataSetChanged();
        });
    }

    private String getMessageId() {
        String messageId = "";
        String senderId = userProfile.uuid;
        if (senderId.compareTo(receiverId) > 0) {
            messageId = senderId + "/" + receiverId;
        } else {
            messageId = receiverId + "/" + senderId;
        }
        return messageId;
    }
}   