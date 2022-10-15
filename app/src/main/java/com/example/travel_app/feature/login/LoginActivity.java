package com.example.travel_app.feature.login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travel_app.R;
import com.example.travel_app.databinding.ActivityLoginBinding;
import com.example.travel_app.databinding.ActivityMainBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    public void onBackPressed() {}
}