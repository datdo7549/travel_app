package com.example.travel_app.feature.login.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.travel_app.R;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.databinding.FragmentRegisterBinding;

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
                    Toast.makeText(requireContext(), "Create account success", Toast.LENGTH_LONG).show();
                    navController.navigate(R.id.action_registerFragment_to_loginFragment);
                    break;
                case FAIL:
                    Toast.makeText(requireContext(), "Create account fail", Toast.LENGTH_LONG).show();
                    break;
                case WRONG_FORMAT:
                    Toast.makeText(requireContext(), "Wrong format of email or password", Toast.LENGTH_LONG).show();
                    break;
                case DUPLICATE_ACCOUNT:
                    Toast.makeText(requireContext(), "Account existed", Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }
}