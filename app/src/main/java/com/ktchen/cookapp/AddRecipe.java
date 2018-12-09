package com.ktchen.cookapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ktchen.cookapp.database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * This class is responsible for Adding a recipie to the app and database.
 */
public class AddRecipe extends AppCompatActivity {
    private static final String IMAGE_DIRECTORY = "/cookapp";
    private static final int GALLERY = 1;
    private static final int CAMERA = 2;
    ImageView recipeImage;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor editor;
    private CheckBox favoriteBox;
    private final String checkBox = "favoriteCheckBox";
    EditText recipeTitle;
    EditText ingredientsBox;
    EditText directionsBox;
    List<Recipe> recipes= new ArrayList<Recipe>();
    public static final String EXTRA_MESSAGE =  "com.ktchen.cookapp/extra";
    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        Log.i("ActivityInfo","AddRecipe created");
        db = DatabaseHelper.getInstance(this);
        recipeImage = findViewById(R.id.recipeImage);
        favoriteBox= findViewById(R.id.favoriteCheckBox);
        recipeTitle = (EditText) findViewById(R.id.title);
        ingredientsBox = (EditText) findViewById((R.id.ingredients));
        directionsBox = (EditText) findViewById(R.id.preparation);
        recipes.addAll(db.getAllRecipes());
        Intent intent= getIntent();
        if (intent.getExtras()!=null) {
            Bundle extras = intent.getExtras();
            Recipe recipe= (Recipe) extras.getSerializable(EXTRA_MESSAGE);
            recipeTitle.setText(recipe.getTitle());
            ingredientsBox.setText(recipe.getIngredients());
            directionsBox.setText(recipe.getDirections());
        }


        recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog(v);
            }
        });
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor= mPreferences.edit();

        checkSharedPreferences();


    }

    /**
     * Called when save button is clicked.   Takes the recipie fields and saves them.
     * @param view
     */
 public void onSaved(View view) {
            String title = recipeTitle.getText().toString();
            String ingredients = ingredientsBox.getText().toString();
            String direction = directionsBox.getText().toString();
            Recipe newRecipe = new Recipe(title, ingredients, direction);
            long id = db.insertRecipe(newRecipe);
            newRecipe.setID(id);
            recipes.add(newRecipe);

            Intent intent= new Intent(this, RecipesActivity.class);
         //   intent.putExtra(EXTRA_MESSAGE, newRecipe);
            startActivity(intent);

        }

    /**
     * Called when checkbox is clicked or uncliked.   Sets favorite using a shared preference.
     * @param view
     */
    public void onCheckboxClicked(View view){
    if(favoriteBox.isChecked()) {
        editor.putString(checkBox, "True");
        editor.commit();
    }
    else{
        editor.putString(checkBox, "False");
        editor.commit();
    }
}
    private void createRecipe(Recipe recipe) {
        // inserting note in db and getting
        // newly inserted note id
        long id = db.insertRecipe(recipe);
        Log.i("database", "added item");
        // get the newly inserted note from db
        Recipe r = db.getRecipe(id);
        Log.i("database", "retrieved item");
        if (r != null) {
            // adding new note to array list at 0 position
            recipes.add(0, r);

        }
    }
    private void checkSharedPreferences(){
        String favoriteCheckbox= mPreferences.getString(checkBox, "False");
        if (favoriteCheckbox.equals("True")){
            favoriteBox.setChecked(true);
        }
        else
            favoriteBox.setChecked(false);
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

    /**
     * Shows a dialog to allow users select an image to use on the app.
     * @param v
     */
    public void pickImageFromGallery(View v) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA);
    }

    /**
     * After user selected an image or took a picture, now it's time to show that picture.
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
                        Log.e("ExceptionThrown", "Error opening image");
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

    /**
     * Saves the image locally for later use.
     * @param bitmap
     * @return the image path.
     */
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
            Log.e("ExceptionThrown", "Error Saving Images");
            e.printStackTrace();
        }

        return "";
    }

}
