package com.example.travel_app.feature.profile;

import static com.example.travel_app.feature.profile.model.UserState.USER_LOG_IN;
import static com.example.travel_app.feature.profile.model.UserState.USER_LOG_OUT;

import androidx.fragment.app.FragmentActivity;

import com.example.travel_app.core.platform.BaseViewModel;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ProfileSettingsFragmentViewModel extends BaseViewModel {

    @Inject
    public ProfileSettingsFragmentViewModel() {
        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                _userState.postValue(USER_LOG_IN);
            } else {
                _userState.postValue(USER_LOG_OUT);
            }
        };
    }

    public void logout() {
        firebaseAuth.signOut();
    }

    public void removeAuthStateListener(FragmentActivity fragmentActivity) {
        firebaseAuth.removeAuthStateListener(authStateListener);
        if (googleApiClient != null) {
            googleApiClient.stopAutoManage(fragmentActivity);
            googleApiClient.disconnect();
        }
    }
}
