package com.example.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.model.R;

public class AboutDeveloper extends AppCompatActivity {

    TextView fbLink , twLink , phone , aboutEmail , collegeName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_developer);

        fbLink =  findViewById(R.id.fb_link);
        twLink =  findViewById(R.id.twitter_link);
        phone =  findViewById(R.id.aboutPhone);
        aboutEmail =  findViewById(R.id.about_email);
        collegeName =  findViewById(R.id.college_name);


        // set email address
        aboutEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    aboutEmail.setMovementMethod(LinkMovementMethod.getInstance());
                }catch (Exception e){
                    Toast.makeText(AboutDeveloper.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // College website

        collegeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    collegeName.setMovementMethod(LinkMovementMethod.getInstance());
                }catch (Exception e){
                    Toast.makeText(AboutDeveloper.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // set facebook hyperlink
        fbLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    fbLink.setMovementMethod(LinkMovementMethod.getInstance());
                }catch (Exception e){
                    Toast.makeText(AboutDeveloper.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        // set twitter hyperlink

        twLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    twLink.setMovementMethod(LinkMovementMethod.getInstance());
                }catch (Exception e){
                    Toast.makeText(AboutDeveloper.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        
        
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                if(getCallPermission()){

                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+phone.getText().toString()));
                    startActivity(callIntent);

                }
            }
        });

    }

    private boolean getCallPermission() {

        if(ContextCompat.checkSelfPermission(AboutDeveloper.this , Manifest.permission.CALL_PHONE)
         != PackageManager.PERMISSION_GRANTED ){
            
            ActivityCompat.requestPermissions(AboutDeveloper.this , new String[] 
                    { Manifest.permission.CALL_PHONE},1);
            return true;
        }else {
            return true;
        }
        
        
    }
}