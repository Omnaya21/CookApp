package com.ktchen.cookapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ktchen.cookapp.database.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * This class is responsible for Adding a recipe to the app and database.
 */
public class AddRecipe extends AppCompatActivity {
    private static final String IMAGE_DIRECTORY = "/cookapp";
    public static final String EXTRA_MESSAGE = "com.ktchen.cookapp/extra";
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
    String imagePath = "";
    Button saveUpdateBtn;
    List<Recipe> recipes = new ArrayList<>();

    private DatabaseHelper db;
    private long recipeId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        Toolbar addRecipeToolbar = findViewById(R.id.add_recipe_toolbar);
        setSupportActionBar(addRecipeToolbar);
        ActionBar ab = getSupportActionBar();
        setTitle("Add Recipe");

        Log.i("ActivityInfo", "AddRecipe created");
        db = DatabaseHelper.getInstance(this);
        saveUpdateBtn = findViewById(R.id.saveButton);
        saveUpdateBtn.setText(R.string.button_save);
        recipeImage = findViewById(R.id.recipe_image);
        favoriteBox = findViewById(R.id.favoriteCheckBox);
        recipeTitle = findViewById(R.id.title);
        ingredientsBox = findViewById((R.id.ingredients));
        directionsBox = findViewById(R.id.preparation);
        recipes.addAll(db.getAllRecipes());
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            Bundle extras = intent.getExtras();
            Recipe recipe = (Recipe) extras.getSerializable(EXTRA_MESSAGE);
            if (recipe != null) {
                recipeTitle.setText(recipe.getTitle());
                ingredientsBox.setText(recipe.getIngredients());
                directionsBox.setText(recipe.getDirections());
                imagePath = recipe.getImagePath();
                recipeId = recipe.getID();
                loadImage(imagePath);

                saveUpdateBtn.setText(R.string.button_update);
                setTitle("Edit Recipe");
                if (ab != null)
                    ab.setSubtitle(recipe.getTitle());
            }
        }

        recipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog(v);
            }
        });
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = mPreferences.edit();
        checkSharedPreferences();
        editor.apply();
    }

    private void loadImage(String fileName) {
        if (fileName == null)
            return;

        File imgFile = new File(fileName);

        if (imgFile.exists()) {
            Bitmap recipeBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ImageView recipeImageView = findViewById(R.id.recipe_image);
            recipeImageView.setImageBitmap(recipeBitmap);
        }
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     *
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     *
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     *
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     *
     * <p>When you add items to the menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_recipe_toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     *
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_recipe:
                onSaved(item.getActionView());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Called when save button is clicked.   Takes the recipe fields and saves them.
     *
     * @param view Current view
     */
    public void onSaved(View view) {
        String title = recipeTitle.getText().toString();
        String ingredients = ingredientsBox.getText().toString();
        String direction = directionsBox.getText().toString();
        //Recipe newRecipe = new Recipe(title, ingredients, direction);
        Recipe newRecipe = new Recipe(title, ingredients, direction, imagePath);
        if (saveUpdateBtn.getText().toString().matches("Save")) {
            long id = db.insertRecipe(newRecipe);
            newRecipe.setID(id);
            recipes.add(newRecipe);
        }
        else {  //Update current recipe instead of creating another one
            if (recipeId > -1) {
                // Update recipe
                newRecipe.setID(recipeId);
                if( db.updateRecipe(newRecipe) != 1)
                    Toast.makeText(this, "Recipe not updated", Toast.LENGTH_SHORT).show();
            }
        }

        // We have to finish activity or the app will have different behaviour with the back arrows on top and bottom
        finish();
    }

    /**
     * Called when checkbox is clicked or un-clicked.   Sets favorite using a shared preference.
     *
     * @param view Current view
     */
    public void onCheckboxClicked(View view) {
        if (favoriteBox.isChecked()) {
            editor.putString(checkBox, "True");
            editor.commit();
        } else {
            editor.putString(checkBox, "False");
            editor.commit();
        }
    }

    /* // This function is never used. I keep it as a comment just in case someone will use it later.
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
    */

    private void checkSharedPreferences() {
        String favoriteCheckbox = mPreferences.getString(checkBox, "False");
        if (favoriteCheckbox != null && favoriteCheckbox.equals("True")) {
            favoriteBox.setChecked(true);
        } else
            favoriteBox.setChecked(false);
    }

    private void showPictureDialog(final View v) {
        String[] pictureDialogItems = {
                "Select image from gallery",
                "Take picture from camera"};
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
     *
     * @param v Current view
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
     *
     * @param requestCode Code sent when the startActivityForResult() was called
     * @param resultCode Result code
     * @param data Contains data returned by the previously launched activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        switch (requestCode) {
            case GALLERY:
                if (data != null) {
                    Uri contentUri = data.getData();

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentUri);
                        String path = saveImage(bitmap);
                        imagePath = path;
                        Toast.makeText(AddRecipe.this, "Image saved to " + path, Toast.LENGTH_SHORT).show();
                        recipeImage.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.e("ExceptionThrown", "Error opening image");
                        e.printStackTrace();
                        Toast.makeText(AddRecipe.this, "Failed.", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case CAMERA:
                Bitmap bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
                recipeImage.setImageBitmap(bitmap);
                imagePath = saveImage(bitmap);
                Toast.makeText(AddRecipe.this, "Image saved to " + imagePath, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * Saves the image locally for later use.
     *
     * @param bitmap Bitmap to save
     * @return the image path.
     */
    public String saveImage(Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File imageDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);

        // Have the object build the directory if needed.
        if (!imageDirectory.exists()) {
            if (!imageDirectory.mkdirs()) {
                Toast.makeText(this, "Image directory could not be created!", Toast.LENGTH_SHORT).show();
                return "";
            }
        }

        try {
            File f = new File(imageDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            if (f.createNewFile()) {
                FileOutputStream outputStream = new FileOutputStream(f);
                outputStream.write(bytes.toByteArray());
                MediaScannerConnection.scanFile(this,
                        new String[]{f.getPath()},
                        new String[]{"image/jpeg"}, null);
                outputStream.close();
                Log.d("TAG", "File saved::--->" + f.getAbsolutePath());

                return f.getAbsolutePath();
            }
            else {
                Toast.makeText(this, "File could not be created!", Toast.LENGTH_SHORT).show();
                return "";
            }

        } catch (IOException e) {
            Log.e("ExceptionThrown", "Error Saving Images");
            e.printStackTrace();
        }

        return "";
    }

}
