package com.example.travel_app.feature.login.fragment;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travel_app.core.platform.BaseViewModel;
import com.example.travel_app.feature.login.model.LoginStatus;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.travel_app.core.extensions.StringExtension.validateLoginData;

import javax.inject.Inject;
import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginFragmentViewModel extends BaseViewModel {
    @Inject
    FirebaseAuth mAuth;

    private final MutableLiveData<LoginStatus> _loginStatus = new MutableLiveData<>();
    public LiveData<LoginStatus> loginStatus = _loginStatus;

    @Inject
    public LoginFragmentViewModel() {
    }

    public void doLoginWithEmailAndPassword(Activity activity, String email, String password) {
        if (validateLoginData(email, password)) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, task -> {
                        if (task.isSuccessful()) {
                            _loginStatus.postValue(LoginStatus.SUCCESS);
                        } else {
                            _loginStatus.postValue(LoginStatus.FAIL);
                        }
                    });
        } else {
            _loginStatus.postValue(LoginStatus.WRONG_FORMAT);
        }

    }


}
