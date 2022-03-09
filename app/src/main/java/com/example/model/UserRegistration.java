package com.example.model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserRegistration extends AppCompatActivity {

    EditText userName,userEmail,userPassword,userConfPassword;
    Button userRegister;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    TextView alreadyRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

       userName =  findViewById(R.id.userName);
       userEmail = findViewById(R.id.userEmail);
       userPassword = findViewById(R.id.userPassword);
       userConfPassword = findViewById(R.id.userConformPassword);
       userRegister = findViewById(R.id.userRegistration);
       alreadyRegistered = findViewById(R.id.userAlreadyRegistered);

       firebaseAuth = firebaseAuth.getInstance();
       progressBar = findViewById(R.id.regProBar);



       // User registration
       userRegister.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String name, email, password, confPassword;

               name = userName.getText().toString();
               email = userEmail.getText().toString();
               password = userPassword.getText().toString();
               confPassword = userConfPassword.getText().toString();

               if (TextUtils.isEmpty(name)) {
                   userName.setError("Name is required.");
                   return;
               }
               if (TextUtils.isEmpty(email)) {
                   userEmail.setError("Email is required.");
                   return;
               }

               if (password.length() < 6) {
                   userPassword.setError("Password must be 6 or more digit");
                   return;
               }

               if (TextUtils.isEmpty(password)) {
                   userPassword.setError("Password is required.");
                   return;
               }
               if (TextUtils.isEmpty(confPassword)) {
                   userConfPassword.setError("Confirm password is required.");
                   return;
               }

               if (!password.equals(confPassword)) {
                   userPassword.setError("Must be same");
                   userConfPassword.setError("Must be same");
                   return;
               }

               progressBar.setVisibility(View.VISIBLE);

               firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       if (task.isSuccessful()) {


                           // Get current user UID
                           FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                           String userUID = user.getUid();

                           //save userUID
                           Login.userID = userUID;

                           //Store User information
                           firebaseDatabase = FirebaseDatabase.getInstance();
                           DatabaseReference databaseReference = firebaseDatabase.getReference(userUID).child("Parent");

                           databaseReference.child("Name").setValue(name);
                           databaseReference.child("Email").setValue(email);

                           Toast.makeText(UserRegistration.this, "Account successfully created", Toast.LENGTH_SHORT).show();

                           //Go to Login activity
                           startActivity(new Intent(UserRegistration.this, Login.class));
                           finish();

                       } else {
                           Toast.makeText(UserRegistration.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                       }
                   }
               });

           }

       });


       // if user already registered
       alreadyRegistered.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               //Go to Login activity
               startActivity(new Intent(UserRegistration.this, Login.class));
               finish();
           }
       });
    }
}