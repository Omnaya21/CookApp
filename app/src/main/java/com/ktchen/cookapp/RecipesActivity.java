package com.ktchen.cookapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new RecipeAdapter(this, recipes);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        // This will allow us to create a context menu to delete recipes we don't need or want
        registerForContextMenu(recyclerView);

        /// Set the onClick listener for the FAB button
        FloatingActionButton fabNewRecipe = findViewById(R.id.fab_new_recipe);
        fabNewRecipe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecipesActivity.this, AddRecipe.class);
                startActivity(intent);
            }
        });
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
        if (v.getId() == R.id.recipe_view) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.recipe_context_menu, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.edit: // Edit Recipe
                Toast.makeText(this, "Edit from Recipes", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.delete:   // Delete Recipe
                Toast.makeText(this, "Delete from Recipes", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

   public void onDeleteButton(View view){
        db.deleteAll();
        recipes.clear();
        adapter.notifyDataSetChanged();
   }


};



