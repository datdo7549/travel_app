package com.example.travel_app.feature.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.travel_app.R;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentProfileSettingsBinding;
import com.example.travel_app.feature.login.LoginActivity;
import com.example.travel_app.feature.profile.model.UserState;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileSettingsFragment extends BaseFragment<FragmentProfileSettingsBinding, ProfileSettingsFragmentViewModel> {
    private NavController navController;

    @Override
    public FragmentProfileSettingsBinding onCreateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentProfileSettingsBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        initView();
        initViewModel();
    }

    private void initView() {
        viewBinding.toolBar.setButtonBackListener(() -> navController.navigateUp());
        viewBinding.lnUpdateProfile.setOnClickListener(v -> navController.navigate(R.id.action_profileSettingsFragment_to_updateProfileFragment));
        viewBinding.lnSignOut.setOnClickListener(v -> viewModel.logout());
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ProfileSettingsFragmentViewModel.class);
        viewModel.firebaseAuth.addAuthStateListener(viewModel.authStateListener);
        viewModel.userState.observe(getViewLifecycleOwner(), isLogin -> {
            if (isLogin == UserState.USER_LOG_OUT) {
                requireActivity().finish();
                Intent intent = new Intent(requireContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.removeAuthStateListener(requireActivity());
    }
}