package com.example.control.smsHistory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.control.callLog.CallLogAdapter;
import com.example.control.callLog.CallLogModel;
import com.example.model.R;

import java.util.ArrayList;

public class SmsLogAdapter extends RecyclerView.Adapter<SmsLogAdapter.MyViewHolder> {
    Context context;
    ArrayList<SmsLogModel> smsLogModelArrayList;

    public SmsLogAdapter(Context context, ArrayList<SmsLogModel> smsLogModelArrayList) {
        this.context = context;
        this.smsLogModelArrayList = smsLogModelArrayList;
    }

    @NonNull
    @Override
    public SmsLogAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Resources r = parent.getResources();
//        px = Math.round(TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP, 8,r.getDisplayMetrics()));
        View v = LayoutInflater.from(context).inflate(R.layout.custom_sms_log, parent, false);

        return new SmsLogAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SmsLogAdapter.MyViewHolder holder, int position) {

        SmsLogModel currentSmsLog = smsLogModelArrayList.get(position);

        holder.smsId.setText(currentSmsLog.getSmsId());
        holder.smsDate.setText(currentSmsLog.getSmsDate());
        holder.smsBody.setText(currentSmsLog.getSmsBody());

        holder.smsBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(context, "Call log", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(

                                "Number : "+currentSmsLog.getSmsId() + "\n" +
                                "Date : "+ currentSmsLog.getSmsDate() + "\n"+
                                "Sms type : " + currentSmsLog.getSmsType()+ "\n"

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
        return smsLogModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView smsBody , smsId, smsDate ;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            smsBody = itemView.findViewById(R.id.smsLogBody);
            smsId = itemView.findViewById(R.id.smsLogNumber);
            smsDate = itemView.findViewById(R.id.smsLogDate);



        }
    }
}
