package com.example.travel_app.core.platform;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.viewbinding.ViewBinding;

import com.example.travel_app.R;
import com.example.travel_app.core.dialog.SingleButtonDialogFragment;
import com.example.travel_app.core.listeners.RegisterAccountButtonListener;
import com.example.travel_app.feature.login.model.LoginStatus;
import com.example.travel_app.feature.login.model.RegisterAccountStatus;

public abstract class BaseFragment<VB extends ViewBinding, VM extends ViewModel> extends Fragment {
    public VB viewBinding;

    public VM viewModel;

    public ProgressDialog progressDialog;

    public abstract VB onCreateViewBinding(LayoutInflater inflater, ViewGroup container);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle(getResources().getString(R.string.text_please_wait));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBinding = onCreateViewBinding(inflater, container);
        return viewBinding.getRoot();
    }

    public void showResultDialog(BaseEnum status) {
        SingleButtonDialogFragment singleButtonDialogFragment = new SingleButtonDialogFragment(
                getChildFragmentManager(),
                status.getTitle(),
                status.getDescription()
        );
        singleButtonDialogFragment.show(status.getTitle());
    }

    public void showResultDialog(BaseEnum status, RegisterAccountButtonListener registerAccountButtonListener) {
        SingleButtonDialogFragment singleButtonDialogFragment = new SingleButtonDialogFragment(
                getChildFragmentManager(),
                status.getTitle(),
                status.getDescription()
        );
        singleButtonDialogFragment.show(status.getTitle());
        singleButtonDialogFragment.setRegisterAccountButtonListener(registerAccountButtonListener);
    }
}
