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



        // Get last update date and time
        getlastUpdateTimeDate();

        //Set child name
        activeChildNameV.setText(activeChildName);

        //Show child location
        showChildLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               startActivity(new Intent(ParentProfileControl.this , ChildLocation.class));
            }
        });

        showChildCallLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ParentProfileControl.this , ChildCallHistory.class));
            }
        });

        // Show child sms history

        showChildSmsHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ParentProfileControl.this , ChildSmsHistory.class));
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
                startActivity(new Intent(ParentProfileControl.this , Contacts.class));
            }
        });


        //Show child photo

        showChildPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ParentProfileControl.this , Photos.class));
            }
        });

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
}