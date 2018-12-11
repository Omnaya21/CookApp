package com.ktchen.cookapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    RecipeAdapter adapter;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DatabaseHelper.getInstance(this);
        Intent intent = getIntent();
        recipes.addAll(db.getAllRecipes());

        //  if (intent.getExtras()!=null)
        //     recipes.add((Recipe) getIntent().getExtras().getSerializable(EXTRA_MESSAGE));

        setContentView(R.layout.activity_recipes);
        Log.i("ActivityInfo", "RecipeActivity created");
        setTitle("Recipes");
        RecyclerView recyclerView = findViewById(R.id.recipe_view);
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
        finish();;
        startActivity(getIntent());
    }

    /**
     * Refresh activity after going to add recipe or update recipe.
     *
     */


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
                //db.deleteRecipe(db.getRecipe(item.getOrder()));
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



