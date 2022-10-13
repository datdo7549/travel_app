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
import com.example.travel_app.databinding.FragmentLoginBinding;

import java.util.Objects;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginFragment extends BaseFragment<FragmentLoginBinding, LoginFragmentViewModel> {
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
        viewBinding.btnLogin.setOnClickListener(v -> viewModel.doLogin(
                requireActivity(),
                Objects.requireNonNull(viewBinding.editTextEmail.getText()).toString(),
                Objects.requireNonNull(viewBinding.editTextPassword.getText()).toString()
        ));
        viewBinding.btnGoogleLogin.setOnClickListener(v -> {

        });
        viewBinding.btnRegisterNow.setOnClickListener(v -> navController.navigate(R.id.action_loginFragment_to_registerFragment));
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(LoginFragmentViewModel.class);
        viewModel.loginStatus.observe(getViewLifecycleOwner(), loginStatus -> {
            switch (loginStatus) {
                case SUCCESS:
                    Toast.makeText(requireContext(), "Login success", Toast.LENGTH_LONG).show();
                    break;
                case FAIL:
                    Toast.makeText(requireContext(), "Login fail", Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }
}