package com.example.control.callLog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.control.ParentProfileControl;
import com.example.control.smsHistory.ChildSmsHistory;
import com.example.model.Login;
import com.example.model.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import javax.security.auth.callback.CallbackHandler;

public class ChildCallHistory extends AppCompatActivity {

    RecyclerView callLogRecyclerView;
    CallLogAdapter callLogAdapter;
    ArrayList<CallLogModel> callLogModelArrayList;
    DatabaseReference databaseReference;
    TextView callLogChild;
    TextView callLogUpdateTimeDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_call_history);

        callLogRecyclerView = findViewById(R.id.callLogReView);
        callLogChild = findViewById(R.id.callLogActiveChild);
        callLogUpdateTimeDate = findViewById(R.id.callLogUpdateDateAndTime);

        //Set last call log time and date
        callLogUpdateTimeDate.setText(ParentProfileControl.lastDataUpdate);

        callLogRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        callLogChild.setText(ParentProfileControl.activeChildName);
        callLogModelArrayList = new ArrayList<>();
        callLogAdapter = new CallLogAdapter(this, callLogModelArrayList);
        callLogRecyclerView.setAdapter(callLogAdapter);


        ProgressDialog dialog = ProgressDialog.show(ChildCallHistory.this , "","Loading...",true);
        dialog.show();
        try {
            databaseReference = FirebaseDatabase.getInstance().getReference(Login.userID)
                    .child("Child")
                    .child(ParentProfileControl.activeChildName)
                    .child("CallLog");

            // Toast.makeText(this, Login.userID + " , " + ParentProfileControl.activeChildName, Toast.LENGTH_SHORT).show();
            // Get Call log history from database
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot callLog : dataSnapshot.getChildren()) {

                        CallLogModel callLogModel = callLog.getValue(CallLogModel.class);
                        callLogModelArrayList.add(callLogModel);
                        //Toast.makeText(ChildCallHistory.this, callLog.getValue().toString(), Toast.LENGTH_SHORT).show();
                    }
                    callLogAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(ChildCallHistory.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
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