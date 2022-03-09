package com.example.control.contacts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.control.ParentProfileControl;
import com.example.control.callLog.CallLogAdapter;
import com.example.control.callLog.CallLogModel;
import com.example.control.callLog.ChildCallHistory;
import com.example.model.Login;
import com.example.model.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity {

    RecyclerView contactsRecyclerView;
    ContactsAdapter contactsAdapter;
    ArrayList<ContactsModel> contactsModelArrayList;
    DatabaseReference databaseReference;
    TextView contactsChild;
    TextView contactsUpdateTimeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        contactsRecyclerView = findViewById(R.id.contactsReView);
        contactsChild = findViewById(R.id.contactsActiveChild);
        contactsUpdateTimeDate = findViewById(R.id.contactsUpdateDateAndTime);

        //Set last call log time and date
        contactsUpdateTimeDate.setText(ParentProfileControl.lastDataUpdate);

        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        contactsChild.setText(ParentProfileControl.activeChildName);
        contactsModelArrayList = new ArrayList<>();
        contactsAdapter = new ContactsAdapter(this, contactsModelArrayList);
        contactsRecyclerView.setAdapter(contactsAdapter);


        ProgressDialog dialog = ProgressDialog.show(Contacts.this , "","Loading...",true);
        dialog.show();
        try {
            databaseReference = FirebaseDatabase.getInstance().getReference(Login.userID)
                    .child("Child")
                    .child(ParentProfileControl.activeChildName)
                    .child("ContactList");

            // Toast.makeText(this, Login.userID + " , " + ParentProfileControl.activeChildName, Toast.LENGTH_SHORT).show();
            // Get Call log history from database
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot contacts : dataSnapshot.getChildren()) {

                        ContactsModel contactsModel = contacts.getValue(ContactsModel.class);
                        contactsModelArrayList.add(contactsModel);
                        //Toast.makeText(ChildCallHistory.this, callLog.getValue().toString(), Toast.LENGTH_SHORT).show();
                    }
                    contactsAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Contacts.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    databaseError.toException().printStackTrace();
                    dialog.dismiss();

                }
            });
        }catch (Exception e){
            dialog.dismiss();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
}