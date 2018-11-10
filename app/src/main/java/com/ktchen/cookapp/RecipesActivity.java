package com.ktchen.cookapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class RecipesActivity extends AppCompatActivity {
    private List<Recipe> recipes = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        setTitle("Recipes - Card view");
    }
}
