package com.example.travel_app.feature.map;

import static com.example.travel_app.feature.explore.ExploreFragment.userProfile;
import static com.example.travel_app.feature.groups.GroupDetailFragment.listMemberId;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.travel_app.R;
import com.example.travel_app.feature.model.MyLatLong;
import com.example.travel_app.feature.model.UserProfile;
import com.example.travel_app.feature.model.UserProfileLite;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MyMapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private GoogleMap googleMap;

    public MyMapFragment() {
        getMapAsync(this);
    }
    public static String fnAddress = "";

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLocationPermission();

    }

    @Override
    public void onMapReady(final GoogleMap gmap) {
        this.googleMap = gmap;
        getDeviceLocation();

        this.googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);


                List<Address> addresses = new ArrayList<>();
                try {
                    addresses = new Geocoder(requireContext(), Locale.getDefault()).getFromLocation(latLng.latitude, latLng.longitude,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                android.location.Address address = addresses.get(0);
                fnAddress = address.getSubThoroughfare() + " " + address.getThoroughfare() + ", " + address.getSubAdminArea() + ", " + address.getAdminArea();
                markerOptions.title(fnAddress);
                // Clear previously click position.
                googleMap.clear();
                // Zoom the Marker
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 30));
                // Add Marker on Map
                googleMap.addMarker(markerOptions);
            }
        });
    }

    private boolean locationPermissionGranted = false;

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1111);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode
                == 1111) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = LocationServices.getFusedLocationProviderClient(requireContext()).getLastLocation();
                locationResult.addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        if (task.getResult() != null) {
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(task.getResult().getLatitude(),
                                            task.getResult().getLongitude()), 30));


                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(new LatLng(task.getResult().getLatitude(),
                                    task.getResult().getLongitude()));
                            markerOptions.title("Your location");
                            // Clear previously click position.
                            googleMap.clear();
                            googleMap.addMarker(markerOptions);


                            MyLatLong myLatLong = new MyLatLong();
                            myLatLong.uuid = userProfile.uuid;
                            myLatLong.name = userProfile.name;
                            myLatLong.lat = task.getResult().getLatitude();
                            myLatLong.longitude = task.getResult().getLongitude();

                            FirebaseDatabase.getInstance().getReference("LatLong").child(userProfile.uuid).setValue(myLatLong);

                        }
                    } else {
                        LatLng vietnam = new LatLng(14.0583, 108.2772);
                        googleMap.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(vietnam, 30));
                        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }


    public void setMemberLocation() {
        FirebaseDatabase.getInstance().getReference("LatLong").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                googleMap.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    MyLatLong user = postSnapshot.getValue(MyLatLong.class);
                    for (String memberId : listMemberId) {
                        if (memberId.equals(user.uuid)) {
                            MarkerOptions markerOptions = new MarkerOptions();
                            markerOptions.position(new LatLng(user.lat,
                                    user.longitude));
                            markerOptions.title(user.name);
                            // Clear previously click position.
                            googleMap.addMarker(markerOptions);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}