package com.example.travel_app.feature.profile;

import static com.example.travel_app.core.extensions.StringExtension.validateUpdateProfileData;
import static com.example.travel_app.feature.profile.model.UpdateProfileStatus.UPDATE_PROFILE_FAIL;
import static com.example.travel_app.feature.profile.model.UpdateProfileStatus.UPDATE_PROFILE_STATUS_WRONG_FORMAT;
import static com.example.travel_app.feature.profile.model.UpdateProfileStatus.UPDATE_PROFILE_SUCCESS;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travel_app.core.platform.BaseViewModel;
import com.example.travel_app.feature.profile.model.UpdateProfileStatus;
import com.example.travel_app.feature.profile.model.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UpdateProfileFragmentViewModel extends BaseViewModel {
    private MutableLiveData<UpdateProfileStatus> _updateProfile = new MutableLiveData<>();
    public LiveData<UpdateProfileStatus> updateProfile = _updateProfile;

    @Inject
    public UpdateProfileFragmentViewModel() {}

    public void updateUserProfile(String name, String phoneNumber, Long birthday, String address) {
        if (validateUpdateProfileData(name, phoneNumber, birthday, address)) {
            String uuid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
            UserProfile userProfile = new UserProfile(uuid, name, phoneNumber, birthday, address);
            firebaseDatabase.child("UserProfile").child(uuid).setValue(userProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        _updateProfile.postValue(UPDATE_PROFILE_SUCCESS);
                    } else {
                        _updateProfile.postValue(UPDATE_PROFILE_FAIL);
                    }
                }
            });
        } else {
            _updateProfile.postValue(UPDATE_PROFILE_STATUS_WRONG_FORMAT);
        }
    }
}
