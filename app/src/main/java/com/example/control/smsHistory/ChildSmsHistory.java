package com.example.control.smsHistory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.control.Dashboard;
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

public class ChildSmsHistory extends AppCompatActivity {

    TextView  smsLogActiveCh;

    RecyclerView smsLogRecyclerView;
    SmsLogAdapter smsLogAdapter;
    ArrayList<SmsLogModel> smsLogModelArrayList;
    DatabaseReference databaseReference;
    TextView smsLogUpdateTimeDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_sms_history);

        smsLogActiveCh = findViewById(R.id.smsLogActiveChild);
        smsLogRecyclerView = findViewById(R.id.smsLogReView);
        smsLogUpdateTimeDate = findViewById(R.id.smsLogUpdateDateAndTime);

        // set last update time and date
        smsLogUpdateTimeDate.setText(ParentProfileControl.lastDataUpdate);

        smsLogActiveCh.setText(ParentProfileControl.activeChildName);

        smsLogRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        smsLogModelArrayList = new ArrayList<>();
        smsLogAdapter = new SmsLogAdapter(this, smsLogModelArrayList);
        smsLogRecyclerView.setAdapter(smsLogAdapter);

        ProgressDialog dialog = ProgressDialog.show(ChildSmsHistory.this , "","Loading...",true);
        dialog.show();

        try {
            databaseReference = FirebaseDatabase.getInstance().getReference(Login.userID)
                    .child("Child")
                    .child(ParentProfileControl.activeChildName)
                    .child("SmsHistory");

            // Get SMS history from database
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot callLog : dataSnapshot.getChildren()) {

                        SmsLogModel smsLogModel = callLog.getValue(SmsLogModel.class);
                        smsLogModelArrayList.add(smsLogModel);
                        //Toast.makeText(ChildCallHistory.this, callLog.getValue().toString(), Toast.LENGTH_SHORT).show();
                    }
                    smsLogAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Toast.makeText(, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    databaseError.toException().printStackTrace();
                    dialog.dismiss();

                }
            });
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }



    }
}