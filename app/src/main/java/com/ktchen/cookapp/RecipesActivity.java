package com.ktchen.cookapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new RecipeAdapter(this, recipes);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
        Intent intent = new Intent(this, AddRecipe.class);
        intent.putExtra(EXTRA_MESSAGE, adapter.getItem(position));
        startActivity(intent);
    }

   public void onDeleteButton(View view){
        db.deleteAll();
        recipes.clear();
        adapter.notifyDataSetChanged();
   }

};



