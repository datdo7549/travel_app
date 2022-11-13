package com.example.travel_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.travel_app.feature.map.MyMapFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChooseLocationActivity extends AppCompatActivity {
    private MyMapFragment myMapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        this.myMapFragment = (MyMapFragment) fragmentManager.findFragmentById(R.id.fragment_map);

        FloatingActionButton btnAdd = findViewById(R.id.btnChoose);
        btnAdd.setOnClickListener(v -> {
            finish();
        });
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, ChooseLocationActivity.class);
        context.startActivity(intent);
    }
}