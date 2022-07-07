package com.example;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.model.R;
import com.github.barteksc.pdfviewer.PDFView;

public class UserGuideViwer extends AppCompatActivity {

    PDFView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_guide_viwer);

        pdfView = findViewById(R.id.pdf_guide);
        pdfView.fromAsset("user_guide.pdf").load();
        pdfView.loadPages();


    }
}