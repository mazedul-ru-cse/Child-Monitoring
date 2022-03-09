package com.example.model;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.control.Dashboard;

public class SetRating extends AppCompatActivity {

    RatingBar ratingBar;
    EditText rateCmt;
    TextView rateText;
    Button rateLater , rateSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_rating);

        ratingBar = findViewById(R.id.ratingApp);
        rateCmt = findViewById(R.id.rateComment);
        rateText = findViewById(R.id.rateText);
       // rateLater = findViewById(R.id.rateLater);
        rateSubmit = findViewById(R.id.rateSubmit);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                rateText.setText("Rate : "+rating);
            }
        });

        // set later button

//        rateLater.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // Go to dashboard activity
//                startActivity(new Intent(SetRating.this , Dashboard.class));
//                finish();
//            }
//        });
    }
}