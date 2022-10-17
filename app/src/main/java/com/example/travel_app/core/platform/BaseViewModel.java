package com.example.travel_app.core.platform;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.travel_app.core.data_store.AppPref;
import com.example.travel_app.feature.profile.model.UserState;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class BaseViewModel extends ViewModel {
    @Inject
    public BaseViewModel() {}

    @Inject
    public AppPref appPref;

    @Inject
    public GoogleSignInOptions googleSignInOptions;

    @Inject
    public FirebaseAuth firebaseAuth;

    @Inject
    public DatabaseReference firebaseDatabase;

    public FirebaseAuth.AuthStateListener authStateListener;

    public GoogleApiClient googleApiClient;

    public MutableLiveData<Boolean> _loading = new MutableLiveData<>();
    public LiveData<Boolean> loading = _loading;

    public MutableLiveData<UserState> _userState = new MutableLiveData<>();
    public LiveData<UserState> userState = _userState;
}
