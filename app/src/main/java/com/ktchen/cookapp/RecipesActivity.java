package com.ktchen.cookapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.ktchen.cookapp.AddRecipe.EXTRA_MESSAGE;

/**
 * RecipesActivity shows all the recipes in a card view so user can select it
 * or do something with a recipe.
 */
public class RecipesActivity extends AppCompatActivity {
    private List<Recipe> recipes = new ArrayList<Recipe>();
    RecipeBook book= new RecipeBook();
    RecipeAdapter adapter =new RecipeAdapter(recipes);
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent= getIntent();

        recipes.add( new Recipe("test1"));
        recipes.add( new Recipe("test2"));
        recipes.add( new Recipe("test3"));
        if (intent.getExtras()!=null)
           recipes.add((Recipe) getIntent().getExtras().getSerializable(EXTRA_MESSAGE));

        setContentView(R.layout.activity_recipes);
        Log.i("ActivityInfo","RecipeActivity created");
        setTitle("Recipes");
        RecyclerView recyclerView= findViewById(R.id.recipe_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
        };


