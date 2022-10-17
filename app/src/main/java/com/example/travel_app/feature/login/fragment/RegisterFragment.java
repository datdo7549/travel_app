package com.example.travel_app.feature.login.fragment;

import static com.example.travel_app.feature.login.model.RegisterAccountStatus.DUPLICATE_ACCOUNT;
import static com.example.travel_app.feature.login.model.RegisterAccountStatus.FAIL;
import static com.example.travel_app.feature.login.model.RegisterAccountStatus.SUCCESS_BUT_UPDATE_PROFILE_ERROR;
import static com.example.travel_app.feature.login.model.RegisterAccountStatus.WRONG_FORMAT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.travel_app.MainActivity;
import com.example.travel_app.R;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentRegisterBinding;
import com.example.travel_app.feature.login.model.RegisterAccountStatus;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends BaseFragment<FragmentRegisterBinding, RegisterFragmentViewModel> {
    private NavController navController;

    @Override
    public FragmentRegisterBinding onCreateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentRegisterBinding.inflate(inflater, container, false);
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
        viewBinding.btnLoginNow.setOnClickListener(v -> navController.navigate(R.id.action_registerFragment_to_loginFragment));
        viewBinding.btnCreateAccount.setOnClickListener(v -> viewModel.createNewAccountWithEmail(requireActivity(),
                Objects.requireNonNull(viewBinding.editTextEmail.getText()).toString(),
                Objects.requireNonNull(viewBinding.editTextPassword.getText()).toString(),
                Objects.requireNonNull(viewBinding.editTextConfirmPassword.getText()).toString()
        ));
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(RegisterFragmentViewModel.class);
        viewModel.registerStatus.observe(getViewLifecycleOwner(), registerAccountStatus -> {
            switch (registerAccountStatus) {
                case SUCCESS:
                    showResultDialog(RegisterAccountStatus.SUCCESS, () -> MainActivity.start(requireContext()));
                    break;
                case SUCCESS_BUT_UPDATE_PROFILE_ERROR:
                    showResultDialog(SUCCESS_BUT_UPDATE_PROFILE_ERROR, () -> MainActivity.start(requireContext()));
                    MainActivity.start(requireContext());
                    break;
                case FAIL:
                    showResultDialog(FAIL);
                    break;
                case WRONG_FORMAT:
                    showResultDialog(WRONG_FORMAT);
                    break;
                case DUPLICATE_ACCOUNT:
                    showResultDialog(DUPLICATE_ACCOUNT);
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
}