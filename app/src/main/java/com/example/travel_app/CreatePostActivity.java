package com.example.travel_app;

import static com.example.travel_app.feature.login.model.RegisterAccountStatus.CREATE_POST_FAIL;
import static com.example.travel_app.feature.login.model.RegisterAccountStatus.CREATE_POST_SUCCESS;
import static com.example.travel_app.feature.login.model.RegisterAccountStatus.SUCCESS;
import static com.example.travel_app.feature.login.model.RegisterAccountStatus.SUCCESS_BUT_UPDATE_PROFILE_ERROR;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.travel_app.core.Const;
import com.example.travel_app.core.dialog.SingleButtonDialogFragment;
import com.example.travel_app.core.listeners.DialogButtonClickListener;
import com.example.travel_app.core.platform.BaseEnum;
import com.example.travel_app.databinding.ActivityCreatePostBinding;
import com.example.travel_app.feature.explore.ExploreFragment;
import com.example.travel_app.feature.map.MyMapFragment;
import com.example.travel_app.feature.model.UserPost;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreatePostActivity extends AppCompatActivity {
    private ActivityCreatePostBinding binding;

    @Inject
    public FirebaseAuth firebaseAuth;

    @Inject
    public DatabaseReference firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    private void initView() {
        binding.btnChooseLocation.setOnClickListener(v -> ChooseLocationActivity.start(this));
        binding.btnCreatePost.setOnClickListener(v -> {
            String postId = UUID.randomUUID().toString();
            UserPost userPost = new UserPost(
                    postId,
                    Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid(),
                    ExploreFragment.userProfile.name,
                    binding.editTextPostTitle.getText().toString(),
                    binding.editTextPostDescription.getText().toString(),
                    System.currentTimeMillis(),
                    binding.editTextPostAddress.getText().toString(),
                    new ArrayList<>(),
                    new ArrayList<>()
            );
            firebaseDatabase.child(Const.USER_POST).child(postId).setValue(userPost).addOnCompleteListener(task -> {
               if (task.isSuccessful()) {
                   showResultDialog(CREATE_POST_SUCCESS, this::finish);
               } else {
                   showResultDialog(CREATE_POST_FAIL, this::finish);
               }
            });
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!MyMapFragment.fnAddress.equals("")) {
            binding.editTextPostAddress.setText(MyMapFragment.fnAddress);
        }
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, CreatePostActivity.class);
        context.startActivity(intent);
    }

    public void showResultDialog(BaseEnum status) {
        SingleButtonDialogFragment singleButtonDialogFragment = new SingleButtonDialogFragment(
                getSupportFragmentManager(),
                status.getTitle(),
                status.getDescription()
        );
        singleButtonDialogFragment.show(status.getTitle());
    }

    public void showResultDialog(BaseEnum status, DialogButtonClickListener dialogButtonClickListener) {
        SingleButtonDialogFragment singleButtonDialogFragment = new SingleButtonDialogFragment(
                getSupportFragmentManager(),
                status.getTitle(),
                status.getDescription()
        );
        singleButtonDialogFragment.show(status.getTitle());
        singleButtonDialogFragment.setRegisterAccountButtonListener(dialogButtonClickListener);
    }
}