package com.example.travel_app.core.views;

import android.app.Dialog;
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
import com.example.travel_app.databinding.LikedBottomSheetLayoutBinding;
import com.example.travel_app.feature.explore.adapter.CommentAdapter;
import com.example.travel_app.feature.explore.adapter.LikedAdapter;
import com.example.travel_app.feature.model.CommentModel;
import com.example.travel_app.feature.model.UserProfile;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;

public class LikedBottomSheetDialog extends BottomSheetDialogFragment {
    private ArrayList<UserProfile> likedUserList;
    private LikedAdapter likedAdapter;

    public LikedBottomSheetDialog(ArrayList<UserProfile> likedUserList) {
        this.likedUserList = likedUserList;
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
        return LikedBottomSheetLayoutBinding.inflate(inflater, container, false).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        likedAdapter = new LikedAdapter(likedUserList);
        RecyclerView rvLiked = view.findViewById(R.id.recycler_view_liked);
        rvLiked.setAdapter(likedAdapter);
    }
}
