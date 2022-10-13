package com.example.travel_app.feature.login.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travel_app.R;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentLoginSplashBinding;

public class LoginSplashFragment extends BaseFragment<FragmentLoginSplashBinding> {
    private NavController navController;
    public static LoginSplashFragment newInstance() {
        LoginSplashFragment fragment = new LoginSplashFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public FragmentLoginSplashBinding onCreateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentLoginSplashBinding.inflate(inflater, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        viewBinding.btnLogin.setOnClickListener(v -> {
            navController.navigate(R.id.action_loginSplashFragment_to_loginFragment);
        });
        viewBinding.btnRegister.setOnClickListener(v -> {
            navController.navigate(R.id.action_loginSplashFragment_to_registerFragment);
        });
    }
}