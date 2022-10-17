package com.example.travel_app.feature.login.fragment;

import static com.example.travel_app.core.extensions.StringExtension.validateLoginData;
import static com.example.travel_app.feature.login.model.LoginStatus.FAIL;
import static com.example.travel_app.feature.login.model.LoginStatus.GOOGLE_SIGN_IN_AUTO_LOG_IN;
import static com.example.travel_app.feature.login.model.LoginStatus.GOOGLE_SIGN_IN_SUCCESS;
import static com.example.travel_app.feature.login.model.LoginStatus.SUCCESS;

import android.app.Activity;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.travel_app.core.platform.BaseViewModel;
import com.example.travel_app.feature.login.model.LoginStatus;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginFragmentViewModel extends BaseViewModel {
    private final MutableLiveData<LoginStatus> _loginStatus = new MutableLiveData<>();
    public LiveData<LoginStatus> loginStatus = _loginStatus;

    @Inject
    public LoginFragmentViewModel() {}

    public void doLoginWithEmailAndPassword(Activity activity, String email, String password) {
        if (validateLoginData(email, password)) {
            _loading.postValue(true);
            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity, task -> {
                        _loading.postValue(false);
                        if (task.isSuccessful()) {
                            _loginStatus.postValue(SUCCESS);
                        } else {
                            _loginStatus.postValue(FAIL);
                        }
                    });
        } else {
            _loginStatus.postValue(LoginStatus.WRONG_FORMAT);
        }
    }

    public void initGoogleSignIn(FragmentActivity activity, GoogleApiClient.OnConnectionFailedListener unresolvedConnectionFailedListener) {
        googleApiClient = new GoogleApiClient.Builder(activity.getBaseContext())
                .enableAutoManage(activity, unresolvedConnectionFailedListener)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                _loginStatus.postValue(GOOGLE_SIGN_IN_AUTO_LOG_IN);
            }
        };

        firebaseAuth.addAuthStateListener(authStateListener);
    }

    public void doLoginWithGoogleAccount(ActivityResultLauncher<Intent> googleSignInResultLauncher) {
        _loading.postValue(true);
        Intent googleSignInApiSignInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        if (googleApiClient != null && googleApiClient.isConnected()) {
            googleApiClient.clearDefaultAccountAndReconnect();
        }
        googleSignInResultLauncher.launch(googleSignInApiSignInIntent);
    }

    public void firebaseAuthWithGoogle(FragmentActivity fragmentActivity, AuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(fragmentActivity, task -> {
                    _loading.postValue(false);
                    if (task.isSuccessful()) {
                        _loginStatus.postValue(GOOGLE_SIGN_IN_SUCCESS);
                    } else {
                        _loginStatus.postValue(FAIL);
                    }
                });
    }

    public void removeAuthStateListener(FragmentActivity fragmentActivity) {
        firebaseAuth.removeAuthStateListener(authStateListener);
        if (googleApiClient != null) {
            googleApiClient.stopAutoManage(fragmentActivity);
            googleApiClient.disconnect();
        }
    }
}
