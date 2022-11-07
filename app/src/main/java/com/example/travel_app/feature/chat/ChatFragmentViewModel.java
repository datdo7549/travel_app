package com.example.travel_app.feature.chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travel_app.core.platform.BaseViewModel;
import com.example.travel_app.feature.chat.model.MessageData;

import java.util.ArrayList;

public class ChatFragmentViewModel extends BaseViewModel {
    private MutableLiveData<ArrayList<MessageData>> _listMessage = new MutableLiveData();
    public LiveData<ArrayList<MessageData>> listMessage = _listMessage;


    public ChatFragmentViewModel() {
        ArrayList<MessageData> temp = new ArrayList<>();

        temp.add(new MessageData("1", "1234", "Dat", "Hello", 1667809316));
        temp.add(new MessageData("2", "11234", "Rocky", "Hiii", 1667809333));

        _listMessage.postValue(temp);
    }
}
