package com.example.travel_app.feature.login.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.travel_app.MainActivity;
import com.example.travel_app.R;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentLoginBinding;
import com.example.travel_app.feature.login.model.LoginStatus;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginFragmentViewModel> implements GoogleApiClient.OnConnectionFailedListener {
    private NavController navController;

    @Override
    public FragmentLoginBinding onCreateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentLoginBinding.inflate(inflater, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        initView();
        initViewModel();
    }

    private void initView() {
        viewBinding.btnLogin.setOnClickListener(v ->
                viewModel.doLoginWithEmailAndPassword(
                        requireActivity(),
                        Objects.requireNonNull(viewBinding.editTextEmail.getText()).toString(),
                        Objects.requireNonNull(viewBinding.editTextPassword.getText()).toString()
                ));
        viewBinding.btnGoogleLogin.setOnClickListener(v -> viewModel.doLoginWithGoogleAccount(googleSignInResultLauncher));
        viewBinding.btnRegisterNow.setOnClickListener(v -> navController.navigate(R.id.action_loginFragment_to_registerFragment));
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(LoginFragmentViewModel.class);
        viewModel.initGoogleSignIn(requireActivity(), this);
        viewModel.loginStatus.observe(getViewLifecycleOwner(), loginStatus -> {
            switch (loginStatus) {
                case SUCCESS:
                case GOOGLE_SIGN_IN_SUCCESS:
                case GOOGLE_SIGN_IN_AUTO_LOG_IN:
                    MainActivity.start(requireContext());
                    break;
                case FAIL:
                    showResultDialog(LoginStatus.FAIL);
                    break;
                case WRONG_FORMAT:
                    showResultDialog(LoginStatus.WRONG_FORMAT);
                    break;
            }
        });

        viewModel.loading.observe(getViewLifecycleOwner(), isLoading -> {
            if (isLoading) {
                progressDialog.show();
            } else {
                progressDialog.hide();
            }
        });
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(requireContext(), "Connection failed, please try again", Toast.LENGTH_LONG).show();
    }

    ActivityResultLauncher<Intent> googleSignInResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getData() != null) {
                    GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(result.getData());
                    assert googleSignInResult != null;
                    handleSignInResult(googleSignInResult);
                }
            });

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount account = result.getSignInAccount();
            if (account != null) {
                String idToken = account.getIdToken();
                AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
                viewModel.firebaseAuthWithGoogle(requireActivity(), credential);
            }
        } else {
            showResultDialog(LoginStatus.FAIL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.removeAuthStateListener(requireActivity());
    }
}