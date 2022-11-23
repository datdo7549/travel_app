package com.example.travel_app.feature.chat;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travel_app.core.platform.BaseViewModel;
import com.example.travel_app.feature.chat.model.MessageData;
import com.example.travel_app.feature.model.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragmentViewModel extends BaseViewModel {
    private MutableLiveData<ArrayList<MessageData>> _listMessage = new MutableLiveData();
    public LiveData<ArrayList<MessageData>> listMessage = _listMessage;

    public void getListMessage(String messageId) {
        FirebaseDatabase.getInstance().getReference("MessageData").child(messageId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<MessageData> messageList = new ArrayList<>();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        MessageData messageData = postSnapshot.getValue(MessageData.class);
                        messageList.add(messageData);
                    }
                    _listMessage.postValue(messageList);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void sentMessage(MessageData messageData) {
        ArrayList<MessageData> currentListMessage = _listMessage.getValue();
        currentListMessage.add(messageData);
        FirebaseDatabase.getInstance().getReference("MessageData").child(messageData.getUuid()).setValue(currentListMessage)
                .addOnCompleteListener(task -> {

                });
    }
}
