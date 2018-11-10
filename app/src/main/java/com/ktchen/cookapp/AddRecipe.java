package com.ktchen.cookapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class AddRecipe extends AppCompatActivity {
    private static final String IMAGE_DIRECTORY = "/cookapp";
    private static final int GALLERY = 1;
    private static final int CAMERA = 2;
    ImageView recipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);

        recipeImage = findViewById(R.id.recipeImage);
        recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog(v);
            }
        });
    }

    private void showPictureDialog(final View v) {
        String[] pictureDialogItems = {
                "Select image from gallery",
                "Take picture from camera" };
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select source")
            .setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:     // Select image from gallery
                                pickImageFromGallery(v);
                                break;

                            case 1:     // Take picture from camera
                                takePhotoFromCamera();
                                break;
                        }
                    }
                })
                .create()
                .show();

    }

    public void pickImageFromGallery(View v) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }

        switch (requestCode) {
            case GALLERY:
                if (data != null) {
                    Uri contentUri = data.getData();

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentUri);
                        String path = saveImage(bitmap);
                        Toast.makeText(AddRecipe.this, "Image saved.", Toast.LENGTH_SHORT).show();
                        recipeImage.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(AddRecipe.this, "Failed.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case CAMERA:
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                recipeImage.setImageBitmap(bitmap);
                saveImage(bitmap);
                Toast.makeText(AddRecipe.this, "Image saved.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public String saveImage(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File imageDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        // Have the object build the directory if needed.
        if (!imageDirectory.exists()) {
            imageDirectory.mkdirs();
        }

        try {
            File f = new File(imageDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(f);
            outputStream.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            outputStream.close();
            Log.d("TAG", "File saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

}
