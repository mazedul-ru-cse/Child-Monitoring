package com.example.control;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.Login;
import com.example.model.R;
import com.example.model.SetRating;
import com.example.view.AboutDeveloper;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView parentName, parentEmailH;

    ListView childListView;
    SharedPreferences sharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = findViewById(R.id.draw_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        childListView = findViewById(R.id.childList);

        //toolbar
        setSupportActionBar(toolbar);

        //Navigation menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

//      Set user name
        View hview;
        hview = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        // Show child list
        try {
            ProgressDialog dialog = ProgressDialog.show(Dashboard.this , "","Loading...",true);
            dialog.show();
            ArrayList<String> list = new ArrayList<>();
            ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.child_list_layout, list);

            childListView.setAdapter(adapter);
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference(Login.userID);

            reference.child("Child").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    list.clear();
                    for (DataSnapshot childInfo : dataSnapshot.getChildren()) {


                        for (DataSnapshot childName : childInfo.getChildren()) {

                            if (childName.getKey().toString().equals("Name"))
                                list.add(childName.getValue().toString());
                        }
                        adapter.notifyDataSetChanged();
                    }
                    dialog.dismiss();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            // set head navigation
            reference.child("Parent").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    parentName = hview.findViewById(R.id.parentName);
                    parentEmailH = hview.findViewById(R.id.userEmailHeader);


                    for (DataSnapshot pName : dataSnapshot.getChildren()) {
                        if (pName.getKey().toString().equals("Name")) {
                            parentName.setText(pName.getValue().toString());

                        } else if (pName.getKey().toString().equals("Email")) {
                            parentEmailH.setText(pName.getValue().toString());

                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        // select child
        childListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(Dashboard.this, childListView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();

                // Set Active child name
                ParentProfileControl.activeChildName = childListView.getItemAtPosition(position).toString();
                startActivity(new Intent(Dashboard.this, ParentProfileControl.class));
            }
        });

    }


    // Double press the back button to exit
    int backPressedCount = 0;

    @Override
    public void onBackPressed() {


        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }

        else {
            if (backPressedCount >= 1) {
                super.onBackPressed();
                backPressedCount = 0;
                return;
            } else {
                backPressedCount = backPressedCount + 1;
                Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.nav_home :

                startActivity(new Intent(Dashboard.this , Dashboard.class));
                finish();
                break;

            case R.id.nav_logout :

                // Logout from user current account
                logout();

                break;

            case R.id.nav_share :

                appShare();
                break;

            case R.id.nav_ratting :
                //Set Rating

                startActivity(new Intent(Dashboard.this , SetRating.class));

                break;

            case R.id.nav_aboutDev :
                Toast.makeText(this, "About Developer", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Dashboard.this , AboutDeveloper.class));
                break;

            default:
                return true;
        }
        return true;
    }

    private void appShare() {

        DatabaseReference shareLink = FirebaseDatabase.getInstance().getReference("AppUpdation").child("ShareLink");
        try{
            shareLink.addValueEventListener(new ValueEventListener() {
                String link;
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    link = dataSnapshot.getValue().toString(); // get app link from firebase

                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String shareBody = "Download the Child Monitoring app now to control your child and family members : "+link;
                    String shareSub = "Child Monitoring";
                    //Toast.makeText(Dashboard.this, shareBody, Toast.LENGTH_SHORT).show();

                    shareIntent.putExtra(Intent.EXTRA_SUBJECT , shareSub);
                    shareIntent.putExtra(Intent.EXTRA_TEXT , shareBody);
                    startActivity(Intent.createChooser(shareIntent , "Share using"));

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void logout (){

        ProgressDialog dialog = ProgressDialog.show(Dashboard.this , "","Logout. Please wait....",true);
        dialog.show();

        try {
            sharedPreference = getSharedPreferences("UserInfoSave", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreference.edit();
            editor.putString("login", "false");
            editor.apply();
            Toast.makeText(this, "Logout", Toast.LENGTH_SHORT).show();

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {

                    startActivity(new Intent(Dashboard.this, Login.class));
                    dialog.dismiss();
                    finish();
                }
            }, 2000);

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}