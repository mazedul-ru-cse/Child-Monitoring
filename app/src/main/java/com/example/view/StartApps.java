package com.example.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.model.Login;
import com.example.model.R;

import java.util.Timer;
import java.util.TimerTask;

public class StartApps extends AppCompatActivity {

    ImageView startUp;
   // TextView startTv ;

    int currentProg = 0;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide action bar and title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();

        setContentView(R.layout.activity_start_apps);
        startUp = findViewById(R.id.start_up_view);
        progressBar = findViewById(R.id.start_up_progress);

       // showAppName("Child Monitoring");


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                currentProg += 20;
                progressBar.setProgress(currentProg);
                progressBar.setMax(100);
            }
        },1000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                currentProg += 20;
                progressBar.setProgress(currentProg);
                progressBar.setMax(100);
            }
        },2000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {

                currentProg += 20;
                progressBar.setProgress(currentProg);
                progressBar.setMax(100);
            }
        },3000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                currentProg += 20;
                progressBar.setProgress(currentProg);
                progressBar.setMax(100);
            }
        },4000);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                currentProg += 20;
                progressBar.setProgress(currentProg);
                progressBar.setMax(100);
            }
        },5000);




        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(StartApps.this, Login.class));
                finish();
            }
        },6000);

    }

//    private void showAppName(String appName) {
//
//        startTv = findViewById(R.id.start_up_name_show);
//
//        final int[] i = new int[1];
//        i[0] = 0;
//        final int length = appName.length();
//        final  Handler handler = new Handler(){
//            @Override
//            public void handleMessage(@NonNull Message msg) {
//                super.handleMessage(msg);
//
//                char c = appName.charAt(i[0]);
//                startTv.append(String.valueOf(c));
//                i[0]++;
//
//            }
//        };
//
//        final Timer timer = new Timer();
//        TimerTask task = new TimerTask() {
//            @Override
//            public void run() {
//                handler.sendEmptyMessage(0);
//                if (i[0] == length - 1){
//                    timer.cancel();
//                }
//            }
//        };
//        timer.schedule(task,1,200);
//
//    }

}