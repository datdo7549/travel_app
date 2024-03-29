package com.example.travel_app.core.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.travel_app.core.listeners.DialogButtonClickListener;
import com.example.travel_app.databinding.SingleButtonDialogFragmentBinding;

import java.util.Objects;

public class SingleButtonDialogFragment extends DialogFragment {
    private SingleButtonDialogFragmentBinding viewBinding;
    private final FragmentManager fragmentManager;
    private final String title, description;

    private DialogButtonClickListener dialogButtonClickListener = null;

    public SingleButtonDialogFragment(FragmentManager fragmentManager, String title, String description) {
        this.fragmentManager = fragmentManager;
        this.title = title;
        this.description = description;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewBinding = SingleButtonDialogFragmentBinding.inflate(inflater, container, false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Window window = Objects.requireNonNull(this.getDialog()).getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setCancelable(false);

        viewBinding.textViewTitle.setText(title);
        viewBinding.textViewDescription.setText(description);
        viewBinding.btnCancel.setOnClickListener(v -> {
            dismiss();
            if (dialogButtonClickListener != null) dialogButtonClickListener.onCLick();
        });
    }

    public void setRegisterAccountButtonListener(DialogButtonClickListener dialogButtonClickListener) {
        this.dialogButtonClickListener = dialogButtonClickListener;
    }

    public void show(String tag) {
        super.show(fragmentManager, tag);
    }
}
