package com.example.control.location;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.example.control.ParentProfileControl;
import com.example.model.Login;
import com.example.model.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ChildLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//
//        Location location = mMap.getMyLocation();
//        Double lat = location.getLatitude();
//        Double lng = location.getLongitude();

        try {


            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Login.userID);
            reference.child("Child").child(ParentProfileControl.activeChildName).child("Location").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Double lat = null;
                    Double lng = null;
                    for (DataSnapshot latlng : dataSnapshot.getChildren()) {
                        if (latlng.getKey().toString().equals("Latitude")) {
                            lat = Double.valueOf(latlng.getValue().toString());
                        } else if (latlng.getKey().toString().equals("Longitude")) {
                            lng = Double.valueOf(latlng.getValue().toString());
                        }
                    }

                    // Toast.makeText(ChildLocation.this,""+lat + " , " + lng , Toast.LENGTH_SHORT).show();
                    // Add a marker in Sydney and move the camera
                    LatLng sydney = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(sydney).title(ParentProfileControl.activeChildName));
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

}