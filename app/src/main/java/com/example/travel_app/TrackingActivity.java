package com.example.travel_app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.travel_app.feature.map.MyMapFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TrackingActivity extends AppCompatActivity {
    private MyMapFragment myMapFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_location);

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        this.myMapFragment = (MyMapFragment) fragmentManager.findFragmentById(R.id.fragment_map);

        this.myMapFragment.setMemberLocation();

        FloatingActionButton btnAdd = findViewById(R.id.btnChoose);
        btnAdd.setVisibility(View.GONE);
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, TrackingActivity.class);
        context.startActivity(intent);
    }
}