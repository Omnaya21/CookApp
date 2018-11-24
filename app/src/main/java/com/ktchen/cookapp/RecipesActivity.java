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

import java.util.List;

import static com.ktchen.cookapp.AddRecipe.EXTRA_MESSAGE;

public class RecipesActivity extends AppCompatActivity {
    private List<Recipe> recipes = null;
    RecipeBook book= new RecipeBook();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent= getIntent();

        book.addRecipe( new Recipe("test1"));
        book.addRecipe( new Recipe("test2"));
        book.addRecipe( new Recipe("test3"));
        Bundle extras= getIntent().getExtras();
        if(extras!=null)
        {
            RecipeBook newBook= new RecipeBook();
            newBook= (RecipeBook)extras.getSerializable(EXTRA_MESSAGE);
            for (int i=0; i< newBook.getRecipeBook().size(); i++)
            {
               book.addRecipe(newBook.getRecipeBook().get(i));
            }
        }
        setContentView(R.layout.activity_recipes);
        Log.i("ActivityInfo","RecipeActivity created");
        setTitle("Recipes");
        RecyclerView recyclerView= findViewById(R.id.recipe_view);
        RecipeAdapter adapter =new RecipeAdapter(book);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
        };


