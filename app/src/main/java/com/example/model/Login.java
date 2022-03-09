package com.example.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.R.color;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.control.Dashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.TimeUnit;


public class  Login extends AppCompatActivity {
    public static String userID;
    TextView forgetPass,createAccount;
    Button btnUserLogin;
    EditText loginEmail , loginPassword;
    FirebaseAuth firebaseAuth;

    SharedPreferences setSharedPreferences;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        forgetPass = findViewById(R.id.forgetPassword);
        createAccount = findViewById(R.id.createAnAccount);
        btnUserLogin = findViewById(R.id.login);

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);

        firebaseAuth = FirebaseAuth.getInstance();
        


        try {
            setSharedPreferences = getSharedPreferences("UserInfoSave", Context.MODE_PRIVATE);

            if (setSharedPreferences.getString("login", "").equals("true") ) {

                // Show progress dialog

                ProgressDialog dialog = ProgressDialog.show(Login.this , "","Login. Please wait...",true);
                dialog.show();

                Login.userID = setSharedPreferences.getString("userID","");

                //Go to Dashboard activity after 2 second
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(Login.this, Dashboard.class));
                        dialog.dismiss();
                        finish();
                    }
                },200);
            }

        }catch (Exception e){
            Toast.makeText(this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }



        //User login button
        btnUserLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // show dialog
                ProgressDialog dialog = ProgressDialog.show(Login.this , "","Login. Please wait...",true);
                dialog.show();

                String email = loginEmail.getText().toString();
                String password = loginPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    loginEmail.setError("Email is required.");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    loginPassword.setError("Password is required.");
                    return;
                }


                // Authenticate the user and add child

                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){

                            // Get current user UID
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String userUID = user.getUid();
                            SharedPreferences.Editor editor = setSharedPreferences.edit();
                            editor.putString("userID",userUID);
                            editor.putString("password",password);
                            editor.putString("email",email);
                            editor.putString("login","true");
                            editor.apply();

                            Toast.makeText(Login.this, "Login successfully", Toast.LENGTH_SHORT).show();

                            //Go to Dashboard activity
                            startActivity(new Intent(Login.this, Dashboard.class));
                            dialog.dismiss();
                            finish();
                        }else {
                            dialog.dismiss();
                            Toast.makeText(Login.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }
        });




        // Recovery password button
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passwordReset = new AlertDialog.Builder(v.getContext());
                passwordReset.setTitle("Reset Password ?");
                passwordReset.setMessage("Enter your email to receive the reset link. ");
                passwordReset.setView(resetMail);
                passwordReset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetMail.getText().toString();
                        firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Login.this, "Reset link send to your email . please check your email inbox.", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Error ! Reset link is not send "+e.getMessage() , Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                passwordReset.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                passwordReset.create().show();
            }
        });

        // Create an new account button
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, UserRegistration.class));
            }
        });
    }



    // Double press the back button to exit
    int backPressedCount = 0;
    @Override
    public void onBackPressed() {

        if (backPressedCount >= 1){
            super.onBackPressed();
            backPressedCount = 0;
            return;
        }
        else{
            backPressedCount = backPressedCount + 1;
            Toast.makeText(getBaseContext(),"Press back again to exit",Toast.LENGTH_SHORT).show();
        }

    }
}