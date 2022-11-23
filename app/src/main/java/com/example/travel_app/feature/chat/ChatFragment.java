package com.example.travel_app.feature.chat;

import static com.example.travel_app.feature.explore.ExploreFragment.userProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentChatBinding;
import com.example.travel_app.feature.chat.adapter.MessageListAdapter;
import com.example.travel_app.feature.chat.model.MessageData;
import com.example.travel_app.feature.explore.PostDetailFragmentArgs;
import com.example.travel_app.feature.friends.FriendsFragmentViewModel;

import java.util.ArrayList;

public class ChatFragment extends BaseFragment<FragmentChatBinding, ChatFragmentViewModel> {
    private MessageListAdapter messageListAdapter;
    private ArrayList<MessageData> messageData = new ArrayList<>();

    private String receiverId = "";
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


        receiverId = ChatFragmentArgs.fromBundle(getArguments()).getReceiveId();


        initView();
        initViewModel();
    }

    private void initView() {
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