package com.example.travel_app.feature.chat;

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
import com.example.travel_app.feature.friends.FriendsFragmentViewModel;

import java.util.ArrayList;

public class ChatFragment extends BaseFragment<FragmentChatBinding, ChatFragmentViewModel> {
    private MessageListAdapter messageListAdapter;
    private ArrayList<MessageData> messageData = new ArrayList<>();
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
        
        initViewModel();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ChatFragmentViewModel.class);

        viewModel.listMessage.observe(getViewLifecycleOwner(), listMessage -> {
            messageData.clear();
            messageData.addAll(listMessage);
            messageListAdapter.notifyDataSetChanged();
        });
    }
}   