package com.example.travel_app.feature.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.travel_app.R;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentProfileBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ProfileFragment extends BaseFragment<FragmentProfileBinding, ProfileFragmentViewModel> {

    private NavController navController;

    @Override
    public FragmentProfileBinding onCreateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentProfileBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        initView();
        initViewModel();
    }

    private void initView() {
        Glide.with(this)
                .load(getResources().getString(R.string.img_cover_sample))
                .centerCrop()
                .into(viewBinding.imgCover);

        Glide.with(this)
                .load(getResources().getString(R.string.img_avatar_sample))
                .circleCrop()
                .into(viewBinding.imgAvatar);

        viewBinding.btnSettings.setOnClickListener(v -> navController.navigate(R.id.action_profileFragment_to_profileSettingsFragment));
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(ProfileFragmentViewModel.class);
        viewModel.getUserProfile();
        viewModel.userProfile.observe(getViewLifecycleOwner(), userProfile -> {
            if (userProfile != null) {
                if (!userProfile.name.equals("")) {
                    viewBinding.userNameTextView.setText(userProfile.name);
                } else {
                    viewBinding.userNameTextView.setText(getResources().getString(R.string.text_name_place_holder));
                }
                viewBinding.userEmailTextView.setText(userProfile.email);
            } else {
                viewBinding.userNameTextView.setText(getResources().getString(R.string.text_name_place_holder));
            }
        });
    }
}