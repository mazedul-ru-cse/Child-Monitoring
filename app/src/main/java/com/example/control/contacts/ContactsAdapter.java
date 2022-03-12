package com.example.control.contacts;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.control.callLog.CallLogAdapter;
import com.example.control.callLog.CallLogModel;
import com.example.model.R;

import java.util.ArrayList;

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {

    Context context;
    ArrayList<ContactsModel> contactsModelArrayList;

    public ContactsAdapter(Context context, ArrayList<ContactsModel> contactsModelArrayList) {
        this.context = context;
        this.contactsModelArrayList = contactsModelArrayList;
    }

    @NonNull
    @Override
    public ContactsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_contacts_list, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ContactsModel contactsList = contactsModelArrayList.get(position);

        holder.contactName.setText(contactsList.getContactName());
        holder.contactNumber.setText(contactsList.getContactNumber());

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+holder.contactNumber.getText().toString()));

        holder.contactName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ActivityCompat.checkSelfPermission(context , Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
                    requestPermission();
                }else{
                    context.startActivity(callIntent);
                }
            }
        });

        holder.contactNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ActivityCompat.checkSelfPermission(context , Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    //Toast.makeText(, "", Toast.LENGTH_SHORT).show();
                    requestPermission();
                }else{
                    context.startActivity(callIntent);
                }
            }
        });


    }

    private void requestPermission() {
        ActivityCompat.requestPermissions((Activity) context, new String[] { Manifest.permission.CALL_PHONE} , 1);
    }

    @Override
    public int getItemCount() {
        return contactsModelArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView contactName;
        TextView contactNumber;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            contactName = itemView.findViewById(R.id.contactName);
            contactNumber = itemView.findViewById(R.id.contactNumber);
        }
    }

}
