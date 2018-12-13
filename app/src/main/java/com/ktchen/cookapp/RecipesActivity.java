package com.ktchen.cookapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ktchen.cookapp.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

import static com.ktchen.cookapp.AddRecipe.EXTRA_MESSAGE;

/**
 * RecipesActivity shows all the recipes in a card view so user can select it
 * or do something with a recipe.
 */
public class RecipesActivity extends AppCompatActivity implements RecipeAdapter.ItemClickListener {
    private List<Recipe> recipes = new ArrayList<Recipe>();
    RecyclerView recyclerView;
    RecipeAdapter adapter;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DatabaseHelper.getInstance(this);
        Intent intent = getIntent();

        recipes.addAll(db.getAllRecipes());

        if (recipes.size() <= 0) {
            //pre-fill database with some recipes for ease of use.
            db.insertRecipe(new Recipe("Mac & Cheese", "noodles, cheese, milk", "Cook noodles and melt cheese."));
            db.insertRecipe(new Recipe("Spaghetti", "pasta, sauce", "Cook noodles and add sauce."));
            db.insertRecipe(new Recipe("Baked Potato", "potatos, sour cream, salt, pepper, butter", "Bake potato and serve"));
            db.insertRecipe(new Recipe("Tacos", "ground beef, taco shells, cheese, taco seasoning, salsa, sour cream", "cook meat, season and serve."));
            db.insertRecipe(new Recipe("Burritos", "tortillas, beans, cheese, hot sauce", "warm beans and serve"));
            db.insertRecipe(new Recipe("Pizza", "frozen pizza", "Bake and serve"));
            db.insertRecipe(new Recipe("Orange Chicken", "frozen orange chicken, rice", "Cook rice and cook chicken according to package"));
            db.insertRecipe(new Recipe("Lo Mein", "noodles, soy sauce, veggies", "Cook noodles and veggies, season."));
            db.insertRecipe(new Recipe("Lasagna", "frozen lasagna", "serve"));
            db.insertRecipe(new Recipe("Chicken and Rice", "chicken and rice", "Cook and serve"));
            db.insertRecipe(new Recipe("Beans", "beans, seasoning, water", "Cook beans and mash"));
            // This function call has been made before. We don't need to call it again unless we add the sample recipes.
            // inside this block. If we call it outside, we might duplicate recipes in the List but not in the database
            // and display view will have the wrong number of recipes.
            recipes.addAll(db.getAllRecipes());
        }


        //  if (intent.getExtras()!=null)
        //     recipes.add((Recipe) getIntent().getExtras().getSerializable(EXTRA_MESSAGE));

        setContentView(R.layout.activity_recipes);
        Toolbar recipesToolbar = findViewById(R.id.recipes_toolbar);
        setSupportActionBar(recipesToolbar);
        setTitle("Recipes");
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        Log.i("ActivityInfo", "RecipeActivity created");

        recyclerView = findViewById(R.id.recipe_view);
        //GridLayoutManager gridLayout = new GridLayoutManager(this, 2);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        adapter = new RecipeAdapter(this, recipes);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        // This will allow us to create a context menu to delete recipes we don't need or want
        registerForContextMenu(recyclerView);

        /// Set the onClick listener for the FAB Add recipe button
        FloatingActionButton fabNewRecipe = findViewById(R.id.fab_new_recipe);
        fabNewRecipe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesActivity.this, AddRecipe.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Called after {@link #onStop} when the current activity is being
     * re-displayed to the user (the user has navigated back to it).  It will
     * be followed by {@link #onStart} and then {@link #onResume}.
     *
     * <p>For activities that are using raw {@link Cursor} objects (instead of
     * creating them through
     * {@link #managedQuery(Uri, String[], String, String[], String)},
     * this is usually the place
     * where the cursor should be required (because you had deactivated it in
     * {@link #onStop}.
     *
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onStop
     * @see #onStart
     * @see #onResume
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        /// This lines will refresh our view once we're back from Add/Edit recipe
        /// Since we updated our database, now we need to update our view and include
        /// those changes.
        finish();;
        startActivity(getIntent());
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
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
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
        GridLayoutManager layout;

        switch(item.getItemId()) {
            case R.id.grid_1:
                layout = (GridLayoutManager)recyclerView.getLayoutManager();
                layout.setSpanCount(1);
                recyclerView.setLayoutManager(layout);
                return true;

            case R.id.grid_2:
                layout = (GridLayoutManager)recyclerView.getLayoutManager();
                layout.setSpanCount(2);
                recyclerView.setLayoutManager(layout);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
        Intent intent = new Intent(this, AddRecipe.class);
        intent.putExtra(EXTRA_MESSAGE, adapter.getItem(position));
        startActivity(intent);
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //if (v.getId() == R.id.recipe_view) {
        //    MenuInflater inflater = getMenuInflater();
        //    inflater.inflate(R.menu.recipe_context_menu, menu);
        // }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        String itemInfoStr;
        super.onContextItemSelected(item);
        switch (item.getItemId()) {
            case R.id.edit: // Edit Recipe
                Toast.makeText(this, "Edit Recipe " + item.getOrder(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, AddRecipe.class);
                intent.putExtra(EXTRA_MESSAGE, adapter.getItem(item.getOrder()));   // item.getOrder() retrieves the recipe ID
                startActivity(intent);
                return true;

            case R.id.delete:   // Delete Recipe
                Toast.makeText(this, "Delete Recipe " + item.getOrder(), Toast.LENGTH_SHORT).show();
                db.deleteRecipe(db.getRecipe(item.getOrder()+1));
                recipes.remove(item.getOrder());
                adapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

   public void onDeleteButton(View view){
        /// If there is no recipes in database we just show a message and return back.
        if (db.getAllRecipes().size() <= 0) {
            Toast.makeText(this, "There are no recipes in database!", Toast.LENGTH_SHORT).show();
            return;
        }

        // We have to add a confirmation here before delete all recipes.
       AlertDialog.Builder dialog = new AlertDialog.Builder(RecipesActivity.this);
       dialog.setTitle("Delete?")
               .setMessage("Are you sure you want to delete ALL recipes?")
               .setNegativeButton("Cancel", null)
               .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialogInterface, int which) {
                       /// Remove all recipes from database
                       db.deleteAll();
                       recipes.clear();
                       adapter.notifyDataSetChanged();
                   }
               })
               .setIcon(R.drawable.ic_dialog_alert)
               .show();
   }


};



