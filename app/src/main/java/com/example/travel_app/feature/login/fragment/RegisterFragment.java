package com.example.travel_app.feature.login.fragment;

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
import com.example.travel_app.core.dialog.SingleButtonDialogFragment;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentRegisterBinding;
import com.example.travel_app.feature.login.model.RegisterAccountStatus;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RegisterFragment extends BaseFragment<FragmentRegisterBinding, RegisterFragmentViewModel> {
    private NavController navController;

    private SingleButtonDialogFragment singleButtonDialogFragment;

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
                    singleButtonDialogFragment = new SingleButtonDialogFragment(
                            getChildFragmentManager(),
                            RegisterAccountStatus.SUCCESS.getTitle(),
                            RegisterAccountStatus.SUCCESS.getDescription()
                    );
                    singleButtonDialogFragment.show(RegisterAccountStatus.SUCCESS.getTitle());
                    navController.navigate(R.id.action_registerFragment_to_loginFragment);
                    break;
                case FAIL:
                    singleButtonDialogFragment = new SingleButtonDialogFragment(
                            getChildFragmentManager(),
                            RegisterAccountStatus.FAIL.getTitle(),
                            RegisterAccountStatus.FAIL.getDescription()
                    );
                    singleButtonDialogFragment.show(RegisterAccountStatus.FAIL.getTitle());
                    break;
                case WRONG_FORMAT:
                    singleButtonDialogFragment = new SingleButtonDialogFragment(
                            getChildFragmentManager(),
                            RegisterAccountStatus.WRONG_FORMAT.getTitle(),
                            RegisterAccountStatus.WRONG_FORMAT.getDescription()
                    );
                    singleButtonDialogFragment.show(RegisterAccountStatus.WRONG_FORMAT.getTitle());
                    break;
                case DUPLICATE_ACCOUNT:
                    singleButtonDialogFragment = new SingleButtonDialogFragment(
                            getChildFragmentManager(),
                            RegisterAccountStatus.DUPLICATE_ACCOUNT.getTitle(),
                            RegisterAccountStatus.DUPLICATE_ACCOUNT.getDescription()
                    );
                    singleButtonDialogFragment.show(RegisterAccountStatus.DUPLICATE_ACCOUNT.getTitle());
                    break;
            }
        });
    }
}