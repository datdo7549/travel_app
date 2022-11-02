package com.example.travel_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.travel_app.databinding.ActivityCreatePostBinding;

public class CreatePostActivity extends AppCompatActivity {
    private ActivityCreatePostBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreatePostBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    private void initView() {
        binding.btnChooseLocation.setOnClickListener(v -> ChooseLocationActivity.start(this));
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, CreatePostActivity.class);
        context.startActivity(intent);
    }
}