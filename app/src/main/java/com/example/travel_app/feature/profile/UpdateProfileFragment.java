package com.example.travel_app.feature.profile;

import android.app.DatePickerDialog;
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
import com.example.travel_app.databinding.FragmentUpdateProfileBinding;
import com.example.travel_app.feature.profile.model.UpdateProfileStatus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UpdateProfileFragment extends BaseFragment<FragmentUpdateProfileBinding, UpdateProfileFragmentViewModel> {
    private NavController navController;

    @Override
    public FragmentUpdateProfileBinding onCreateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentUpdateProfileBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
        initView();
        initViewModel();
    }

    final Calendar myCalendar = Calendar.getInstance();

    private void initView() {
        viewBinding.toolBar.setButtonBackListener(() -> navController.navigateUp());
        DatePickerDialog.OnDateSetListener date = (view, year, month, day) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateBirthday();
        };

        viewBinding.editTextBirthday.setOnClickListener(v -> new DatePickerDialog(requireContext(),
                date, myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show());

        viewBinding.btnSave.setOnClickListener(v -> viewModel.updateUserProfile(
                Objects.requireNonNull(viewBinding.editTextName.getText()).toString(),
                Objects.requireNonNull(viewBinding.editTextPhone.getText()).toString(),
                myCalendar.getTimeInMillis(),
                Objects.requireNonNull(viewBinding.editTextAddress.getText()).toString()
        ));
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(UpdateProfileFragmentViewModel.class);
        viewModel.updateProfile.observe(getViewLifecycleOwner(), isSuccess -> {
            if (isSuccess == UpdateProfileStatus.UPDATE_PROFILE_SUCCESS) {
                showResultDialog(UpdateProfileStatus.UPDATE_PROFILE_SUCCESS, () -> navController.navigate(R.id.action_updateProfileFragment_to_profileFragment));
            } else {
                showResultDialog(UpdateProfileStatus.UPDATE_PROFILE_FAIL);
            }
        });
    }

    private void updateBirthday() {
        String myFormat = "MM/dd/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        viewBinding.editTextBirthday.setText(dateFormat.format(myCalendar.getTime()));
    }
}