package com.example.control;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.control.callLog.ChildCallHistory;
import com.example.control.contacts.Contacts;
import com.example.control.location.ChildLocation;
import com.example.control.photos.Photos;
import com.example.control.smsHistory.ChildSmsHistory;
import com.example.model.Login;
import com.example.model.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ParentProfileControl extends AppCompatActivity {

    public static String lastDataUpdate = null;

    TextView activeChildNameV;
    public static String activeChildName;
    ImageView showChildLoc ,
            showChildCallLog ,
            showChildSmsHistory ,
            showChildBrowserHistory,
            showChildContacts,
            showChildPhoto;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    private InterstitialAd mInterstitialAd;
    AdView adView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_profile_control);

        activeChildNameV = findViewById(R.id.activeChild);
        showChildLoc = findViewById(R.id.showChildLocation);
        showChildCallLog = findViewById(R.id.showChildCallHistory);
        showChildSmsHistory = findViewById(R.id.showChildMessageHistory);
        showChildBrowserHistory = findViewById(R.id.showChildBrowsingHistory);
        showChildContacts = findViewById(R.id.showChildContacts);
        showChildPhoto = findViewById(R.id.showChildPhoto);
        adView2 = findViewById(R.id.banner_ads_view2);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        // Get last update date and time
        getlastUpdateTimeDate();

        //Set Ads
        setAds(getString(R.string.interstitial_ads2));
        // Show Banner Ads
        showBannerAds();

        //Set child name
        activeChildNameV.setText(activeChildName);

        //Show child location
        showChildLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(mInterstitialAd != null){

                    mInterstitialAd.show(ParentProfileControl.this);

                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();

                            mInterstitialAd = null;
                            setAds(getString(R.string.interstitial_ads3));
                            startActivity(new Intent(ParentProfileControl.this , ChildLocation.class));                            mInterstitialAd = null;
                        }
                    });

                }else{
                    startActivity(new Intent(ParentProfileControl.this , ChildLocation.class));
                }

            }
        });

        showChildCallLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mInterstitialAd != null){

                    mInterstitialAd.show(ParentProfileControl.this);

                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();

                            mInterstitialAd = null;
                            setAds(getString(R.string.interstitial_ads4));
                            startActivity(new Intent(ParentProfileControl.this , ChildCallHistory.class));
                        }
                    });

                }else{
                    startActivity(new Intent(ParentProfileControl.this , ChildCallHistory.class));
                }
            }
        });

        // Show child sms history

        showChildSmsHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mInterstitialAd != null){

                    mInterstitialAd.show(ParentProfileControl.this);

                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();

                            mInterstitialAd = null;
                            setAds(getString(R.string.interstitial_ads5));
                            startActivity(new Intent(ParentProfileControl.this , ChildSmsHistory.class));
                        }
                    });

                }else{
                    startActivity(new Intent(ParentProfileControl.this , ChildSmsHistory.class));
                }

            }
        });

        //Show child browsing history

        showChildBrowserHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AlertDialog alertDialog = new AlertDialog.Builder(ParentProfileControl.this).create();
                alertDialog.setMessage("Coming Soon");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL , "OK", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                alertDialog.show();
            }
        });

        //Show child contacts list

        showChildContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mInterstitialAd != null){

                    mInterstitialAd.show(ParentProfileControl.this);

                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();

                            mInterstitialAd = null;
                            setAds(getString(R.string.interstitial_ads6));
                            startActivity(new Intent(ParentProfileControl.this , Contacts.class));
                        }
                    });

                }else{
                    startActivity(new Intent(ParentProfileControl.this , Contacts.class));
                }

            }
        });


        //Show child photo

        showChildPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mInterstitialAd != null){

                    mInterstitialAd.show(ParentProfileControl.this);

                    mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            super.onAdDismissedFullScreenContent();

                            mInterstitialAd = null;
                            setAds(getString(R.string.interstitial_ads1));
                            startActivity(new Intent(ParentProfileControl.this , Photos.class));                        }
                    });

                }else{
                    startActivity(new Intent(ParentProfileControl.this , Photos.class));                }
            }
        });

    }

    private void showBannerAds() {
        try {

            AdRequest adRequest = new AdRequest.Builder().build();
            adView2.loadAd(adRequest);

        }catch (Exception e) {

        }
    }

    private void getlastUpdateTimeDate() {

        databaseReference = FirebaseDatabase.getInstance().getReference(Login.userID).
                child("Child").
                child(activeChildName).
                child("LastUpdate");

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {

                    Toast.makeText(ParentProfileControl.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                    lastDataUpdate = dataSnapshot.getValue().toString();

                } catch (Exception e) {

                    Toast.makeText(ParentProfileControl.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }


                @Override
                public void onCancelled (@NonNull DatabaseError databaseError){

                    Toast.makeText(ParentProfileControl.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();

                }



        });

    }

    private void setAds(String adsID) {

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this, adsID, adRequest,
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

}