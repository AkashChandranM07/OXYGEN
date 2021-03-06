package com.ac07.oxygen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Wleaf extends AppCompatActivity {
    ImageView imageViewProfilePicture1;
    Button lr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wleaf);
        lr=findViewById(R.id.b1);
        imageViewProfilePicture1 = findViewById(R.id.camera);
        imageViewProfilePicture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseProfilePicture();
            }
        });
        lr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lr1=new Intent(Wleaf.this, leafrust.class);
                startActivity(lr1);
            }
        });
    }
    private void chooseProfilePicture(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Wleaf.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_dialog_profile_picture1, null);
        builder.setCancelable(false);
        builder.setView(dialogView);

        ImageView imageViewADPPCamera = dialogView.findViewById(R.id.camera);
        ImageView imageViewADPPGallery = dialogView.findViewById(R.id.gallery);

        final AlertDialog alertDialogProfilePicture = builder.create();
        alertDialogProfilePicture.show();

        imageViewADPPCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkAndRequestPermissions()) {
                    takePictureFromCamera();
                    alertDialogProfilePicture.dismiss();
                }
            }
        });

        imageViewADPPGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePictureFromGallery();
                alertDialogProfilePicture.dismiss();
            }
        });
    }

    private void takePictureFromGallery(){
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 1);
    }

    private void takePictureFromCamera(){
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePicture.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takePicture, 2);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImageUri = data.getData();
                    imageViewProfilePicture1.setImageURI(selectedImageUri);
                }
                break;
            case 2:
                if(resultCode == RESULT_OK){
                    Bundle bundle = data.getExtras();
                    Bitmap bitmapImage = (Bitmap) bundle.get("data");
                    imageViewProfilePicture1.setImageBitmap(bitmapImage);
                }
                break;
        }
    }

    private boolean checkAndRequestPermissions(){
        if(Build.VERSION.SDK_INT >= 23){
            int cameraPermission = ActivityCompat.checkSelfPermission(Wleaf.this, Manifest.permission.CAMERA);
            if(cameraPermission == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(Wleaf.this, new String[]{Manifest.permission.CAMERA}, 20);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 20 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            takePictureFromCamera();
        }
        else
            Toast.makeText(Wleaf.this, "Permission not Granted", Toast.LENGTH_SHORT).show();
    }


}