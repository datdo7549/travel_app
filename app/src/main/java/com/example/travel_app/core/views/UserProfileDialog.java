package com.example.travel_app.core.views;

import static com.example.travel_app.feature.explore.ExploreFragment.userProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.travel_app.R;
import com.example.travel_app.feature.model.UserProfile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserProfileDialog extends DialogFragment {
    private AppCompatImageView avatar;
    private AppCompatTextView tvName;
    private AppCompatButton btnGroup, btnAddFriend;
    private String uuid, name;

    private Boolean isAddGroupShow;

    public UserProfileDialog(String uuid, String name, Boolean isAddGroupShow) {
        this.uuid = uuid;
        this.name = name;
        this.isAddGroupShow = isAddGroupShow;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_profile_popup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        avatar = view.findViewById(R.id.img_avatar);
        tvName = view.findViewById(R.id.user_name_text_view);

        btnGroup = view.findViewById(R.id.btnGroup);
        btnAddFriend = view.findViewById(R.id.btnAddFriend);

        if (!isAddGroupShow) {
            btnGroup.setVisibility(View.GONE);
        } else {
            btnGroup.setVisibility(View.VISIBLE);
        }

        try {
            FirebaseDatabase.getInstance().getReference("UserProfile").child(userProfile.uuid).child("friends").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        boolean isFriend = false;
                        for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                            UserProfile user = postSnapshot.getValue(UserProfile.class);
                            if (user.uuid.equals(uuid)) {
                                isFriend = true;
                                break;
                            }
                        }
                        if (isFriend) {
                            btnAddFriend.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        } catch (Exception e) {

        }


        if (uuid.equals(userProfile.uuid)) {
            btnAddFriend.setVisibility(View.GONE);
        }


        Glide.with(view)
                .load("https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80")
                .circleCrop()
                .into(avatar);

        tvName.setText(name);

        btnAddFriend.setOnClickListener(v -> {
            ArrayList<UserProfile> currentFriendList = new ArrayList<>();
            if (userProfile.friends != null && userProfile.friends.size() > 0) {
                currentFriendList.addAll(userProfile.friends);
            }
            currentFriendList.add(new UserProfile(uuid, name, ""));

            FirebaseDatabase.getInstance().getReference("UserProfile").child(userProfile.uuid).child("friends").setValue(currentFriendList).addOnCompleteListener(task -> {
                userProfile.setFriends(currentFriendList);
                dismiss();
            });
        });
    }

}
