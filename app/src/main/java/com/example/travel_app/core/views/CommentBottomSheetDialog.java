package com.example.travel_app.core.views;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travel_app.R;
import com.example.travel_app.databinding.CommentBottomSheetLayoutBinding;
import com.example.travel_app.feature.explore.adapter.CommentAdapter;
import com.example.travel_app.feature.explore.model.Comment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class CommentBottomSheetDialog extends BottomSheetDialogFragment {
    private ArrayList<Comment> arrayList;
    private CommentAdapter commentAdapter;
    public CommentBottomSheetDialog(ArrayList<Comment> arrayList) {
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme);
        dialog.setOnShowListener(dialog1 -> {
            BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialog1;
            View parentLayout = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            BottomSheetBehavior behavior = BottomSheetBehavior.from(parentLayout);

            ViewGroup.LayoutParams layoutParams = parentLayout.getLayoutParams();
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            parentLayout.setLayoutParams(layoutParams);

            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            behavior.setSkipCollapsed(true);
        });

        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return CommentBottomSheetLayoutBinding.inflate(inflater, container, false).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        commentAdapter = new CommentAdapter();
        RecyclerView rvComments = view.findViewById(R.id.recycler_view_comment);
        rvComments.setAdapter(commentAdapter);
        commentAdapter.submitList(arrayList);
    }
}
