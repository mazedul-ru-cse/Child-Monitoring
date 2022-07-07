package com.example.control;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.UserGuideViwer;
import com.example.model.BuildConfig;
import com.example.model.Login;
import com.example.model.R;
import com.example.view.AboutDeveloper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
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
    ProgressDialog dialog;
    private InterstitialAd mInterstitialAd;
    AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        drawerLayout = findViewById(R.id.draw_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        adView = findViewById(R.id.banner_ads_view1);

        childListView = findViewById(R.id.childList);
        ArrayAdapter adapter;

        //toolbar
        setSupportActionBar(toolbar);

        //Navigation menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();



        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        //show ads
        setAds();


//      Set user name
        View hview;
        hview = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        // Show child list
        try {
            dialog = ProgressDialog.show(Dashboard.this ,
                    "","Loading...",true);

            ArrayList<String> list = new ArrayList<>();

            adapter = new ArrayAdapter<String>(this, R.layout.child_list_layout, list);

            //childListView.setAdapter(adapter);

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
                    }
                    dialog.dismiss();
                    childListView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                    dialog.dismiss();
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

            dialog.dismiss();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        //Banner Ads
        showBannerAds();

        // select child
        childListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(Dashboard.this, childListView.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                //Set Active child name
                ParentProfileControl.activeChildName = childListView.getItemAtPosition(position).toString();

                adsLoad();

            }
        });

    }

    private void showBannerAds() {

        try {

            AdRequest adRequest = new AdRequest.Builder().build();
            adView.loadAd(adRequest);

        }catch (Exception e) {

        }

    }

    private void adsLoad() {
        if(mInterstitialAd != null){

            mInterstitialAd.show(Dashboard.this);

            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();

                    startActivity(new Intent(Dashboard.this, ParentProfileControl.class));
                    mInterstitialAd = null;
                    setAds();
                }
            });

        }else{
            startActivity(new Intent(Dashboard.this, ParentProfileControl.class));
        }
    }

    private void setAds() {

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, getString(R.string.interstitial_ads1), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        // Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        //  Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
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

            case R.id.download_child_version:
                downloadChildVersion();

                break;

            case R.id.user_guide:

                userGuide();

                break;

            case R.id.privacy_policy:
                privacyPolicy();

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
                rateApp();

                break;

            case R.id.nav_aboutDev :
                Toast.makeText(this, "About Developer", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Dashboard.this , AboutDeveloper.class));
                break;
        }
        leftSide();

        return true;
    }

    private void userGuide() {

//        try {
//            Uri uri = Uri.parse(" https://drive.google.com/file/d/1P81uAYQjPtsSt49nhHEvl4s6SLy9qrsU/view?usp=sharing");
//            startActivity(new Intent(Intent.ACTION_VIEW, uri));
//        }catch (Exception e){
//
//        }
        startActivity(new Intent(Dashboard.this , UserGuideViwer.class));

    }

    private void privacyPolicy() {

        try {
            Uri uri = Uri.parse("https://docs.google.com/document/d/1WIQ7_8HLEYzmJ4Lj6cZ9wAB6SvnbfQhnEmN4tv555QE/edit?usp=sharing");
            startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }catch (Exception e){

        }

    }

    private void downloadChildVersion() {

        try{
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.example.childmonitoringchildversion");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }catch (Exception e){

        }
    }

    private void rateApp() {

        try{
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName().toString());
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }catch (Exception e){

        }
    }

    private void appShare() {

        try{
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Child Monitoring app");
            String mess = "\nThis is the first free Parent Controlling app. Download the app now.\n";
            mess = mess + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, mess);
            startActivity(Intent.createChooser(shareIntent,"Share by"));

        }catch (Exception e){

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

    public void leftSide(){
        // rightSide();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

}