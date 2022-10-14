package com.example.travel_app.feature.login.fragment;

import static com.example.travel_app.core.extensions.StringExtension.validateRegisterData;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travel_app.core.platform.BaseViewModel;
import com.example.travel_app.feature.login.model.RegisterAccountStatus;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

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
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, task -> {
                        if (task.isSuccessful()) {
                            _registerStatus.postValue(RegisterAccountStatus.SUCCESS);
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                _registerStatus.postValue(RegisterAccountStatus.DUPLICATE_ACCOUNT);
                            } else {
                                _registerStatus.postValue(RegisterAccountStatus.FAIL);
                            }
                        }
                    });
        } else {
            _registerStatus.postValue(RegisterAccountStatus.WRONG_FORMAT);
        }
    }
}
