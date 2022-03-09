package com.example.control.callLog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.R;

import java.util.ArrayList;

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.MyViewHolder> {
    Context context;
    ArrayList<CallLogModel> callLogModelArrayList;

    public CallLogAdapter(Context context, ArrayList<CallLogModel> callLogModelArrayList) {
        this.context = context;
        this.callLogModelArrayList = callLogModelArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Resources r = parent.getResources();
//        px = Math.round(TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, 8,r.getDisplayMetrics()));
        View v = LayoutInflater.from(context).inflate(R.layout.custom_call_log, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        CallLogModel currentLog = callLogModelArrayList.get(position);
        holder.phNumber.setText(currentLog.getPhNumber());
        holder.contactName.setText(currentLog.getContactName());
        holder.callDate.setText(currentLog.getCallDate());
        holder.callLogDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, "Call log", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(

                        "Number : "+currentLog.getPhNumber() + "\n" +
                        "Name : "+ currentLog.getContactName() + "\n"+
                        "Call type : " + currentLog.getCallType() + "\n" +
                        "Date : " + currentLog.getCallDate() + " , "+  currentLog.getCallTime() + "\n"+
                        "Call duration : " +currentLog.getCallDuration()

                );
                builder.setCancelable(true);

                builder.setPositiveButton("Close",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return callLogModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView phNumber, contactName, callType, callDate, callTime, callDuration;
        ImageView callLogDetails;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            phNumber = itemView.findViewById(R.id.callLogNumber);
            contactName = itemView.findViewById(R.id.callLogName);
            callDate = itemView.findViewById(R.id.callLogDate);
            callLogDetails = itemView.findViewById(R.id.callLogDetails);



        }
    }
}
