package com.example.control.photos;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.control.Dashboard;
import com.example.control.ParentProfileControl;
import com.example.model.Login;
import com.example.model.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class Photos extends AppCompatActivity {

    ImageView childImage;
    TextView imActiveChildName, imLastUpdate;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);

        childImage = findViewById(R.id.childPhoto);
        imActiveChildName = findViewById(R.id.photoActiveChild);
        imLastUpdate = findViewById(R.id.photoUpdateDateAndTime);


        imActiveChildName.setText(ParentProfileControl.activeChildName);
        imLastUpdate.setText(ParentProfileControl.lastDataUpdate);

        getPhotoFromDatabase();
    }

    private void getPhotoFromDatabase() {

        storageReference = FirebaseStorage.getInstance().getReference(Login.userID+"/Child/"+
                ParentProfileControl.activeChildName);
        ProgressDialog dialog = ProgressDialog.show(Photos.this ,
                "","Loading...",true);
        dialog.show();

        try {
            final File file = File.createTempFile(ParentProfileControl.activeChildName, null);
            storageReference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    childImage.setImageBitmap(bitmap);

                    if(dialog.isShowing()){
                        dialog.dismiss();
                    }
                }
            });

        }catch (Exception e){

            if(dialog.isShowing()){
                dialog.dismiss();
            }
            e.printStackTrace();
        }
    }
}