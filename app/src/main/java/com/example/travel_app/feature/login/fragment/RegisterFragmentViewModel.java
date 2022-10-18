package com.example.travel_app.feature.login.fragment;

import static com.example.travel_app.core.extensions.StringExtension.validateRegisterData;
import static com.example.travel_app.feature.login.model.RegisterAccountStatus.DUPLICATE_ACCOUNT;
import static com.example.travel_app.feature.login.model.RegisterAccountStatus.FAIL;
import static com.example.travel_app.feature.login.model.RegisterAccountStatus.SUCCESS;
import static com.example.travel_app.feature.login.model.RegisterAccountStatus.SUCCESS_BUT_UPDATE_PROFILE_ERROR;
import static com.example.travel_app.feature.login.model.RegisterAccountStatus.WRONG_FORMAT;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travel_app.core.Const;
import com.example.travel_app.core.platform.BaseViewModel;
import com.example.travel_app.feature.login.model.RegisterAccountStatus;
import com.example.travel_app.feature.profile.model.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RegisterFragmentViewModel extends BaseViewModel {
    @Inject
    FirebaseAuth mAuth;

    private final MutableLiveData<RegisterAccountStatus> _registerStatus = new MutableLiveData<>();
    public LiveData<RegisterAccountStatus> registerStatus = _registerStatus;

    @Inject
    public RegisterFragmentViewModel() {
    }

    public void createNewAccountWithEmail(Activity activity, String email, String password, String confirmPassword) {
        if (validateRegisterData(email, password, confirmPassword)) {
            _loading.postValue(true);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, task -> {
                        if (task.isSuccessful()) {
                            //Create user profile on database
                            createUserProfileOnDatabase(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid());
                        } else {
                            _loading.postValue(false);
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                _registerStatus.postValue(DUPLICATE_ACCOUNT);
                            } else {
                                _registerStatus.postValue(FAIL);
                            }
                        }
                    });
        } else {
            _registerStatus.postValue(WRONG_FORMAT);
        }
    }

    private void createUserProfileOnDatabase(String uuid) {
        String email = firebaseAuth.getCurrentUser().getEmail();
        UserProfile userProfile = new UserProfile(uuid, email);
        firebaseDatabase.child(Const.USER_PROFILE).child(uuid).setValue(userProfile).addOnCompleteListener(task_2 -> {
            _loading.postValue(false);
            if (task_2.isComplete()) {
                _registerStatus.postValue(SUCCESS);
            } else {
                _registerStatus.postValue(SUCCESS_BUT_UPDATE_PROFILE_ERROR);
            }
        });
    }
}
