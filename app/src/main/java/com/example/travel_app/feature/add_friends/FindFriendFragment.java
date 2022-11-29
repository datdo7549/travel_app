package com.example.travel_app.feature.add_friends;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.travel_app.core.listeners.ItemLikeCommentClick;
import com.example.travel_app.core.platform.BaseFragment;
import com.example.travel_app.core.views.UserProfileDialog;
import com.example.travel_app.databinding.FragmentFindFriendBinding;
import com.example.travel_app.feature.explore.adapter.LikedAdapter;
import com.example.travel_app.feature.model.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class FindFriendFragment extends BaseFragment<FragmentFindFriendBinding, FindFriendViewModel> {
    private final ArrayList<UserProfile> likedUserList = new ArrayList<>();
    private LikedAdapter userAdapter;

    @Override
    public FragmentFindFriendBinding onCreateViewBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentFindFriendBinding.inflate(inflater, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userAdapter = new LikedAdapter(likedUserList);
        userAdapter.setListener(new ItemLikeCommentClick() {
            @Override
            public void onAvatarClick(String uuid, String userName) {
                UserProfileDialog dialog = new UserProfileDialog(uuid, userName, false);
                dialog.show(getChildFragmentManager(), UserProfileDialog.class.getSimpleName());
            }
        });
        viewBinding.rvListUser.setAdapter(userAdapter);
        viewBinding.btnSearch.setOnClickListener(v -> {
            FirebaseDatabase.getInstance().getReference("UserProfile").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    likedUserList.clear();
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            UserProfile user = postSnapshot.getValue(UserProfile.class);
                            if (user.name != null && user.name.trim().toLowerCase(Locale.ROOT).contains(Objects.requireNonNull(viewBinding.edtSearch.getText()).toString().trim().toLowerCase(Locale.ROOT))) {
                                likedUserList.add(user);
                            }
                        }
                        userAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }
}