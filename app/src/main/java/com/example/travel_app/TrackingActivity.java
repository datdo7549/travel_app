package com.example.travel_app;

import static com.example.travel_app.feature.explore.ExploreFragment.userProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.travel_app.core.Const;
import com.example.travel_app.feature.map.MyMapFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

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

        FirebaseDatabase.getInstance().getReference().child(Const.USER_ONLINE).child(Objects.requireNonNull(userProfile).uuid).setValue(userProfile.uuid).addOnCompleteListener(task -> {

        });
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, TrackingActivity.class);
        context.startActivity(intent);
    }


}