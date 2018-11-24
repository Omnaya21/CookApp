package com.ktchen.cookapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.List;

public class RecipesActivity extends AppCompatActivity {
    private List<Recipe> recipes = null;
    private RecipeViewModel mRecipeViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        Log.i("ActivityInfo","RecipeActivity created");
        setTitle("Recipes");

        };

}
